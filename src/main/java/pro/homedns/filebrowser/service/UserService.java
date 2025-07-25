package pro.homedns.filebrowser.service;

import java.time.ZoneId;
import java.util.TimeZone;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.security.AuthenticationContext;

@Service
public class UserService {
    private static final String DEFAULT_ZONEINFO = "Europe/Helsinki";

    private final AuthenticationContext authContext;

    public UserService(final AuthenticationContext authContext) {
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

    public ZoneId zoneId() {
        return TimeZone.getTimeZone(this.zoneInfo()).toZoneId();
    }
}
