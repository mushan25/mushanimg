package com.hzb.base.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: hzb
 * @Date: 2023/5/9
 */
@Data
@AllArgsConstructor
public class CustomPageInfo {
    private Integer total;
    private Integer pageSize;
    private Integer pageNum;
}
