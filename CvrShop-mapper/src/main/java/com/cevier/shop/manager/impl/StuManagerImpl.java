package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.StuManager;
import com.cevier.shop.mapper.StuMapper;
import com.cevier.shop.pojo.Stu;
import org.springframework.stereotype.Repository;

@Repository
public class StuManagerImpl extends ServiceImpl<StuMapper, Stu> implements StuManager {
    @Override
    public Stu getById(int id) {
        return this.getBaseMapper().selectById(id);
    }
}
