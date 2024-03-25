package com.exampl.tasklist.web.security.expression;

import com.exampl.tasklist.domain.user.Role;
import com.exampl.tasklist.service.UserService;
import com.exampl.tasklist.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;


    public boolean canAccessUser(Long id) {
        //Получаем authentication до контроллера
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal();

        //Получаем id authentication пользователя
        Long usedId = user.getId();

        //Вернет правду если это и есть данный пользователь, либо он админ
        return usedId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);

    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            //Если данные пользователя из authentication содержит в себе authority с именем роли админ, то правда
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }


}
