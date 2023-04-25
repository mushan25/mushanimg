package com.hzb.system.domain.user.model.entities;

import com.alibaba.cola.domain.Entity;
import com.github.pagehelper.PageInfo;
import com.hzb.system.domain.user.model.valueobjects.Password;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */

@Data
@Entity
@Slf4j
public class User {
    private Long userId;

    private String userName;

    private String nickName;

    private String email;

    private String phoneNumber;

    private String sex;

    private String avatar;

    private Password password;

    private String status;

    private String delFlag;

    private String loginIp;

    private LocalDate loginDate;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;

    public User(Long userId, String updateBy, LocalDateTime updateTime){
        this.userId = userId;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

    public void setPassword(String password){
        this.password.setPassword(password);
    }
    public User(){
        this.password = new Password();
    }

    public Tuple3<Integer, Integer, Integer> getPageInfo(List<?> list){
        PageInfo<?> pageInfo = new PageInfo<>(list);
        return Tuples.of((int) pageInfo.getTotal(), pageInfo.getPageSize(), pageInfo.getPageNum());
    }
}
