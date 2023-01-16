package com.cevier.shop.controller;

import com.cevier.shop.StuService;
import com.cevier.shop.pojo.Stu;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuController {
    @Resource
    private StuService stuService;

//    @Hidden注解与@Operation(hidden = true)效果相同
//    @Hidden
    @Operation(summary = "跟具ID查询stu", hidden = true)
    @GetMapping("/stu/{id}")
    public Stu getStuById(@PathVariable("id") int id){
        return stuService.getStuInfo(id);
    }
}
