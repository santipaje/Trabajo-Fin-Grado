import { Component } from '@angular/core';
import { Login } from '../../../../models/Login';
import { AuthService } from '../../../../services/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { isFullFilledLogin } from '../../../../shared/pages/utils/Utils';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  userdata: Login = {
    username:'',
    password: ''
  };

  existError: boolean = false;
  errorMessage: string = '';

  errors: string[] = [
    'All fields must be filled in'
  ]

  constructor(
    private authService: AuthService,
    private router: Router
   ) {
   }
  
  onSubmit(form: NgForm) {
    console.log('Form: ', form.value);
    if(isFullFilledLogin(this.userdata)){
      this.authService.loginUser(this.userdata)
      .subscribe(response => {
        this.router.navigate(['/home']); 
      },
      (error) => {
        if (error && error.error) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = 'An error occurred.';
        }
        this.existError = true;
        console.log(this.errorMessage);
      }
      );
    }else{
      this.errorMessage = this.errors[0];
      this.existError = true;
    }
  }
}
