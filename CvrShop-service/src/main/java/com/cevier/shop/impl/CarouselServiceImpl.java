package com.cevier.shop.impl;

import com.cevier.shop.CarouselService;
import com.cevier.shop.manager.CarouselManager;
import com.cevier.shop.pojo.Carousel;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Resource
    private CarouselManager carouselManager;

    @Override
    public List<Carousel> getAllCarousel() {
        return carouselManager.getAllCarousel();
    }
}
