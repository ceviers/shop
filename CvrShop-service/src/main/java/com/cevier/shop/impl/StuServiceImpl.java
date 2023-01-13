package com.cevier.shop.impl;

import com.cevier.shop.StuService;
import com.cevier.shop.mapper.StuMapper;
import com.cevier.shop.pojo.Stu;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {

    @Resource
    private StuMapper stuMapper;

    @Override
    public Stu getStuInfo(int id) {
        return stuMapper.selectById(id);
    }

    @Override
    public void saveStu() {

    }

    @Override
    public void updateStu(int id) {

    }

    @Override
    public void deleteStu(int id) {

    }
}
