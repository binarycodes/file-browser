import {useNavigate} from "react-router";
import folderIcon from "@icons/icons8-folder.svg?url";
import {Icon} from "@vaadin/react-components";

type BreadcrumbProps = {
    path: string | undefined; // prop type
};

export default function BreadCrumbs({path}: BreadcrumbProps) {
    if (!path) return null;

    const navigate = useNavigate();
    const breadcrumbs = path.split("/") || [];

    const breadcrumbRelativePath = (idx: number): string => {
        return breadcrumbs.slice(0, idx + 1).join("/")
    }

    const breadcrumbNavigate = (idx: number) => {
        const relativePath = breadcrumbRelativePath(idx);
        navigate("/browser/" + relativePath);
    }

    const breadcrumbNavigateRoot = () => navigate("/browser");

    return <>
        <div className="flex flex-row items-start gap-s pb-m">
            <span className="breadcrumb" title="/">
                <Icon src={folderIcon} onClick={() => breadcrumbNavigateRoot()}/>
                <span className="ml-s text-secondary">/</span>
            </span>

            {breadcrumbs.map((crumb, idx) => (
                <span key={idx} className="breadcrumb" title={breadcrumbRelativePath(idx)}>
                    <span className="text-primary" onClick={() => breadcrumbNavigate(idx)}>{crumb}</span>
                    {idx < breadcrumbs.length - 1 && <span className="ml-s text-secondary">/</span>}
                </span>
            ))}
        </div>
    </>;
}