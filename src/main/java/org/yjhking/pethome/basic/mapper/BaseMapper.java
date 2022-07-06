package org.yjhking.pethome.basic.mapper;

import org.yjhking.pethome.basic.query.BaseQuery;

import java.util.List;

/**
 * 通用Mapper
 *
 * @author YJH
 */
public interface BaseMapper<T> {
    /**
     * 通过主键删除
     *
     * @param id 主键
     * @return 删除的id
     */
    Integer deleteByPrimaryKey(Long id);
    
    /**
     * 增加数据
     *
     * @param t 部门
     * @return 增加的id
     */
    Integer insertSelective(T t);
    
    /**
     * 通过主键查询
     *
     * @param id 主键
     * @return 查询结果
     */
    T selectByPrimaryKey(Long id);
    
    /**
     * 更新
     *
     * @param t 更新的数据
     * @return 更新的id
     */
    Integer updateByPrimaryKeySelective(T t);
    
    /**
     * 查询全部
     *
     * @return 查询结果
     */
    List<T> selectAll();
    
    /**
     * 查询总条数
     *
     * @param baseQuery 当前页和每页条数
     * @return 总条数
     */
    Long queryCount(BaseQuery baseQuery);
    
    /**
     * 分页查询
     *
     * @param baseQuery 当前页和每页条数
     * @return 查询结果
     */
    List<T> queryData(BaseQuery baseQuery);
    
    /**
     * 批量删除
     *
     * @param ids 要删除的id
     */
    void patchDelete(List<Long> ids);
}
