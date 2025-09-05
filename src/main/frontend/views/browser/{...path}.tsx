import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {FileBrowserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";
import {Grid, GridColumn, Icon} from "@vaadin/react-components";
import {useEffect} from "react";
import {formatDate} from "Frontend/util/dateFormat";

import folderIcon from "@icons/icons8-folder.svg?url";
import fileIcon from "@icons/icons8-file.svg?url";

import {useNavigate, useParams} from "react-router";
import BreadCrumbs from "Frontend/components/BreadCrumbs";

export const config: ViewConfig = {
    title: 'File Browser',
    loginRequired: true,
};

export default function FileBrowserView() {
    const navigate = useNavigate();
    const wildcardPath = useParams()['*'];

    const fileItems = useSignal<FileItem[]>([]);

    useEffect(() => {
        FileBrowserService.getPathItems(wildcardPath)
            .then(items => fileItems.value = items);
    }, [wildcardPath]);

    const visitItem = (item: FileItem) => {
        if (!item.directory) return;
        navigate("/browser/" + item.path);
    };

    return <>
        <BreadCrumbs path={wildcardPath}/>

        <Grid items={fileItems.value}>
            <GridColumn path="displayName" renderer={
                ({item}) => (
                    <div className="flex gap-s items-center"
                         onClick={() => visitItem(item)}
                         title={item.path}>
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