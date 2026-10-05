import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';

// The backend serializes its Map<Device, Long> and Map<String, Long> as JSON
// objects, so both are records keyed by name (e.g. { "MOBILE": 3 }).
interface LinkAnalytics {
  totalClicks: number;
  clicksByDevice: Record<string, number>;
  clicksByCountry: Record<string, number>;
}


@Service()
export class LinkService {
  private http = inject(HttpClient);
  
  private apiUrl = 'http://localhost:8080/api/v1/links'; 

  shortenUrl(targetUrl: string): Observable<any> {
    // Request body to much the dto in the backend
    const payload = { targetUrl: targetUrl }; 
    
    return this.http.post(`${this.apiUrl}/shorten`, payload);
  }

  // Get links for User
  getUserLinks(): Observable<any[]>{
    return this.http.get<any[]>(this.apiUrl);
  }

  // Get link analytics
  getLinkAnalytics(linkId: number): Observable<LinkAnalytics>{
    return this.http.get<LinkAnalytics>(`${this.apiUrl}/${linkId}/analytics`)
  }
}
