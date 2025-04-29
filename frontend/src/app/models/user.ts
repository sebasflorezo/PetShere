export enum UserRole {
  ADMIN = 'ADMIN',
  CLIENT = 'CLIENT',
  MANAGER = 'MANAGER',
  CARER = 'CARER',
}

export interface User {
  id: number;
  document: string;
  email: string;
  firstName: string;
  middleName: string;
  lastName: string;
  secondSurname: string;
  phone: string;
  direction: string;
  state: boolean;
  role: UserRole;
}
