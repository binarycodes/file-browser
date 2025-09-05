package pro.homedns.filebrowser.model;

import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class FileItem {
    @EqualsAndHashCode.Include
    private String path;
    private boolean isDirectory;
    private UserPrincipal owner;
    private Set<PosixFilePermission> permission;
    private FileTime lastModifiedOn;
    private Long fileSize;
}
