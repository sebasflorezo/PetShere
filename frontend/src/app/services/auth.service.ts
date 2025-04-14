import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, type Observable } from 'rxjs';
import type { LoginCredentials } from '../models/loginCredentials';
import type { User } from '../models/user';
import type { AuthResponse } from '../models/authResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authStatus = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  private hasToken(): boolean {
    return !!this.getToken();
  }

  private isBrowser(): boolean {
    return (
      typeof window !== 'undefined' &&
      typeof window.localStorage !== 'undefined'
    );
  }

  isAuthenticated(): Observable<boolean> {
    this.authStatus.next(this.hasToken());
    return this.authStatus.asObservable();
  }

  fetchUserData(token: string): Observable<User> {
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}` });
    return this.http.get<User>(`/api/v1/clients`, { headers });
  }

  login(credentials: LoginCredentials): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`/api/v1/auth/login`, credentials);
  }

  logout(): void {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('token');
    this.authStatus.next(false);
  }

  getUser(): User | null {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  getToken(): string | null {
    if (!this.isBrowser()) return null;
    return localStorage.getItem('token') || sessionStorage.getItem('token');
  }

  saveLocalToken(token: string): void {
    localStorage.setItem('token', token);
  }

  saveSessionToken(token: string): void {
    sessionStorage.setItem('token', token);
  }

  saveLocalUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  saveSessionUser(user: User): void {
    sessionStorage.setItem('user', JSON.stringify(user));
  }
}
