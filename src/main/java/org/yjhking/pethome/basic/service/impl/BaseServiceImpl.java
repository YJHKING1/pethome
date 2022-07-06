package org.yjhking.pethome.basic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yjhking.pethome.basic.mapper.BaseMapper;
import org.yjhking.pethome.basic.query.BaseQuery;
import org.yjhking.pethome.basic.service.BaseService;
import org.yjhking.pethome.basic.util.PageList;

import java.util.List;

/**
 * 通用业务层实现类
 *
 * @author YJH
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    private BaseMapper<T> baseMapper;
    
    @Transactional
    @Override
    public Integer deleteByPrimaryKey(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Integer insertSelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }
    
    @Override
    public T selectByPrimaryKey(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }
    
    @Transactional
    @Override
    public Integer updateByPrimaryKeySelective(T t) {
        return baseMapper.updateByPrimaryKeySelective(t);
    }
    
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }
    
    @Override
    public PageList<T> queryData(BaseQuery baseQuery) {
        // 查询总条数
        Long queryCount = baseMapper.queryCount(baseQuery);
        // 如果查询总条数为0，则直接返回空集合
        if (queryCount < 1) {
            return new PageList<T>();
        }
        // 查询结果
        List<T> list = baseMapper.queryData(baseQuery);
        return new PageList<T>(queryCount, list);
    }
    
    @Override
    public void patchDelete(List<Long> ids) {
        baseMapper.patchDelete(ids);
    }
}