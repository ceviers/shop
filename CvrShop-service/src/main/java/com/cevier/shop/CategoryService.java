package com.cevier.shop;

import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;
import com.cevier.shop.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    List<Category> getCategories(CategoryTypeEnum categoryType);

    List<CategoryVO> getCategoriesByFatherId(Integer fatherId);

    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
