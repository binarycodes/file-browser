package pro.homedns.filebrowser.config;

import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolderStrategy;

import static java.util.Objects.requireNonNull;

@Log4j2
public class CurrentUser {

    private final SecurityContextHolderStrategy securityContextHolderStrategy;

    public CurrentUser(final SecurityContextHolderStrategy securityContextHolderStrategy) {
        this.securityContextHolderStrategy = requireNonNull(securityContextHolderStrategy);
    }

    public Optional<UserInfo> get() {
        return getPrincipal().map(UserPrincipal::getUser);
    }

    public Optional<UserPrincipal> getPrincipal() {
        return Optional.ofNullable(
                getPrincipalFromAuthentication(securityContextHolderStrategy.getContext().getAuthentication()));
    }

    private @Nullable UserPrincipal getPrincipalFromAuthentication(@Nullable final Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        final var principal = authentication.getPrincipal();

        if (principal instanceof final UserPrincipal userPrincipal) {
            return userPrincipal;
        }

        log.warn("Unexpected principal type: {}", principal.getClass().getName());

        return null;
    }

    public UserInfo require() {
        return get().orElseThrow(() -> new AuthenticationCredentialsNotFoundException("No current user"));
    }

    public UserPrincipal requirePrincipal() {
        return getPrincipal().orElseThrow(() -> new AuthenticationCredentialsNotFoundException("No current user"));
    }
}
