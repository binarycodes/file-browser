import {Button, Dialog, TextField} from "@vaadin/react-components";
import {Signal, useSignal} from "@vaadin/hilla-react-signals";
import FileItem from "Frontend/generated/pro/homedns/filebrowser/model/FileItem";

export let renameFileDialogOpenSignal: Signal<boolean>;
export let itemToRename: Signal<FileItem | null>;

export default function RenameFileDialog() {
    renameFileDialogOpenSignal = useSignal<boolean>(false);
    itemToRename = useSignal<FileItem | null>(null);

    return <Dialog
        header={(
            <div className="flex flex-col gap-xs">
                <span className="text-secondary text-s">{itemToRename.value?.displayName}</span>
                <span className="text-body text-m">Rename</span>
            </div>
        )}
        opened={renameFileDialogOpenSignal.value}
        onClosed={() => {
            renameFileDialogOpenSignal.value = false;
        }}
        footer={
            <>
                <div className="flex flex-row w-full justify-between">
                    <Button
                        onClick={() => {
                            renameFileDialogOpenSignal.value = false;
                        }}
                    >
                        Cancel
                    </Button>
                    <Button
                        theme="primary"
                        onClick={() => {
                            renameFileDialogOpenSignal.value = false;
                        }}
                    >
                        Rename
                    </Button>
                </div>
            </>
        }
    >
        <div className="flex flex-col items-stretch max-w-full" style={{width: '18rem'}}>
            <TextField label="New name" value={itemToRename.value?.displayName}/>
        </div>
    </Dialog>
}