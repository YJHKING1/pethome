package org.yjhking.pethome.org.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjhking.pethome.basic.service.impl.BaseServiceImpl;
import org.yjhking.pethome.org.domain.Systemdictionarydetail;
import org.yjhking.pethome.org.domain.Systemdictionarytype;
import org.yjhking.pethome.org.mapper.SystemdictionarydetailMapper;
import org.yjhking.pethome.org.mapper.SystemdictionarytypeMapper;
import org.yjhking.pethome.org.service.SystemdictionarytypeService;

import java.util.List;

@Service
public class SystemdictionarytypeServiceImpl extends BaseServiceImpl<Systemdictionarytype> implements SystemdictionarytypeService {
    @Autowired
    private SystemdictionarytypeMapper systemdictionarytypeMapper;
    
    @Autowired
    private SystemdictionarydetailMapper systemdictionarydetailMapper;
    
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
