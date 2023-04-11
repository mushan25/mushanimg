package com.hzb.ucenter.model.dto;

import com.hzb.ucenter.model.po.MsUser;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/11
 */
@Data
@ToString
@Accessors(chain = true)
public class MsUserExt extends MsUser {
    /**
     * 用户权限
     */
    List<String> permissions = new ArrayList<>();
}
