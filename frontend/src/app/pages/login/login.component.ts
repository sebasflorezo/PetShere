import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import { Credentials } from "../../models/credentials";
import { UserRole } from "../../models/user";

@Component({
  selector: "app-login",
  imports: [FormsModule],
  templateUrl: "./login.component.html",
  styleUrl: "./login.component.css",
})
export class LoginComponent implements OnInit {
  errorMessage = "";
  credentials: Credentials = {
    email: "",
    password: "",
  };
  rememberMe: boolean = false;

  constructor(
    private router: Router,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    if (this.authService.isAuthenticated()) {
      this.redirectToDashBoard();
    }
  }

  onSubmit(): void {
    this.authService.login(this.credentials).subscribe({
      next: (token) => {
        if (this.rememberMe) {
          // TODO guardar usuario
          this.authService.saveToken(token);
        }

        if (this.authService.isAuthenticated()) {
          this.redirectToDashBoard();
        }
      },
      error: (err) => {
        console.error("Error al iniciar sesión: ", err);
        this.errorMessage =
          "Error al iniciar sesión, verifique sus credenciales.";
      },
    });
  }

  redirectToDashBoard(): void {
    const role = this.authService.getUser()?.role;

    switch (role) {
      case UserRole.ADMIN:
        this.router.navigate(["/admin-dashboard"]);
        break;
      case UserRole.CARER:
        this.router.navigate(["/carer-dashboard"]);
        break;
      case UserRole.MANAGER:
        this.router.navigate(["/manager-dashboard"]);
        break;
      case UserRole.USER:
        this.router.navigate(["/dashboard"]);
        break;
      default:
        this.router.navigate(["/dashboard"]);
        break;
    }
  }
}
