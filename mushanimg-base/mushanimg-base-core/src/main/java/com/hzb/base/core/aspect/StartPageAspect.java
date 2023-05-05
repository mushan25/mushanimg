package com.hzb.base.core.aspect;

import com.github.pagehelper.PageHelper;
import com.hzb.base.core.annotation.StartPage;
import com.hzb.base.core.utils.PageUtils;
import com.hzb.base.core.web.page.PageParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author: hzb
 * @Date: 2023/4/25
 */

@Aspect
@Component
public class StartPageAspect {


    /**
     * 构建
     */
    public StartPageAspect(){

    }

    @Around("@annotation(startPage)")
    public Object around(ProceedingJoinPoint point, StartPage startPage) throws Throwable {
        startPage(startPage.pageNum(), startPage.pageSize(), startPage.isAsc());
        return point.proceed();
    }

    public void startPage(int defaultPageNum, int defaultPageSize, String isAsc){
        PageParam pageParam = PageUtils.getPageParam();
        if (null != pageParam){
            Integer pageNum = pageParam.getPageNum();
            Integer pageSize = pageParam.getPageSize();
            String orderBy = pageParam.getOrderBy();
            PageHelper.startPage(null == pageNum ? defaultPageNum : pageNum, null == pageSize ? defaultPageSize : pageSize, orderBy).setReasonable(true);
        }else {
            PageHelper.startPage(defaultPageNum, defaultPageSize, isAsc).setReasonable(true);
        }
    }
}
