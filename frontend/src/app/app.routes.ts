import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', loadComponent: () => { return import('./pages/login/login').then(module => module.Login)} },
  { path: 'home', loadComponent: () => { return import('./pages/home/home').then(module => module.Home)} },
  
];
