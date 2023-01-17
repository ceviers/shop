package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryManager extends IService<Category> {

    /**
     * 根据分类等级查询分类
     * @param categoryType 分类等级
     */
    List<Category> getCategories(CategoryTypeEnum categoryType);

    List<CategoryVO> getCategoryByFatherId(Integer fatherCategoryId);
}
