package com.sky.controller.user;


import com.sky.constant.RedisConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "用户端-营业状态")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserShopController {
    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/status")
    public Result<Object> nowStatus() {
        Object value = redisTemplate.opsForValue().get(RedisConstant.STATUS);
        return Result.success(value);
    }
}
