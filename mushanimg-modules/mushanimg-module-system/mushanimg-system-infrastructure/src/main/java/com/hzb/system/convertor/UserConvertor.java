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
        return userDO;
    }

    public static UserDO toDataObjectForCreate(User user){
        UserDO userDO = toDataObject(user);
        return userDO;
    }

    public static UserDO toDataObjectForUpdate(User user){
        UserDO userDO = toDataObject(user);
        return userDO;
    }
}
