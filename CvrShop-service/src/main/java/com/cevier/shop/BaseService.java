package com.cevier.shop;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cevier.shop.utils.PagedGridResult;

public class BaseService {
    public PagedGridResult setterPagedGrid(IPage<?> list) {
        PagedGridResult grid = new PagedGridResult();
        grid.setPage((int)list.getCurrent());
        grid.setRows(list.getRecords());
        grid.setTotal((int)(Math.ceil(list.getTotal() / list.getSize())));
        grid.setRecords((int)list.getTotal());
        return grid;
    }
}
