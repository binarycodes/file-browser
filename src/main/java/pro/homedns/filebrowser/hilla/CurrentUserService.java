package pro.homedns.filebrowser.hilla;

import jakarta.annotation.security.PermitAll;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import pro.homedns.filebrowser.config.CurrentUser;
import pro.homedns.filebrowser.domain.UserInfo;

import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@PermitAll
public class CurrentUserService {

    private final CurrentUser currentUser;

    public CurrentUserService(final CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public @NonNull UserInfo getUserInfo() {
        final var principal = currentUser.requirePrincipal();
        final var user = principal.getUser();
        final var authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return new UserInfo(user.getUserId().toString(),
                user.getPreferredUsername(),
                user.getFullName(),
                user.getProfileUrl(),
                user.getPictureUrl(),
                user.getEmail(),
                user.getZoneId().toString(),
                user.getLocale().toString(),
                authorities);
    }
}
