import { HttpClient } from '@angular/common/http';
import { Service, inject } from '@angular/core';
import { Observable } from 'rxjs';

@Service()
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/v1/auth';

  private http = inject(HttpClient);

  loginAction(credentials: any): Observable<any>{
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  registerAction(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }
}
