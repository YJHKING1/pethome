package org.yjhking.pethome.org;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yjhking.pethome.PethomeApplication;
import org.yjhking.pethome.basic.util.PageList;
import org.yjhking.pethome.org.domain.Employee;
import org.yjhking.pethome.org.query.EmployeeQuery;
import org.yjhking.pethome.org.service.EmployeeService;

import java.util.List;

/**
 * @author YJH
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PethomeApplication.class)
public class TestEmployee {
    @Autowired
    private EmployeeService employeeService;
    
    @Test
    public void testSelectAll() {
        List<Employee> employees = employeeService.selectAll();
        employees.forEach(System.out::println);
    }
    
    @Test
    public void testQueryData() {
        EmployeeQuery employeeQuery = new EmployeeQuery();
        employeeQuery.setPageSize(10);
        employeeQuery.setCurrentPage(1);
        PageList<Employee> employeePageList = employeeService.queryData(employeeQuery);
        System.out.println(employeePageList);
    }
}