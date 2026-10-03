import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {

  // Inject router
  const router = inject(Router);

  // Look for a token in the browser
  const token = localStorage.getItem('auth_token');

  // If the token is present
  if (token) {
    // Token, we open the gate
    return true; 
  } else {
    // No token? force redirection to login page
    router.navigate(['/login']);
    // Close the gate
    return false; 
  }
  return true;
};
