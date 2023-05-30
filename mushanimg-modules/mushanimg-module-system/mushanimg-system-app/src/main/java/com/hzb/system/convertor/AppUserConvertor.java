package com.hzb.system.convertor;

import com.hzb.base.security.form.LoginUser;
import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.dto.clientobject.LoginUserInfoCO;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author: hzb
 * @Date: 2023/5/16
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AppUserConvertor {
    AppUserConvertor INSTANCT = Mappers.getMapper(AppUserConvertor.class);

    /**
     * user2CO
     * @param user User
     * @return UserCO
     */
    UserCO user2CO(User user);

    /**
     * CO2User
     * @param userCO UserCO
     * @return User
     */
    User CO2User(UserCO userCO);

    /**
     * userList2CO
     * @param userList List<User>
     * @return List<UserCO>
     */
    List<UserCO> userList2CO(List<User> userList);

    /**
     * loginUser2LoginUserInfoCO
     * @param loginUser LoginUser
     * @return LoginUserInfoCO
     */
    LoginUserInfoCO loginUser2LoginUserInfoCO(LoginUser loginUser);
}
