package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Stu;

public interface StuManager extends IService<Stu> {
    Stu getById(int id);
}
