package com.hzb.system.convertor;

import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.gatewayimpl.database.dataobject.UserDO;
import org.springframework.beans.BeanUtils;

/**
 * @author: hzb
 * @Date: 2023/4/17
 */
public class UserConvertor {
    public static UserDO toDataObject(User user){
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(user, userDO);
        userDO.setPassword(user.getPassword().getPassword());
        return userDO;
    }

    public static UserDO toDataObjectForCreate(User user){
        return toDataObject(user);
    }

    public static UserDO toDataObjectForUpdate(User user){
        return toDataObject(user);
    }
}
