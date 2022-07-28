package org.yjhking.pethome.pet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.yjhking.pethome.basic.constants.FastDfsImgConstants;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.basic.util.BaiduAiUtils;
import org.yjhking.pethome.basic.util.LoginContext;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.mapper.EmployeeMapper;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.PetDetail;
import org.yjhking.pethome.pet.domain.PetOnlineAuditLog;
import org.yjhking.pethome.pet.domain.PetType;
import org.yjhking.pethome.pet.mapper.PetDetailMapper;
import org.yjhking.pethome.pet.mapper.PetMapper;
import org.yjhking.pethome.pet.mapper.PetOnlineAuditLogMapper;
import org.yjhking.pethome.pet.mapper.PetTypeMapper;
import org.yjhking.pethome.pet.service.PetService;
import org.yjhking.pethome.user.domain.Logininfo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author YJH
 */
@Service
public class PetServiceImpl extends BaseServiceImpl<Pet> implements PetService {
    @Autowired
    private PetTypeMapper petTypeMapper;
    @Autowired
    private PetDetailMapper petDetailMapper;
    @Autowired
    private PetMapper petMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private PetOnlineAuditLogMapper petOnlineAuditLogMapper;
    
    @Override
    @Transactional
    public Integer deleteByPrimaryKey(Long id) {
        petDetailMapper.deleteByPetId(id);
        super.deleteByPrimaryKey(id);
        return null;
    }
    
    @Override
    @Transactional
    public Integer insertSelective(Pet pet) {
        super.insertSelective(pet);
        //保存宠物明细
        PetDetail petDetail = pet.getPetDetail();
        if (petDetail != null) {
            petDetail.setPetId(pet.getId());
            petDetailMapper.insertSelective(petDetail);
        }
        return null;
    }
    
    @Override
    @Transactional
    public Integer updateByPrimaryKeySelective(Pet pet) {
        super.updateByPrimaryKeySelective(pet);
        //修改宠物明细信息
        PetDetail petDetail = pet.getPetDetail();
        if (petDetail != null) {
            petDetail.setPetId(pet.getId());
            petDetailMapper.updateByPrimaryKeySelective(petDetail);
        }
        return null;
    }
    
    @Override
    @Transactional
    public void patchDelete(List<Long> ids) {
        petDetailMapper.patchDeleteByPetIds(ids);
        super.patchDelete(ids);
    }
    
    @Override
    public List<PetType> typeTree() {
        List<PetType> petTypes = petTypeMapper.selectAll();
        HashMap<Long, PetType> map = new HashMap<>();
        for (PetType petType : petTypes) {
            map.put(petType.getId(), petType);
        }
        List<PetType> petTypeTree = new ArrayList<>();
        for (PetType petType : petTypes) {
            if (petType.getPid() == null) {
                petTypeTree.add(petType);
            } else {
                PetType parentType = map.get(petType.getPid());
                parentType.getChildren().add(petType);
            }
        }
        return petTypeTree;
    }
    
    @Override
    public AjaxResult onsale(List<Long> ids, HttpServletRequest request) {
        // 上架 -不能用批量操作，有上架审核
        for (Long id : ids) {
            // 上架自动审核文本
            Pet pet = petMapper.selectByPrimaryKey(id);
            String auditText = pet.getName();
            Boolean textBoo = BaiduAiUtils.textCensor(auditText);
            // 审核图片：多张图片resources
            Boolean imageBoo = true;
            // 图片途径是否为空
            if (StringUtils.hasLength(pet.getResources())) {
                // 判断是否有多张图片
                if (pet.getResources().contains(",")) {
                    String[] resources = pet.getResources().split(",");
                    for (String resource : resources) {
                        String petResources = FastDfsImgConstants.IMG_SERVER_PREFIX_URL + resource;
                        imageBoo = BaiduAiUtils.imgCensor(petResources);
                        if (!imageBoo) {
                            break;
                        }
                    }
                } else {
                    String petResources = FastDfsImgConstants.IMG_SERVER_PREFIX_URL + pet.getResources();
                    imageBoo = BaiduAiUtils.imgCensor(petResources);
                }
            }
            // 获取审核人
            Logininfo logininfo = (Logininfo) LoginContext.getLoginUser(request);
            Employee employee = employeeMapper.selectByLogininfoId(logininfo.getId());
            
            // 审核通过
            if (textBoo && imageBoo) {
                // 数据库操作
                Map<String, Object> params = new HashMap<>();
                params.put("id", id);
                params.put("onsaletime", new Date());
                petMapper.onsale(params);
                // 审核成功-note
                
                // 记录审核日志
                PetOnlineAuditLog auditLog = new PetOnlineAuditLog();
                auditLog.setState((byte) 1);
                auditLog.setPetId(id);
                auditLog.setAuditId(employee.getId());
                auditLog.setNote("审核成功！");
                petOnlineAuditLogMapper.insertSelective(auditLog);
            } else {
                // 审核失败-note-当前是下架状态，如果审核失败！
                // 记录审核日志
                PetOnlineAuditLog auditLog = new PetOnlineAuditLog();
                auditLog.setState((byte) 0);
                auditLog.setPetId(id);
                auditLog.setAuditId(employee.getId());
                auditLog.setNote("审核失败,宠物名称或图片不合法!!!");
                petOnlineAuditLogMapper.insertSelective(auditLog);
                //审核失败，上架失败
                return new AjaxResult(false, "上架失败！审核失败,宠物名称或图片不合法!!!");
            }
        }
        return AjaxResult.me();
    }
    
    @Override
    public AjaxResult offsale(List<Long> ids, HttpServletRequest request) {
        // 下架：修改state为0，offsaletime为当前时间 ,可以用批量操作
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("offsaletime", new Date());
        petMapper.offsale(params);
        return AjaxResult.me();
    }
}
