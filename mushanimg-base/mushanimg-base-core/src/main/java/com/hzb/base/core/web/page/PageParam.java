package com.hzb.base.core.web.page;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: hzb
 * @Date: 2023/4/24
 */
@Data
public class PageParam {
    /** 当前记录起始索引 */
    private Integer pageNum;

    /** 每页显示记录数 */
    private Integer pageSize;

    /** 排序列 */
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    private String isAsc = "asc";

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return orderByColumn + " " + isAsc;
    }

}
