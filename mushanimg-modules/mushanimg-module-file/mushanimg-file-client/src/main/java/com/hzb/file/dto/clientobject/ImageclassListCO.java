package com.hzb.file.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageclassListCO extends ClientObject {
    private Long id;
    private String imgclassName;
}
