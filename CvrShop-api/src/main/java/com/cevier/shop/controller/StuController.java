package com.cevier.shop.controller;

import com.cevier.shop.StuService;
import com.cevier.shop.pojo.Stu;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuController {
    @Resource
    private StuService stuService;

    @GetMapping("/stu/{id}")
    public Stu getStuById(@PathVariable("id") int id){
        return stuService.getStuInfo(id);
    }
}
