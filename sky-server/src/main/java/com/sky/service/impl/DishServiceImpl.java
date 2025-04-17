package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DishServiceImpl implements DishService {
    private final DishMapper dishMapper;
    private final SetMealDishMapper setMealDishMapper;
    private final DishFlavorMapper dishFlavorMapper;

    @Override
    public void add(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //设置默认状态
        dish.setStatus(StatusConstant.DISABLE);
        //需要返回主键值
        dishMapper.add(dish);
        Long id = dish.getId();

        dishFlavorMapper.addBatch(id, dishDTO.getFlavors());
    }

    @Override
    public PageResult page(DishPageQueryDTO dishQuery) {
        PageHelper.startPage(dishQuery.getPage(), dishQuery.getPageSize());
        Page<Dish> page = dishMapper.query(dishQuery);
        long total = page.getTotal();
        List<Dish> result = page.getResult();
        return new PageResult(total, result);
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(List<Long> ids) {
        //起售商品不能删除
        List<Dish> dishList = dishMapper.selectByList(ids);
        List<Long> collectDel = dishList.stream().filter(dish -> dish.getStatus() == 1)
                .map(Dish::getId).collect(Collectors.toList());
        if (!collectDel.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        //被套餐关联的商品不能删除
        List<Long> dishIds = setMealDishMapper.getMealIdsByDishIds(ids);
        if (!dishIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品关联菜品也进行删除
        dishMapper.deleteBatch(ids);
        dishFlavorMapper.delBatch(ids);
    }

    @Override
    public DishVO selectByOne(Long id) {
        //先查对应菜品
        Dish dish = dishMapper.queryByOne(id);
        //再查对应口味
        List<DishFlavor> dishFlavorList = dishFlavorMapper.query(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishDTO
     */
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.DISABLE);
        dishMapper.update(dish);
        //空 有
        //有 有
        //有 空
        //空 空
        if (!dishDTO.getFlavors().isEmpty()) {
            dishFlavorMapper.delBatch(Arrays.asList(dish.getId()));
            dishFlavorMapper.addBatch(dishDTO.getId(), dishDTO.getFlavors());
        }

    }

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }
}
