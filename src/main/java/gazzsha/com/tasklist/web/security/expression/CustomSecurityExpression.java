package gazzsha.com.tasklist.web.security.expression;

import gazzsha.com.tasklist.domain.user.Role;
import gazzsha.com.tasklist.service.UserService;
import gazzsha.com.tasklist.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {
    private final UserService userService;

    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();
        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication authentication, Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority))
                return true;
        }
        return false;
    }

    public boolean canAccessTask(Long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) authentication.getPrincipal();
        Long userId = user.getId();
        return userService.isTaskOwner(userId, taskId);
    }
}
