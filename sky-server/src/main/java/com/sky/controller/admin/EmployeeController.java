package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@RequiredArgsConstructor
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {


    private final EmployeeService employeeService;

    private final JwtProperties jwtProperties;

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result<Void> save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("print employee information:{}", employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    /**
     * 查询员工
     */
    @GetMapping("/page")
    @ApiOperation("查询员工")
    public Result<PageResult> page(EmployeePageQueryDTO pageQueryDTO) {
        log.info(pageQueryDTO.toString());
        PageResult pageResult = employeeService.pageQuery(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 查询一个员工
     */
    @GetMapping("/{id}")
    @ApiOperation("查询一个员工")
    public Result<Employee> query(@PathVariable Long id) {
        return Result.success(employeeService.queryByOne(id));
    }

    /**
     * 修改员工
     */
    @PutMapping
    @ApiOperation("修改员工")
    public Result<Void> update(@RequestBody EmployeeDTO employeeDTO) {
        log.info(employeeDTO.toString());
        employeeService.update(employeeDTO);
        return Result.success();
    }

    /**
     * 启用/禁用员工账户
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用/禁用员工账户")
    public Result<Void> updateStatus(@PathVariable Integer status, @RequestParam Long id) {
        employeeService.updateStatus(status, id);
        return Result.success();
    }
}
