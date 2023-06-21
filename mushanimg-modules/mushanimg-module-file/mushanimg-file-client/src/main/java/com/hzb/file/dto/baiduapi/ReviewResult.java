package com.hzb.file.dto.baiduapi;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/6/19
 */
@Data
@Builder
public class ReviewResult {
    private Long logId;
    private Long errorCode;
    private String errorMsg;
    private String conclusion;
}
