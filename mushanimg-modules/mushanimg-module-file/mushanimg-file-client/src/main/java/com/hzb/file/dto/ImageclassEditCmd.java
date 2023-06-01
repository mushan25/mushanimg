package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import com.hzb.file.dto.clientobject.ImageclassCO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageclassEditCmd extends Command {
    private ImageclassCO imageclassCO;
}
