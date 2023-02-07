package com.cevier.shop.controller;

import com.cevier.shop.CarouselService;
import com.cevier.shop.CategoryService;
import com.cevier.shop.enums.CategoryTypeEnum;
import com.cevier.shop.pojo.Carousel;
import com.cevier.shop.pojo.Category;
import com.cevier.shop.pojo.vo.CategoryVO;
import com.cevier.shop.pojo.vo.NewItemsVO;
import com.cevier.shop.utils.ApiJsonResult;
import com.cevier.shop.utils.JsonUtils;
import com.cevier.shop.utils.RedisOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "商店首页接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private CarouselService carouselService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisOperator redisOperator;

    @Operation(summary = "获取轮播图")
    @GetMapping("/carousel")
    public ApiJsonResult getCarousel() {
//        return ApiJsonResult.ok(carouselService.getAllCarousel());
        List<Carousel> list;
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {

            list = carouselService.getAllCarousel();
            redisOperator.set("carousel", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return ApiJsonResult.ok(list);
    }

    @Operation(summary = "获取所有一级分类")
    @GetMapping("/cats")
    public ApiJsonResult getTopLevelCategories() {
//        return ApiJsonResult.ok(categoryService.getCategories(CategoryTypeEnum.TOP_LEVEL));
        List<Category> list;
        String catsStr = redisOperator.get("cats");
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.getCategories(CategoryTypeEnum.TOP_LEVEL);
            redisOperator.set("cats", JsonUtils.objectToJson(list));
        } else {
            list = JsonUtils.jsonToList(catsStr, Category.class);
        }
        return ApiJsonResult.ok(list);
    }

    @Operation(summary = "获取所有子分类")
    @GetMapping("/subCat/{fatherId}")
    public ApiJsonResult getChildCategories(
            @Parameter(description = "一级分类ID") @PathVariable("fatherId") Integer fatherId) {
//        return ApiJsonResult.ok(categoryService.getCategoriesByFatherId(fatherId));
        List<CategoryVO> list;
        String catsStr = redisOperator.get("subCat:" + fatherId);
        if (StringUtils.isBlank(catsStr)) {
            list = categoryService.getCategoriesByFatherId(fatherId);

            /**
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (list != null && list.size() > 0) {
                redisOperator.set("subCat:" + fatherId, JsonUtils.objectToJson(list));
            } else {
                redisOperator.set("subCat:" + fatherId, JsonUtils.objectToJson(list), 5*60);
            }
        } else {
            list = JsonUtils.jsonToList(catsStr, CategoryVO.class);
        }
        return ApiJsonResult.ok(list);
    }

    @Operation(summary = "查询每个一级分类下的最新6条商品数据")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ApiJsonResult sixNewItems(
            @Parameter(description = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return ApiJsonResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return ApiJsonResult.ok(list);
    }
}
