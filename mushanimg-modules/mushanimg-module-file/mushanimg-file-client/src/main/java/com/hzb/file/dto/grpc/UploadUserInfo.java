package com.hzb.file.dto.grpc;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/6/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadUserInfo extends Command {
    private String userName;
    private Integer uploadSize;
}
