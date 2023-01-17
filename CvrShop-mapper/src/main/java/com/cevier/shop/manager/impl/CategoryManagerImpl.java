package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.manager.CategoryManager;
import com.cevier.shop.mapper.CategoryMapper;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryManagerImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryManager  {
    @Override
    public List<Category> getCategories(CategoryTypeEnum categoryType) {
        return this.lambdaQuery().eq(categoryType != null, Category::getType, categoryType.getCode()).list();
    }

    @Override
    public List<CategoryVO> getCategoryByFatherId(Integer fatherCategoryId) {
        return this.baseMapper.getCategoryByFatherId(fatherCategoryId);
    }
}
