import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { guardsGuard } from './guards/auth.guard';

const routes: Routes = [
  { 
    path:'home',
    canActivate: [guardsGuard],
    loadChildren: () => import('./home/home.module').then( m => m.HomeModule )
  },
  { 
    path:'',
    loadChildren: () => import('./auth/auth.module').then( m => m.AuthModule )
  },
  {
    path:'',
    redirectTo:'',
    pathMatch:'full'
  },
  {
    path:'**',
    redirectTo:'404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
