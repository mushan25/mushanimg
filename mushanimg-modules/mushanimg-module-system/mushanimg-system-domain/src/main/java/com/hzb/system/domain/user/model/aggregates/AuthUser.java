package com.hzb.system.domain.user.model.aggregates;

import com.hzb.system.domain.user.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * @author: hzb
 * @Date: 202   3/4/19
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 4124066294343073186L;
    private User user;
    private Set<String> roleKeys;
    private Set<String> permissions;

    private Set<String> routes;
}
