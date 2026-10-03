import { HttpClient } from '@angular/common/http';
import { Service, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Service()
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';
  private router = inject(Router)
  private http = inject(HttpClient);

  // Signal to carry the login state : true if logedin and false if not
  // double !! transform the value into boolean
  isLoggedIn = signal<boolean>(!!localStorage.getItem('auth_token'));

  loginAction(credentials: any): Observable<any>{
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  registerAction(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  logout(): void {
    // Remove the token
    localStorage.removeItem('auth_token');

    // Update the Signal
    this.isLoggedIn.set(false);

    // Redirect to home
    this.router.navigate(['/home']);
  }
}
