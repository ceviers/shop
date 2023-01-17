package com.cevier.shop.controller;

import com.cevier.shop.CarouselService;
import com.cevier.shop.CategoryService;
import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.utils.ApiJsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商店首页接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private CarouselService carouselService;

    @Resource
    private CategoryService categoryService;

    @Operation(summary = "获取轮播图")
    @GetMapping("/carousel")
    public ApiJsonResult getCarousel() {
        return ApiJsonResult.ok(carouselService.getAllCarousel());
    }

    @Operation(summary = "获取所有一级分类")
    @GetMapping("/cats")
    public ApiJsonResult getTopLevelCategories() {
        return ApiJsonResult.ok(categoryService.getCategories(CategoryTypeEnum.TOP_LEVEL));
    }

    @Operation(summary = "获取所有子分类")
    @GetMapping("/subCat/{fatherId}")
    public ApiJsonResult getChildCategories(
            @Parameter(description = "一级分类ID") @PathVariable("fatherId") Integer fatherId) {
        return ApiJsonResult.ok(categoryService.getCategoriesByFatherId(fatherId));
    }
}
