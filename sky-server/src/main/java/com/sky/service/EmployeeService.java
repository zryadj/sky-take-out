package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增与员工
     *
     * @param employeeDTO
     */
    void add(EmployeeDTO employeeDTO);

    /**
     * 查询员工
     * @param pageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO pageQueryDTO);

    /**
     * 查询单个员工
     *
     * @param id
     * @return
     */
    Employee queryByOne(Long id);
}
