package com.huike.common.core.dto;

import lombok.Data;

@Data
public class PageRequestDto {

    protected Integer pageSize = 10;
    protected Integer pageNum = 1;

    public void checkParam() {
        if (this.pageNum == null || this.pageNum <= 0) {
            setPageNum(1);
        }
        if (this.pageSize == null || this.pageSize <= 0 || this.pageSize > 100) {
            setPageSize(10);
        }
    }
}
