package com.hzb.file.factory;

import com.hzb.base.core.annotation.AccessModeAnnotation;
import com.hzb.base.core.enums.AccessMode;
import com.hzb.base.core.utils.SpringUtils;
import com.hzb.file.executor.service.ImageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: hzb
 * @Date: 2023/6/14
 */
@Slf4j
public class AnnotationAccessStrategyFactory {
    static Map<AccessMode, ImageStrategy> accessStrategyMap = new HashMap<>();

    static {
        registerAccessStrategy();
    }

    public static ImageStrategy getAccessStrategy(AccessMode accessMode) {
        if (accessStrategyMap.containsKey(accessMode)) {
            return accessStrategyMap.get(accessMode);
        } else {
            throw new RuntimeException("没有找到对应的策略");
        }
    }

    private static void registerAccessStrategy() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.hzb.file.executor.command"))
                .setScanners(new SubTypesScanner()));
        Set<Class<? extends ImageStrategy>> taxStrategyClassSet = reflections.getSubTypesOf(ImageStrategy.class);
        if (taxStrategyClassSet != null){
            for (Class<?> clazz : taxStrategyClassSet) {
                if (clazz.isAnnotationPresent(AccessModeAnnotation.class)) {
                    AccessModeAnnotation taxTypeAnnotation = clazz.getAnnotation(AccessModeAnnotation.class);
                    AccessMode accessMode = taxTypeAnnotation.taxType();
                    accessStrategyMap.put(accessMode, (ImageStrategy) SpringUtils.getBean(clazz));
                }
            }
        }
    }
}
