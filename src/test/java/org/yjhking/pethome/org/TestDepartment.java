package org.yjhking.pethome.org;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yjhking.pethome.PethomeApplication;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Department;
import org.yjhking.pethome.org.query.DepartmentQuery;
import org.yjhking.pethome.org.service.DepartmentService;

/**
 * @author YJH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PethomeApplication.class)
public class TestDepartment {
    @Autowired
    private DepartmentService departmentService;
    
    @Test
    public void testSelectAll() {
        departmentService.selectAll();
    }
    
    @Test
    public void testQueryData() {
        DepartmentQuery departmentQuery = new DepartmentQuery();
        departmentQuery.setPageSize(10);
        departmentQuery.setCurrentPage(1);
        PageList<Department> departmentPageList = departmentService.queryData(departmentQuery);
        System.out.println(departmentPageList);
    }
}
