package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api("菜品相关接口")
@Slf4j
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info(dishDTO.toString());
        dishService.add(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        return Result.success(dishService.page(dishPageQueryDTO));
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result del(@RequestParam List<Long> ids) {
        dishService.del(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取当前菜品信息")
    public Result<DishVO> detail(@PathVariable Long id) {
        return Result.success(dishService.selectByOne(id));
    }

    @PutMapping
    @ApiOperation("菜品信息修改")
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result status(@PathVariable Integer status, @RequestParam Long id) {
        dishService.updateStatus(status,id);
        return Result.success();
    }
}
