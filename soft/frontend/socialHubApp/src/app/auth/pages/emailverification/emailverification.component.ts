import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-emailverification',
  templateUrl: './emailverification.component.html',
  styleUrls: ['./emailverification.component.css']
})
export class EmailverificationComponent implements OnInit {
  loading: boolean = true;
  success: boolean = false;
  errorr: boolean = false;
  // Mensaje de exito
  successMessage: string = "Your account has been successfully verified."
  + "<br>" + "You can now log in with your account and start enjoying SocialHub.";
  // Depende del backend
  errorMessage: string = "Error verifying your email address";

  constructor(private route: ActivatedRoute, private authService: AuthService) { }

  ngOnInit(): void {
    this.loading = true;


    // Obtener el token de verificación del correo electrónico de los parámetros de la URL
    this.route.queryParams.subscribe(params => {
      const token = params['token']; // Accede al valor del parámetro 'token'
      console.log(token);
      // Hacer la solicitud de verificación de correo electrónico al backend
      this.authService.verifyEmail(token).subscribe(
        (response) => {
          this.loading = false;
          this.success = true;
          this.successMessage = response.message;
        },
        (error) => {
          this.loading = false;
          this.errorr = true;
          this.errorMessage = error.error.message;
        }
      );
    });
  }
}
