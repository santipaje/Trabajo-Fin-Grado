import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { inject } from '@angular/core';

export const guardsGuard: CanActivateFn = async (route, state) => {

  const authService = inject( AuthService );
  const router      = inject( Router );

  const token = authService.getToken();

  let errorMessage: string = '';

  if(token){
     await authService.authToken(token).subscribe(
      (responseAuth) => {
        console.log(responseAuth);
      },
      (error) => {
        router.navigateByUrl('/loginhome');
        errorMessage = error.error.message;
        console.log(errorMessage);
      }
    );

    if(errorMessage != 'Token has expired'){
      return true;
    }
  }

  console.log('routerrrr');
  router.navigateByUrl('/loginhome');
  return false;

};
