package pro.homedns.filebrowser.domain;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String userId;
    private String preferredUsername;
    private String fullName;
    private String profileUrl;
    private String pictureUrl;
    private String email;
    private String zoneId;
    private String locale;
    private Collection<String> authorities;
}
