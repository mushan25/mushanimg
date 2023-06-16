package com.hzb.base.core.utils;

import java.io.File;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
public class FileUtils {
    private static final ThreadLocal<File> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String path) {
        File file = new File(path);
        if (!file.exists()) {
            CheckUtils.isTrue(!file.mkdirs(), "创建文件夹失败", "创建文件夹失败");
        }
        THREAD_LOCAL.set(file);
    }

    public static void deleteTempFile(File tempFile){
        if (null != tempFile && !tempFile.delete()) {
            tempFile.deleteOnExit();
        }
    }

    public static File get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
