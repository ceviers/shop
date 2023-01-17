package com.cevier.shop.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cevier.shop.pojo.Carousel;

import java.util.List;

public interface CarouselManager extends IService<Carousel> {

    /**
     * 获取所有轮播图
     */
    List<Carousel> getAllCarousel();

    /**
     * 查询轮播图
     */
    List<Carousel> getCarousel(Integer isShow);
}
