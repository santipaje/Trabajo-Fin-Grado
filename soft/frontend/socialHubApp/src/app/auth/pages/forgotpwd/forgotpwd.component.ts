import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-resetpassword',
  templateUrl: './forgotpwd.component.html',
  styleUrls: ['./forgotpwd.component.css']
})
export class ForgotPwdComponent {

  email: string = '';
  errorMessage: string = '';

  verificationMessage: boolean = false;
  existError: boolean = false;

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  onSubmit(form: NgForm) {
    this.existError = false;
    console.log('Form: ', form.value);
    this.authService.forgotPwd(this.email).subscribe(
      (response) => {
        this.verificationMessage = true;
      },
      (error) => {
        this.existError = true;
        this.errorMessage = error.error.message;
      }
    );
  }
}
