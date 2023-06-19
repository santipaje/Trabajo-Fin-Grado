import { Component } from '@angular/core';
import { Login } from '../../../models/Login';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';
import { isFullFilledLogin } from '../../../shared/pages/utils/Utils';

@Component({
  selector: 'app-loginhome',
  templateUrl: './loginhome.component.html',
  styleUrls: ['./loginhome.component.css']
})
export class LoginhomeComponent {

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
    this.existError = false;
    console.log('Form: ', this.userdata);
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
