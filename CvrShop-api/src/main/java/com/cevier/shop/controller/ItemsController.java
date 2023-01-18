package com.cevier.shop.controller;

import com.cevier.shop.ItemService;
import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;
import com.cevier.shop.pojo.vo.CommentLevelCountsVO;
import com.cevier.shop.pojo.vo.ItemInfoVO;
import com.cevier.shop.utils.ApiJsonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "商品信息展示的相关接口")
@RequestMapping("items")
public class ItemsController {
    @Resource
    private ItemService itemService;

    @Operation(summary = "查询商品详情")
    @GetMapping("/info/{itemId}")
    public ApiJsonResult info(
            @Parameter(description = "商品id", required = true)
            @PathVariable String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ApiJsonResult.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return ApiJsonResult.ok(itemInfoVO);
    }

    @Operation(summary = "查询商品评价等级")
    @GetMapping("/commentLevel")
    public ApiJsonResult commentLevel(
            @Parameter(description = "商品id", required = true)
            @RequestParam String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return ApiJsonResult.errorMsg(null);
        }

        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);

        return ApiJsonResult.ok(countsVO);
    }

//    @Operation(summary = "查询商品评论")
//    @GetMapping("/comments")
//    public ApiJsonResult comments(
//            @Parameter(description = "商品id", required = true)
//            @RequestParam String itemId,
//            @Parameter(description = "评价等级", required = false)
//            @RequestParam Integer level,
//            @Parameter(description = "查询下一页的第几页", required = false)
//            @RequestParam Integer page,
//            @Parameter(description = "分页的每一页显示的条数", required = false)
//            @RequestParam Integer pageSize) {
//
//        if (StringUtils.isBlank(itemId)) {
//            return ApiJsonResult.errorMsg(null);
//        }
//
//        if (page == null) {
//            page = 1;
//        }
//
//        if (pageSize == null) {
//            pageSize = COMMON_PAGE_SIZE;
//        }
//
//        PagedGridResult grid = itemService.queryPagedComments(itemId,
//                level,
//                page,
//                pageSize);
//
//        return ApiJsonResult.ok(grid);
//    }
//
//    @Operation(summary = "搜索商品列表")
//    @GetMapping("/search")
//    public ApiJsonResult search(
//            @Parameter(description = "关键字", required = true)
//            @RequestParam String keywords,
//            @Parameter(description = "排序", required = false)
//            @RequestParam String sort,
//            @Parameter(description = "查询下一页的第几页", required = false)
//            @RequestParam Integer page,
//            @Parameter(description = "分页的每一页显示的条数", required = false)
//            @RequestParam Integer pageSize) {
//
//        if (StringUtils.isBlank(keywords)) {
//            return ApiJsonResult.errorMsg(null);
//        }
//
//        if (page == null) {
//            page = 1;
//        }
//
//        if (pageSize == null) {
//            pageSize = PAGE_SIZE;
//        }
//
//        PagedGridResult grid = itemService.searhItems(keywords,
//                sort,
//                page,
//                pageSize);
//
//        return ApiJsonResult.ok(grid);
//    }
//
//    @Operation(summary = "通过分类id搜索商品列表")
//    @GetMapping("/catItems")
//    public ApiJsonResult catItems(
//            @Parameter(description = "三级分类id", required = true)
//            @RequestParam Integer catId,
//            @Parameter(description = "排序", required = false)
//            @RequestParam String sort,
//            @Parameter(description = "查询下一页的第几页", required = false)
//            @RequestParam Integer page,
//            @Parameter(description = "分页的每一页显示的条数", required = false)
//            @RequestParam Integer pageSize) {
//
//        if (catId == null) {
//            return ApiJsonResult.errorMsg(null);
//        }
//
//        if (page == null) {
//            page = 1;
//        }
//
//        if (pageSize == null) {
//            pageSize = PAGE_SIZE;
//        }
//
//        PagedGridResult grid = itemService.searhItems(catId,
//                sort,
//                page,
//                pageSize);
//
//        return ApiJsonResult.ok(grid);
//    }
//
//    // 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
//    @Operation(summary = "根据商品规格ids查找最新的商品数据")
//    @GetMapping("/refresh")
//    public ApiJsonResult refresh(
//            @Parameter(description = "拼接的规格ids", required = true)
//            @RequestParam String itemSpecIds) {
//
//        if (StringUtils.isBlank(itemSpecIds)) {
//            return ApiJsonResult.ok();
//        }
//
//        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);
//
//        return ApiJsonResult.ok(list);
//    }
}
