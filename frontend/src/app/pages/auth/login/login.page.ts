import { Component, Inject, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth';
import { Router } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-login',
  styleUrl: './login.page.css',
  templateUrl: './login.page.html',
})
export class LoginPage {
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  })

  // Inject service into the constructor
  private authService = inject(AuthService);

  // Inject router
  private router = inject(Router);

  errorMessage = '';

  onSubmit() {
    // Verify is Validators rules are respected
    if (this.loginForm.valid) {
      this.authService.loginAction(this.loginForm.value).subscribe({
        next: (response) => {
          localStorage.setItem('auth_token', response.token);
          
          // Update the signal for login state
          this.authService.isLoggedIn.set(true)
          
          this.router.navigate(['/dashboard'])
        },

        error: (err) => {
          console.error('Erreur technique', err);

          this.errorMessage = "Incorrect Username or Password";
        }
      })
    }
  }
}

