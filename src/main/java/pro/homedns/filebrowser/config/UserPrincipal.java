package pro.homedns.filebrowser.config;


import java.security.Principal;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface UserPrincipal extends Principal {

    UserInfo getUser();

    @Override
    default String getName() {
        return getUser().getUserId().toString();
    }

    Collection<? extends GrantedAuthority> getAuthorities();
}
