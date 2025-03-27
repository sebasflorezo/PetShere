import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import type { Credentials } from "../models/credentials";
import { BehaviorSubject, type Observable } from "rxjs";
import type { User } from "../models/user";

@Injectable({
  providedIn: "root",
})
export class AuthService {
  private authStatus = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  private hasToken(): boolean {
    return !!this.getToken();
  }

  isAuthenticated(): Observable<boolean> {
    this.authStatus.next(this.hasToken());
    // TODO validar token
    return this.authStatus.asObservable();
  }

  userData(token: string): Observable<string> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.post<string>(`/clients`, {}, { headers });
  }

  login(credentials: Credentials): Observable<string> {
    return this.http.post<string>(`/auth/login`, credentials);
  }

  logout(): void {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
    sessionStorage.removeItem("user");
    sessionStorage.removeItem("token");
    this.authStatus.next(false);
  }

  getUser(): User | null {
    const user = localStorage.getItem("user");
    return user ? JSON.parse(user) : null;
  }

  getToken(): string | null {
    return localStorage.getItem("token") || sessionStorage.getItem("token") || null;
  }

  saveLocalToken(token: string): void {
    localStorage.setItem("token", token);
  }

  saveSessionToken(token: string): void {
    sessionStorage.setItem("token", token);
  }

  saveLocalUser(user: User): void {
    localStorage.setItem("user", JSON.stringify(user));
  }

  saveSessionUser(user: User): void {
    sessionStorage.setItem("user", JSON.stringify(user));
  }
}
