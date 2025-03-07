export enum UserRole {
    ADMIN = "ADMIN",
    USER = "USER",
    MANAGER = "MANAGER",
    CARER = "CARER",
}

export interface User {
    id: number;
    role: UserRole;
    name: string;
    email: string;
    token: string;
}
