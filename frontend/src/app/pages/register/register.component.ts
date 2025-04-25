import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegisterCredentials } from '../../models/registerCredentials';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  errorMessage = '';
  passwordConfirmation: string = '';
  credentials: RegisterCredentials = {
    email: '',
    password: '',
    document: null,
    firstName: '',
    middleName: '',
    lastName: '',
    phone: null,
    direction: '',
    secondSurname: '',
  };

  constructor(private router: Router, private authService: AuthService) {}

  onSubmit(): void {}

  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
