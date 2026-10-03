import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../../services/auth';

@Component({
  imports: [RouterLink],
  selector: 'app-nav-component',
  styleUrl: './nav-component.css',
  templateUrl: './nav-component.html',
})
export class NavComponent {
  authService = inject(AuthService);

  onLogout() {
    this.authService.logout();
  }
}
