package com.offcn.user.controller;

import com.offcn.user.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "此处是测试的控制层--swagger")
public class MyController {
    @ApiOperation(value = "这是一个hello的方法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "姓名",name = "name",required = true)
    })
    @GetMapping("/hello")
    public String hello(String name){
        return "success:" + name;
    }
    @ApiOperation(value = "这是一个保存用户的的方法")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(value = "姓名",name = "name",required = true),
            @ApiImplicitParam(value = "邮箱",name = "emain")
    })
    @GetMapping("/save")
    public User saveUser(String name,String email){
        User user = new User();
        user.setName("哈哈");
        user.setEmail("123456");
        return user;
    }
}
