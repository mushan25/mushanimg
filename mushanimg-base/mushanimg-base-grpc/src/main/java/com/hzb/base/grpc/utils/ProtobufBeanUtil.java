package com.hzb.base.grpc.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.hzb.base.grpc.config.LocalDateAdapter;
import com.hzb.base.grpc.config.LocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author hzb
 * @date 2023/4/20 0:08
 */
public class ProtobufBeanUtil {
    /**
     * 将ProtoBean对象转化为POJO对象
     *
     * @param destPojoClass 目标POJO对象的类类型
     * @param sourceMessage 含有数据的ProtoBean对象实例
     * @param <PojoType> 目标POJO对象的类类型范型
     * @return
     * @throws IOException
     */
    public static <PojoType> PojoType toPojoBean(@NotNull Class<PojoType> destPojoClass, @NotNull Message sourceMessage)
            throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = JsonFormat.printer().print(sourceMessage);
        return gson.fromJson(json, destPojoClass);
    }

    /**
     * 将POJO对象转化为ProtoBean对象
     *
     * @param destBuilder 目标Message对象的Builder类
     * @param sourcePojoBean 含有数据的POJO对象
     * @return
     * @throws IOException
     */
    public static void toProtoBean(@NotNull Message.Builder destBuilder, @NotNull Object sourcePojoBean) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = gson.toJson(sourcePojoBean);
        JsonFormat.parser().ignoringUnknownFields().merge(json, destBuilder);
    }
}
