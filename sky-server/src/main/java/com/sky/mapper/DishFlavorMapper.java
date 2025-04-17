package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 根据菜品批量删除口味
     *
     * @param ids
     */
    void delBatch(List<Long> ids);

    @Select("select id,dish_id,name,value from dish_flavor where dish_id=#{id}")
    List<DishFlavor> query(Long id);

    /**
     * 批量新增
     *
     * @param id
     * @param flavors
     */
    void addBatch(@Param("id") Long id, @Param("flavors") List<DishFlavor> flavors);

}
