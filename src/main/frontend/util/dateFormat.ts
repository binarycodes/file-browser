export const formatDate = (value?: string | Date): string => {
    if (!value) return "";
    const date = value instanceof Date ? value : new Date(value);
    return new Intl.DateTimeFormat("en-US", {
        year: "numeric",
        month: "short",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
    }).format(date);
};