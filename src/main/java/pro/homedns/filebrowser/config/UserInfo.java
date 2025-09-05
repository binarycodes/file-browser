package pro.homedns.filebrowser.config;

import java.time.ZoneId;
import java.util.Locale;

import org.jspecify.annotations.Nullable;
import pro.homedns.filebrowser.domain.UserId;

public interface UserInfo {

    UserId getUserId();

    String getPreferredUsername();

    default String getFullName() {
        return getPreferredUsername();
    }

    default @Nullable String getProfileUrl() {
        return null;
    }

    default @Nullable String getPictureUrl() {
        return null;
    }

    default @Nullable String getEmail() {
        return null;
    }

    default ZoneId getZoneId() {
        return ZoneId.systemDefault();
    }

    default Locale getLocale() {
        return Locale.getDefault();
    }
}
