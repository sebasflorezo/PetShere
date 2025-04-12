import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { LoginCredentials } from '../../models/loginCredentials';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  errorMessage = '';
  credentials: LoginCredentials = {
    email: '',
    password: '',
  };
  rememberMe: boolean = false;
  showPassword: boolean = false;

  constructor(private router: Router, private authService: AuthService) {}

  togglePasswordVisibility(visible: boolean) {
    this.showPassword = visible;
  }

  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((isAuth) => {
      if (isAuth) this.redirectToDashBoard();
    });
  }

  onSubmit(): void {
    this.authService.login(this.credentials).subscribe({
      next: (tokenResponse) => {
        if (!this.authService.isAuthenticated()) return;
        this.authService.saveSessionToken(tokenResponse.token);

        this.authService
          .fetchUserData(tokenResponse.token)
          .subscribe((user) => {
            this.authService.saveSessionUser(user);
            if (this.rememberMe) {
              this.authService.saveLocalUser(user);
              this.authService.saveLocalToken(tokenResponse.token);
            }
          });

        this.redirectToDashBoard();
      },
      error: (err) => {
        this.errorMessage =
          'No se pudo iniciar sesión, verifique sus credenciales';
      },
    });
  }

  redirectToDashBoard(): void {
    this.router.navigate(['/dashboard']);
  }
}
