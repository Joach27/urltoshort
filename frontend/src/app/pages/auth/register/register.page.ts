import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { AuthService } from '../../../services/auth';
import { Router } from '@angular/router';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-register',
  styleUrl: './register.page.css',
  templateUrl: './register.page.html',
})
export class RegisterPage {
  registerForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  })

  // Inject authsService
  private authService = inject(AuthService);

  // Inject router
  private router = inject(Router);

  errorMessage = '';

  onSubmit() {
    if (this.registerForm.valid) {
      this.authService.registerAction(this.registerForm.value).subscribe({
        next: (response) => {
          this.router.navigate(['/login']);
        },

        error: (err) => {
          console.error('Error', err);
          this.errorMessage = "Something went wrong";
        }
      })
    }
  }
}
