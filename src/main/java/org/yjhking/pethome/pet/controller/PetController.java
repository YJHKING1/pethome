package org.yjhking.pethome.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yjhking.pethome.basic.query.AjaxResult;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.pet.domain.Pet;
import org.yjhking.pethome.pet.domain.PetType;
import org.yjhking.pethome.pet.query.PetQuery;
import org.yjhking.pethome.pet.service.PetService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * (t_pet)表控制层
 *
 * @author xxxxx
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @GetMapping
    public List<Pet> findAll() {
        return petService.selectAll();
    }
    
    /**
     * 查找一个
     *
     * @param id 查找id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Pet findById(@PathVariable Long id) {
        return petService.selectByPrimaryKey(id);
    }
    
    /**
     * 删除
     *
     * @param id 删除id
     * @return 返回信息
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteById(@PathVariable Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            petService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 增加及修改
     *
     * @param Pet 增加或修改内容
     * @return 返回信息
     */
    @PutMapping
    public AjaxResult add(@RequestBody Pet Pet) {
        AjaxResult ajaxResult = new AjaxResult();
        if (Pet.getId() == null) {
            try {
                petService.insertSelective(Pet);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("新增失败");
            }
        } else {
            try {
                petService.updateByPrimaryKeySelective(Pet);
            } catch (Exception e) {
                e.printStackTrace();
                ajaxResult.setSuccess(false);
                ajaxResult.setMsg("修改失败");
            }
        }
        
        return ajaxResult;
    }
    
    /**
     * 高级分页查询
     *
     * @param PetQuery 高级分页查询参数
     * @return 查询结果
     */
    @PostMapping
    public PageList<Pet> queryPage(@RequestBody PetQuery PetQuery) {
        return petService.queryData(PetQuery);
    }
    
    /**
     * 批量删除
     *
     * @param ids 要删除的id集合
     * @return 返回信息
     */
    @PatchMapping
    public AjaxResult patchDelete(@RequestBody List<Long> ids) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            petService.patchDelete(ids);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMsg("批量删除失败");
        }
        return ajaxResult;
    }
    
    /**
     * 类型树
     *
     * @return 类型树
     */
    @GetMapping("/typeTree")
    public List<PetType> typeTree() {
        try {
            return petService.typeTree();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 宠物上架
     *
     * @param ids     要上架的id集合
     * @param request 请求
     * @return 返回信息
     */
    @PostMapping("/onsale")
    public AjaxResult onsale(@RequestBody List<Long> ids, HttpServletRequest request) {
        try {
            return petService.onsale(ids, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "上架失败");
        }
    }
    
    /**
     * 宠物下架
     *
     * @param ids     要下架的id集合
     * @param request 请求
     * @return 返回信息
     */
    @PostMapping("/offsale")
    public AjaxResult offsale(@RequestBody List<Long> ids, HttpServletRequest request) {
        try {
            return petService.offsale(ids, request);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(false, "下架失败");
        }
    }
}
