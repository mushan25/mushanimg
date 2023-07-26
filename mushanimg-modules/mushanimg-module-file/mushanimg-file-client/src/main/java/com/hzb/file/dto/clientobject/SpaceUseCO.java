package com.hzb.file.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/7/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class SpaceUseCO extends ClientObject {
    private String totalSize;
    private String usedSize;
    private Double usedPercent;
}
