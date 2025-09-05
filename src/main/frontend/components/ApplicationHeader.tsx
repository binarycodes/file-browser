import {CurrentUserService} from "Frontend/generated/endpoints";
import {useSignal} from "@vaadin/hilla-react-signals";

export default function ApplicationHeader() {
    const username = useSignal("");

    CurrentUserService.getUserInfo().then(userInfo => {
        username.value = userInfo.fullName || '';
        console.log(username.value);
    });

    return <>
        <div className="flex flex-row justify-between">
            <h1>File Browser</h1>
            <span>{username.value}</span>
        </div>
    </>;
}