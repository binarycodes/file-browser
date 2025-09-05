import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {FileBrowserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";
import {Grid, GridColumn, Icon} from "@vaadin/react-components";
import {useEffect} from "react";
import {formatDate} from "Frontend/util/dateFormat";

import folderIcon from "@icons/icons8-folder.svg?url";
import fileIcon from "@icons/icons8-file.svg?url";

export const config: ViewConfig = {
    title: 'File Browser',
    loginRequired: true,
};

export default function FileBrowserView() {
    const fileItems = useSignal<FileItem[]>([]);

    useEffect(() => {
        FileBrowserService.getRootLevelItems()
            .then(items => fileItems.value = items);
    }, []);

    const visitItem = (item: FileItem) => {
        if (!item.directory) return;

        FileBrowserService.getPathItems(item.path)
            .then(items => fileItems.value = items);
    };

    return <>
        <Grid items={fileItems.value}>
            <GridColumn path="displayName" renderer={
                ({item}) => (
                    <div className={"flex gap-s items-center"} onClick={() => visitItem(item)}>
                        <Icon src={item.directory ? folderIcon : fileIcon}/>
                        <span>{item.displayName}</span>
                    </div>
                )
            }/>
            <GridColumn path="fileSize"/>
            <GridColumn path="lastModifiedOn" renderer={
                ({item}) => formatDate(item.lastModifiedOn)
            }/>
        </Grid>
    </>;
}