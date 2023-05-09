package com.hzb.base.core.utils;

import com.github.pagehelper.PageInfo;
import com.hzb.base.core.domain.CustomPageInfo;
import com.hzb.base.core.web.page.PageParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/25
 */
public class PageUtils {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    public static PageParam getPageParam(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null != attributes){
            PageParam pageParam = new PageParam();
            String pageNum = attributes.getRequest().getParameter(PAGE_NUM);
            String pageSize = attributes.getRequest().getParameter(PAGE_SIZE);
            pageParam.setPageNum(null == pageNum ? null : Integer.parseInt(pageNum))
                    .setPageSize(null == pageSize ? null : Integer.parseInt(pageSize))
                    .setOrderByColumn(attributes.getRequest().getParameter(ORDER_BY_COLUMN))
                    .setIsAsc(attributes.getRequest().getParameter(IS_ASC));
            return pageParam;
        }
        return null;
    }

    public static String camelToSnake(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([A-Z]+)", "_$1").toLowerCase();
    }

    public static CustomPageInfo getPageInfo(List<?> list){
        PageInfo<?> pageInfo = new PageInfo<>(list);
        return new CustomPageInfo((int) pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
    }
}
