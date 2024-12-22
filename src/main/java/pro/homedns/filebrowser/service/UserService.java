package pro.homedns.filebrowser.service;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final String DEFAULT_ZONEINFO = "Europe/Helsinki";

    private final AuthenticationContext authContext;

    public UserService(AuthenticationContext authContext) {
        this.authContext = authContext;
    }

    public String userName() {
        return authContext.getAuthenticatedUser(DefaultOidcUser.class)
                .map(DefaultOidcUser::getFullName)
                .orElse(Strings.EMPTY);
    }

    public String zoneInfo() {
        return authContext.getAuthenticatedUser(DefaultOidcUser.class)
                .map(DefaultOidcUser::getZoneInfo)
                .orElse(DEFAULT_ZONEINFO);
    }
}
