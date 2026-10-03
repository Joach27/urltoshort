import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

@Service()
export class LinkService {
  private http = inject(HttpClient);
  
  private apiUrl = 'http://localhost:8080/api/v1/links'; 

  shortenUrl(targetUrl: string): Observable<any> {
    // Request body to much the dto in the backend
    const payload = { targetUrl: targetUrl }; 
    
    return this.http.post(`${this.apiUrl}/shorten`, payload);
  }
}
