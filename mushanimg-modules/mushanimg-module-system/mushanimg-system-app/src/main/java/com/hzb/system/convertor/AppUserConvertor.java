package com.hzb.system.convertor;

import com.hzb.system.domain.user.model.entities.User;
import com.hzb.system.user.dto.clientobject.UserCO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "password", source = "password.password")
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
}
