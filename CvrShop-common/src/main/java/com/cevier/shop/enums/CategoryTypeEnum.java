package com.cevier.shop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryTypeEnum {
    TOP_LEVEL(1, "一级分类"),

    SECOND_LEVEL(2, "二级分类"),

    THIRD_LEVEL(3, "三级分类");

    private final Integer code;

    private final String desc;
}
