import { Injectable, signal } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, tap } from 'rxjs/operators';
import { EMPTY, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public isLoggedIn = signal(false);

  // TODO adjust address
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(
    private http: HttpClient,
    private router: Router
  ) {}

  login(credentials: { username: string; password: string }) {
    return this.http.post(`${this.apiUrl}/login`, credentials, {
      withCredentials: true // httponly cookies
    }).pipe(
      tap(() => {
        // success
        this.isLoggedIn.set(true);
      })
    );
  }

  logout() {
    return this.http.post(`${this.apiUrl}/logout`, {}, {
      withCredentials: true
    }).pipe(
      tap(() => {
        // success
        this.isLoggedIn.set(false);
        this.router.navigate(['/']);
      })
    );
  }

  // called on startup to check status
  checkAuthStatus() {
    return this.http.get<HttpResponse<any>>(`${this.apiUrl}/status`, {
      withCredentials: true
    }).pipe(
      tap(response => {
        if(response.status === 200) {
          this.isLoggedIn.set(true);
        //   this.router.navigate(['/home']);
        }
      }),
      catchError(() => {
        this.isLoggedIn.set(false);
        this.router.navigate(['/']);
        return EMPTY;
      })
    );
  }

  initializeCsrf() {
    return this.http.get(`${this.apiUrl}/csrf`, {
      withCredentials: true
    });
  }
}