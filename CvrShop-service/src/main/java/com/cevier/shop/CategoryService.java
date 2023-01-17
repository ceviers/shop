package com.cevier.shop;

import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories(CategoryTypeEnum categoryType);

    List<CategoryVO> getCategoriesByFatherId(Integer fatherId);
}
