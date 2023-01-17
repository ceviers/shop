package com.cevier.shop.impl;

import com.cevier.shop.CategoryService;
import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.manager.CategoryManager;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryManager categoryManager;

    @Override
    public List<Category> getCategories(CategoryTypeEnum categoryType) {
        return categoryManager.getCategories(categoryType);
    }

    @Override
    public List<CategoryVO> getCategoriesByFatherId(Integer fatherId) {
        return categoryManager.getCategoryByFatherId(fatherId);
    }
}
