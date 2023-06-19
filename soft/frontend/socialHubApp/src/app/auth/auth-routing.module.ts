import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './pages/register/register.component'
import { LoginhomeComponent } from './pages/loginhome/loginhome.component'
import { LoginComponent } from './pages/loginhome/login/login.component'
import { EmailverificationComponent } from './pages/emailverification/emailverification.component'
import { ForgotPwdComponent } from './pages/forgotpwd/forgotpwd.component';
import { ResetpasswordComponent } from './pages/forgotpwd/resetpassword/resetpassword.component';

const routes: Routes = [
  {
    path: '',
    children: [
      { path: 'loginhome', component: LoginhomeComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'login', component: LoginComponent },
      { path: 'verify-email', component: EmailverificationComponent },
      { path: 'forgot-pwd', component: ForgotPwdComponent},
      { path: 'reset-pwd', component: ResetpasswordComponent},
      { path: '**', redirectTo: 'loginhome' }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
