import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../services/auth.service';
import { JwtHelperService } from '@auth0/angular-jwt';

interface Passwords {
  password: string,
  password2: string
}

interface RequestChangePass {
  username: string,
  password: string
}

interface ResponseAuth {
  isAuth: boolean,
  username: string
}

const jwtHelper = new JwtHelperService();

@Component({
  selector: 'app-resetpassword',
  templateUrl: './resetpassword.component.html',
  styleUrls: ['./resetpassword.component.css']
})
export class ResetpasswordComponent {

  passFields: Passwords = {
    password: '',
    password2: ''
  }

  request: RequestChangePass = {
    username: '',
    password: ''
  }

  responseAuth: ResponseAuth = {
    isAuth: false,
    username: ''
  }

  isAuth: boolean = true;
  successful: boolean = false;
  existError: boolean = false;

  errorMessage: string = '';

  pwdError: string[] = [
    'Passwords do not match',
    'Passwords must be at least <br> 8 characters long.'
  ];

  token = '';

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {

    // Obtiene el token
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      console.log(this.token);
      // Autenticacion del token
      this.authService.authToken(this.token).subscribe(
        (responseAuth) => {
          console.log(responseAuth);
          this.isAuth = responseAuth.isAuth;
        },
        (error) => {
          this.isAuth = false;
          console.log(error.error.message);
        }
      );
    });

  }

  onSubmit(form: NgForm) {
    this.existError = false;
    console.log('Form: ', form.value);
    if (this.passFields.password === this.passFields.password2) {
      if (this.passFields.password.length >= 8) {
        let decodedToken = jwtHelper.decodeToken(this.token);
        console.log(decodedToken);
        this.request.username = decodedToken.sub;
        this.request.password = this.passFields.password;
        this.authService.changePwd(this.request)
          .subscribe(response => {
            this.successful = true;
          },
            (error) => {
              this.existError = true;
              this.errorMessage = error.error.message;
            }
          );
      } else {
        this.errorMessage = this.pwdError[1];
        this.existError = true;
      }
    } else {
      this.errorMessage = this.pwdError[0];
      this.existError = true;
    }

  }

}
