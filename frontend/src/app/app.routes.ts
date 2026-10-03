import { Routes } from '@angular/router';
import { HomePage } from './pages/home/home.page';
import { LoginPage } from './pages/auth/login/login.page';
import { RegisterPage } from './pages/auth/register/register.page';
import { NotFound } from './pages/not-found/not-found';
import { DashboardPage } from './pages/dashboard/dashboard.page';
import { authGuard } from './guards/auth-guard';

export const routes: Routes = [
  // 1. Home route
  { path: 'home', component: HomePage },

  // 2. Login route 
  { path: 'login', component: LoginPage },

  // 3. Register route
  { path: 'register', component: RegisterPage },

  // // 4. Dashbord
  {
    path: 'dashboard',
    component: DashboardPage,
    canActivate: [authGuard]
  },

  // // 5. Analytics
  // { path: 'analytics/linkId', component: AnalyticsComponent },

  // 6. Default
  { path: '', component: HomePage },

  // 7. Any other routes
  { path: '**', component: NotFound },
];
