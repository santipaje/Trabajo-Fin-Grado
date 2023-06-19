import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Login } from '../models/Login';
import { Register } from '../models/Register';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  registerUser(userdata: Register) {
    return this.http.post('http://localhost:8080/tfg/register', userdata).subscribe(
      (response: any) => {
        console.log('Registration success');
      },
      (error) => {
        console.error('Registration error', error);
      }
    );;
  }

  loginUser(userdata: Login) {
    return this.http.post('http://localhost:8080/tfg/login', userdata, {
      observe: 'response'
    }).pipe(map((response:HttpResponse<any>) => {
      const body = response.body;
      const headers = response.headers;
      const bearerToken = headers.get('Authorization')!;
      const token = bearerToken.replace('Bearer ', '');
      localStorage.setItem('token',token);  
      return body;
    }
    ))
  }
  
  getToken(){
    return localStorage.getItem('token');
  }

  verifyEmail(token: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8080/tfg/verify-email?token=${token}`);
  }

  forgotPwd(email:string){
    return this.http.post('http://localhost:8080/tfg/forgot-password', email)
  }

  authToken(token: string): Observable<any>{
    return this.http.get<any>(`http://localhost:8080/tfg/authenticate-token?token=${token}`);
  }

  changePwd(cred: any){
    return this.http.post('http://localhost:8080/tfg/change-password', cred)
  }

}
