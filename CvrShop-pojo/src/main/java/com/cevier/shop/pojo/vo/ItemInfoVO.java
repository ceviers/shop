package com.cevier.shop.pojo.vo;

import com.cevier.shop.pojo.Items;
import com.cevier.shop.pojo.ItemsImg;
import com.cevier.shop.pojo.ItemsParam;
import com.cevier.shop.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

@Data
public class ItemInfoVO {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}
