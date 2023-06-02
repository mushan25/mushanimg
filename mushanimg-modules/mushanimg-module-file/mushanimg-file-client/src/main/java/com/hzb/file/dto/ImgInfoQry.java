package com.hzb.file.dto;

import com.alibaba.cola.dto.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class ImgInfoQry extends Command {
    /**
     * id
     */
    private Long id;

    /**
     *
     */
    private Long userId;
}
