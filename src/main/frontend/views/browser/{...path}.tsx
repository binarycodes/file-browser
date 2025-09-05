import {ViewConfig} from "@vaadin/hilla-file-router/types.js";
import {FileBrowserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";
import {Grid, GridColumn, GridSortColumn, Icon} from "@vaadin/react-components";
import {useEffect} from "react";

import folderIcon from "@icons/icons8-folder.svg?url";
import fileIcon from "@icons/icons8-file.svg?url";
import downloadImage from "@images/icons8-download-from-the-cloud.svg?url";

import {useNavigate, useParams} from "react-router";
import BreadCrumbs from "Frontend/components/BreadCrumbs";
import {formatDate} from "Frontend/util/dateFormat";


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
            <GridSortColumn resizable path="displayName" header="Name" renderer={
                ({item}) => (
                    <div className="flex gap-s items-center"
                         onClick={() => visitItem(item)}
                         title={item.path}>
                        <Icon src={item.directory ? folderIcon : fileIcon}/>
                        <span className="text-s text-secondary">{item.displayName}</span>
                    </div>
                )}
            />
            <GridSortColumn resizable path="fileSize" header="Size" renderer={
                ({item}) => (
                    <span className="text-s text-secondary">{item.fileSize}</span>
                )}
            />
            <GridSortColumn resizable path="lastModifiedOn" header="Last Modified On" renderer={
                ({item}) => (
                    <span className="text-s text-secondary">{formatDate(item.lastModifiedOn)}</span>
                )}
            />
            <GridColumn resizable frozenToEnd renderer={
                ({item}) => (
                    <>
                        {item.directory ?
                            <></> :
                            <a href={`/download/${item.path}`} download>
                                <img src={downloadImage} width="32" alt="Vaadin logo"/>
                            </a>
                        }
                    </>
                )}
            />
        </Grid>
    </>;
}