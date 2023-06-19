import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Register } from '../../../models/Register';
import { NgForm } from '@angular/forms';
import { validateEmail, isFullFilled } from '../../../shared/pages/utils/Utils';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  userdata: Register = {
    fullname: '',
    username:'',
    email: '',
    password: ''
  };
  verificationMessage: boolean = false;
  existError: boolean = false;

  errors: string[] = [
    'All fields must be filled in',
    'Email does not have a valid format',
    'Passwords must be at least <br> 8 characters long.'
  ];

  errorMessage: string = '';

  constructor(private authService: AuthService) {
   }
  
  onSubmit(form: NgForm) {
    this.existError = false;
    console.log('Form: ', form.value);
    if(isFullFilled(this.userdata)){
      if(validateEmail(this.userdata.email)){
        if(this.userdata.password.length >= 8){
          this.authService.registerUser(this.userdata);
          this.verificationMessage = true;
        }else{
          this.errorMessage = this.errors[2];
          this.existError = true;
        }
      }else{
        this.errorMessage = this.errors[1];
        this.existError = true;
    }
    }else{
      this.errorMessage = this.errors[0];
      this.existError = true;
    }
    
    
  }

}
