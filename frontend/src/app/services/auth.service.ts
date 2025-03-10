import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import type { Credentials } from "../models/credentials";
import type { Observable } from "rxjs";
import type { User } from "../models/user";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  constructor(private http: HttpClient) {}

  // userData(token: string): Observable<string> {
  //   return this.http.post<string>(`/clients`, token, hea);
  // }

  login(credentials: Credentials): Observable<string> {
    return this.http.post<string>(`/api/v1/auth/login`, credentials);
  }

  logout(user: User) {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
  }

  getUser(): User | null {
    const user = localStorage.getItem("user");
    return user ? JSON.parse(user) : null;
  }

  getToken(): string | null {
    const token = localStorage.getItem("token");
    return token ? JSON.parse(token) : null;
  }

  saveToken(token: String) {
    localStorage.setItem("token", JSON.stringify(token));
  }

  saveUser(user: User) {
    localStorage.setItem("user", JSON.stringify(user));
  }
}
