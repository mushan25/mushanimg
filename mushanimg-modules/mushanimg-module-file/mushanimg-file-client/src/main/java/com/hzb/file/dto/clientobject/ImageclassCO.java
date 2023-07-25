package com.hzb.file.dto.clientobject;

import com.alibaba.cola.dto.ClientObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author: hzb
 * @Date: 2023/6/1
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ImageclassCO extends ClientObject {
    private Long id;
    private String imgclassName;
    @JsonIgnore
    private Long userId;
    private LocalDateTime createTime;
}
