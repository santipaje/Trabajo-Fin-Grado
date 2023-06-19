import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { AppModule } from '../app.module';
import { EmailverificationComponent } from './pages/emailverification/emailverification.component';
import { LoginhomeComponent } from './pages/loginhome/loginhome.component';
import { LoginComponent } from './pages/loginhome/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { ForgotPwdComponent } from './pages/forgotpwd/forgotpwd.component';
import { ResetpasswordComponent } from './pages/forgotpwd/resetpassword/resetpassword.component';


@NgModule({
  declarations: [
    EmailverificationComponent,
    LoginhomeComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPwdComponent,
    ResetpasswordComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers:[AuthService]
})
export class AuthModule { }
