package com.hzb.base.core.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author: hzb
 * @Date: 2023/4/20
 */
public class BeanCopyUtil extends BeanUtils {
    /**
     * <p>对象数据的拷贝</p>
     *
     * @param sources 数据源
     * @param target  目标参数类型(eg: UserVo:new)
     * @return
     */
    public static <S, T> T copyProperties(S sources, Supplier<T> target) {
        T t = target.get();
        copyProperties(sources, t);
        return t;
    }

    /**
     * 拷贝不为空的字段
     * 使用场景：只想把 sources中有值的拷贝到target中，而不把target对应sources中没有值的赋值为null
     * 使用 BeanUtils.copyProperties();会全量拷贝，包括null
     *
     * @param sources 数据源
     * @param target  目标
     * @param <S>     数据源泛型
     * @param <T>     目标泛型
     * @return 目标数据
     */
    public static <S, T> T copyNotEmptyProperties(S sources, T target) {
        copyProperties(sources, target, getNullPropertyNames(sources));
        return target;
    }


    /**
     * <p>集合数据的拷贝</p>
     *
     * @param sources 数据源
     * @param target  目标参数类型(eg: UserVo:new)
     * @return
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {

        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            list.add(t);
        }
        return list;
    }

    /**
     * 获取为空的字段
     */
    private static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[emptyNames.size()]);
    }
}
