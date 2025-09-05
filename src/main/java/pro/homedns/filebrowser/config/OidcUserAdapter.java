package pro.homedns.filebrowser.config;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import pro.homedns.filebrowser.domain.UserId;

import static java.util.Objects.requireNonNull;

public final class OidcUserAdapter implements OidcUser, UserPrincipal {

    private final OidcUser delegate;
    private final UserInfo appUserInfo;

    public OidcUserAdapter(final OidcUser oidcUser) {
        this.delegate = requireNonNull(oidcUser);
        this.appUserInfo = createAppUserInfo(oidcUser);
    }

    private static UserInfo createAppUserInfo(final OidcUser oidcUser) {
        return new UserInfo() {
            private final UserId userId = UserId.of(oidcUser.getSubject());
            private final String preferredUsername = requireNonNull(oidcUser.getPreferredUsername());
            private final String fullName = requireNonNull(oidcUser.getFullName());
            private final ZoneId zoneId = parseZoneInfo(oidcUser.getZoneInfo());
            private final Locale locale = parseLocale(oidcUser.getLocale());

            @Override
            public UserId getUserId() {
                return userId;
            }

            @Override
            public String getPreferredUsername() {
                return preferredUsername;
            }

            @Override
            public String getFullName() {
                return fullName;
            }

            @Override
            public @Nullable String getProfileUrl() {
                return oidcUser.getProfile();
            }

            @Override
            public @Nullable String getPictureUrl() {
                return oidcUser.getPicture();
            }

            @Override
            public @Nullable String getEmail() {
                return oidcUser.getEmail();
            }

            @Override
            public ZoneId getZoneId() {
                return zoneId;
            }

            @Override
            public Locale getLocale() {
                return locale;
            }
        };
    }


    static ZoneId parseZoneInfo(@Nullable final String zoneInfo) {
        if (zoneInfo == null) {
            return ZoneId.systemDefault();
        }
        try {
            return ZoneId.of(zoneInfo);
        } catch (final DateTimeException e) {
            return ZoneId.systemDefault();
        }
    }

    static Locale parseLocale(@Nullable final String locale) {
        if (locale == null) {
            return Locale.getDefault();
        }
        return Locale.forLanguageTag(locale);
    }

    @Override
    public UserInfo getUser() {
        return appUserInfo;
    }

    @Override
    public Map<String, Object> getClaims() {
        return delegate.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return delegate.getUserInfo();
    }

    @Override
    public OidcIdToken getIdToken() {
        return delegate.getIdToken();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return delegate.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return delegate.getAuthorities();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
