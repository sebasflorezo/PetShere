import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private darkModeClass = 'dark-mode';
  constructor() {}

  toggleDarkMode(): void {
    document.body.classList.toggle(this.darkModeClass);

    const isDark = document.body.classList.contains(this.darkModeClass);
    localStorage.setItem('darkMode', isDark ? 'true' : 'false');
  }

  loadTheme(): void {
    const isDark = localStorage.getItem('darkMode') === 'true';
    if (isDark) {
      document.body.classList.add(this.darkModeClass);
    }
  }
}
