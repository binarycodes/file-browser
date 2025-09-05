package pro.homedns.filebrowser.config;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.util.StringUtils;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig extends VaadinWebSecurity {
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    private static final String ROLES_CLAIM = "roles";
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String SCOPE_PREFIX = "SCOPE_";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.oauth2Login(Customizer.withDefaults());
        super.configure(http);
    }

    @Bean
    public CurrentUser currentUser(final SecurityContextHolderStrategy securityContextHolderStrategy) {
        return new CurrentUser(securityContextHolderStrategy);
    }

    @Bean
    protected OidcUserService oidcUserService() {
        final var userService = new OidcUserService();
        userService.setOidcUserMapper(SecurityConfig::mapOidcUser);
        return userService;
    }

    private static OidcUser mapOidcUser(final OidcUserRequest userRequest, final OidcUserInfo userInfo) {
        final var authorities = mapAuthorities(userRequest, userInfo);
        final var providerDetails = userRequest.getClientRegistration().getProviderDetails();
        final var userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
        final var oidcUser = StringUtils.hasText(userNameAttributeName)
                ? new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo, userNameAttributeName)
                : new DefaultOidcUser(authorities, userRequest.getIdToken(), userInfo);
        return new OidcUserAdapter(oidcUser);
    }

    private static Collection<? extends GrantedAuthority> mapAuthorities(final OidcUserRequest userRequest, final OidcUserInfo userInfo) {
        final var authorities = new LinkedHashSet<GrantedAuthority>();
        authorities.add(new OidcUserAuthority(userRequest.getIdToken(), userInfo));

        final var accessToken = userRequest.getAccessToken();
        for (final var scope : accessToken.getScopes()) {
            authorities.add(new SimpleGrantedAuthority(SCOPE_PREFIX + scope));
        }

        if (userInfo.hasClaim(RESOURCE_ACCESS_CLAIM)) {

            final var realmAccessClaim = MAPPER.convertValue(
                    userInfo.getClaimAsMap(REALM_ACCESS_CLAIM),
                    new TypeReference<Map<String, List<String>>>() {
                    });

            final var roles = realmAccessClaim.getOrDefault(ROLES_CLAIM, List.of());
            roles.stream().map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                    .forEach(authorities::add);
        }

        if (userInfo.hasClaim(RESOURCE_ACCESS_CLAIM)) {
            final var resourceAccessClaim = MAPPER.convertValue(
                    userInfo.getClaimAsMap(RESOURCE_ACCESS_CLAIM),
                    new TypeReference<Map<String, Map<String, List<String>>>>() {
                    });

            final var clientRegistration = userRequest.getClientRegistration();
            final var clientId = clientRegistration.getClientId();
            final var clientResources = resourceAccessClaim.getOrDefault(clientId, Map.of());
            final var roles = clientResources.getOrDefault(ROLES_CLAIM, List.of());
            roles.stream().map(s -> s)
                    .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                    .forEach(authorities::add);
        }

        return authorities;
    }
}
