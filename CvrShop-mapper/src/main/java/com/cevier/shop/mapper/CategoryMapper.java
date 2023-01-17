package com.cevier.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryVO> getCategoryByFatherId(@Param("fatherId") Integer fatherCategoryId);
}