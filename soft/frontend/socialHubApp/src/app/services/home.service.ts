import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/Post';
import { ProfileInfo } from '../models/ProfileInfo';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) { }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>('http://localhost:8080/tfg/feed');
  }

  saveImage(formData: FormData) {
    return this.http.post('http://localhost:8080/tfg/create-post',formData);
  }

  searchUser(username: string) {
    return this.http.get<any>(`http://localhost:8080/tfg/search-user?username=${username}`);
  }

  followRequest(username: string) {
    return this.http.post('http://localhost:8080/tfg/follow-request',username);
  }

  unfollow(username: string) {
    return this.http.post('http://localhost:8080/tfg/unfollow',username);
  }

  getProfile(username: string) {
    return this.http.get<any>(`http://localhost:8080/tfg/show-profile?username=${username}`);
  }

  editUser(editRequest: any) {
    return this.http.post('http://localhost:8080/tfg/edit-user',editRequest);
  }

  like(post:any){
    return this.http.post('http://localhost:8080/tfg/like',post);
  }

  getLiked() {
    return this.http.get<any>('http://localhost:8080/tfg/getLiked');
  }

}
