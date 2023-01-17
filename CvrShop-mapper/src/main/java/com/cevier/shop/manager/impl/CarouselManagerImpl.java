package com.cevier.shop.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cevier.shop.manager.CarouselManager;
import com.cevier.shop.mapper.CarouselMapper;
import com.cevier.shop.pojo.Carousel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarouselManagerImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselManager {
    @Override
    public List<Carousel> getAllCarousel() {
        return this.getCarousel(null);
    }

    @Override
    public List<Carousel> getCarousel(Integer isShow) {
        return this.lambdaQuery()
                .eq(isShow != null, Carousel::getIsShow, isShow)
                .orderByAsc(Carousel::getSort).list();
    }
}
