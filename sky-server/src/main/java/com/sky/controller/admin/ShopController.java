package com.sky.controller.admin;

import com.sky.constant.RedisConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/shop")
@Api(tags = "商店状态")
@RequiredArgsConstructor
public class ShopController {
    private final RedisTemplate redisTemplate;


    @PutMapping("/{status}")
    @ApiOperation("店铺状态")
    public Result status(@PathVariable Integer status) {
        redisTemplate.opsForValue().set(RedisConstant.STATUS, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result nowStatus() {
        Object value = redisTemplate.opsForValue().get(RedisConstant.STATUS);
        return Result.success(value);
    }
}
