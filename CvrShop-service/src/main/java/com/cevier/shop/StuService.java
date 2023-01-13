package com.cevier.shop;

import com.cevier.shop.pojo.Stu;

public interface StuService {
    Stu getStuInfo(int id);

    void saveStu();

    void updateStu(int id);

    void deleteStu(int id);
}
