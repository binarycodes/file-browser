package pro.homedns.filebrowser.domain;

import java.io.Serializable;

import lombok.EqualsAndHashCode;

import static java.util.Objects.requireNonNull;

@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public final class UserId implements Serializable {

    @EqualsAndHashCode.Include
    private final String userId;

    private UserId(final String userId) {
        this.userId = requireNonNull(userId);
    }

    public static UserId of(final String userId) {
        return new UserId(userId);
    }

    @Override
    public String toString() {
        return userId;
    }
}
