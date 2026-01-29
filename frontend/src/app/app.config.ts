import { provideAppInitializer, ApplicationConfig, provideBrowserGlobalErrorListeners, provideZoneChangeDetection, inject } from '@angular/core';
import { provideRouter } from '@angular/router';;

import { routes } from './app.routes';
import { registerLocaleData } from '@angular/common';
import de from '@angular/common/locales/de';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { authInterceptor } from './core/interceptors/auth-interceptor'
import { AuthService } from './core/services/auth.service';
import { forkJoin, map } from 'rxjs';

registerLocaleData(de);

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor])),
    // TODO keep commented during dev when not testing this
    // provideAppInitializer(() => {
    //   const authService = inject(AuthService);
      
    //   return forkJoin({
    //     csrf: authService.initializeCsrf(),
    //     auth: authService.checkAuthStatus()
    //   }).pipe(
    //     map(() => undefined)
    //   );
    // }),
  ]
};
