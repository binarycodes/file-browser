package pro.homedns.filebrowser.model;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.time.Instant;
import java.util.Set;

public record FileItem(
        Path path,
        boolean isDirectory,
        UserPrincipal owner,
        Set<PosixFilePermission> permission,
        FileTime lastModifiedOn,
        Long fileSize
) {

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileItem item) {
            return item.path().equals(this.path());
        }
        return false;
    }
}
