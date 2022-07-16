package org.yjhking.pethome.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.system.domain.Systemdictionarydetail;
import org.yjhking.pethome.system.domain.Systemdictionarytype;
import org.yjhking.pethome.system.mapper.SystemdictionarydetailMapper;
import org.yjhking.pethome.system.mapper.SystemdictionarytypeMapper;
import org.yjhking.pethome.system.service.SystemdictionarytypeService;

import java.util.List;

/**
 * @author YJH
 */
@Service
public class SystemdictionarytypeServiceImpl extends BaseServiceImpl<Systemdictionarytype> implements SystemdictionarytypeService {
    @Autowired
    private SystemdictionarytypeMapper systemdictionarytypeMapper;
    
    @Autowired
    private SystemdictionarydetailMapper systemdictionarydetailMapper;
    
    @Transactional
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        List<Systemdictionarydetail> systemdictionarydetails = systemdictionarydetailMapper.selectAll();
        for (Systemdictionarydetail systemdictionarydetail : systemdictionarydetails) {
            if (systemdictionarydetail.getTypesId().equals(id)) {
                systemdictionarydetailMapper.deleteByPrimaryKey(systemdictionarydetail.getId());
            }
        }
        return systemdictionarytypeMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public void patchDelete(List<Long> ids) {
        List<Systemdictionarydetail> systemdictionarydetails = systemdictionarydetailMapper.selectAll();
        for (Systemdictionarydetail systemdictionarydetail : systemdictionarydetails) {
            for (Long id : ids) {
                if (systemdictionarydetail.getTypesId().equals(id)) {
                    systemdictionarydetailMapper.deleteByPrimaryKey(systemdictionarydetail.getId());
                }
            }
        }
        systemdictionarytypeMapper.patchDelete(ids);
    }
}
