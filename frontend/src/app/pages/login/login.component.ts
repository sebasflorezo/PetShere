import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { AuthService } from "../../services/auth.service";
import { Router } from "@angular/router";
import type { Credentials } from "../../models/credentials";

@Component({
  selector: "app-login",
  imports: [FormsModule],
  templateUrl: "./login.component.html",
  styleUrl: "./login.component.css",
})
export class LoginComponent {
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

  onSubmit() {
    // TODO Validar entrada de los datos

    this.authService.login(this.credentials).subscribe({
      next: (token) => {
        if (this.rememberMe) {
          this.authService.saveToken(token);
        }

        this.router.navigate(["/dashboard"]);
      },
      error: (err) => {
        console.error("Error al iniciar sesión: ", err);
        this.errorMessage =
          "Error al iniciar sesión, verifique sus credenciales.";
      },
    });
  }
}
