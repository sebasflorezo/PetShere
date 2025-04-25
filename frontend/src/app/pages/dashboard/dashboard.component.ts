import { Component, type OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  imports: [FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  constructor(private router: Router, private authService: AuthService) {}
  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((isAuth) => {
      if (!isAuth) this.router.navigate(['/login']);
    });

    // Validar / Refrescar token
  }

  onSubmit(): void {
    this.logout();
  }

  logout(): void {
    this.authService.logout();
    location.reload();
  }
}
