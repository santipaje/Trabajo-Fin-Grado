import { Component } from '@angular/core';
import { HomeService } from '../../../services/home.service';
import { ProfileInfo } from '../../../models/ProfileInfo';
import { Router } from '@angular/router';

interface EditRequest {
  username: string,
  fullname: string
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

  
  username: string = '';
  
  profile: ProfileInfo = {
    username: '',
    fullname: '',
    profilePic: '',
    nposts: 0,
    nfollowers: 0,
    nfollowing: 0,
    posts: [],
    followRequests: []
  };

  editRequest: EditRequest = {
    username: '',
    fullname: ''
  }

  errorMessage: string = '';
  existError: boolean = false;
  
  isEditMode: boolean = false;
  newPassword: string = '';

  estadoLike = 1;

  constructor(private homeService: HomeService, private router: Router) { }
  
  ngOnInit() {
    this.getProfile();
  }

  getProfile(){
    this.homeService.getProfile('').subscribe(
      (p: any) => {
        this.profile = p;
        console.log(this.profile);
      },
      (error: any) => {
        console.log(error);
        if(error.status == 404){
          this.errorMessage = 'Username: ' + this.username + ' not found';
        }
      }
    );
  }

  editProfile() {
    this.isEditMode = true;
  }

  saveProfile() {
    this.existError = false;
    console.log(this.editRequest)
    this.isEditMode = false;
    this.homeService.editUser(this.editRequest).subscribe(
      (response: any) => {
        console.log(response);
      },
      (error) => {
        this.existError = true;
        this.editRequest.fullname = '';
        this.editRequest.username = '';
        this.errorMessage = error.error.message;
        console.error(error);
      }
    );
  }

  cancelEditProfile() {
    this.isEditMode = false;
    this.editRequest.fullname = '';
    this.editRequest.username = '';
  }

  changePassword() {
    let token = localStorage.getItem('token')
    this.router.navigateByUrl(`/reset-pwd?token=${token}`)
  }

  acceptFollowRequest(request: string) {

  }

  rejectFollowRequest(request: string) {

  }

  likeImage(post: any){
    post.likeImage = !post.likeImage;
    this.homeService.like(post).subscribe(
      (response: any) => {
        if(post.likeImage){
          post.nlikes = post.nlikes + 1;
        }else{
          post.nlikes = post.nlikes - 1;
        }
        console.log(response);
      },
      (error) => {
        this.existError = true;
        this.errorMessage = error.error.message;
        console.error(error);
      }
    );
  }

  changePrivacy(){
    
  }

  getBase64Image(imageData: any){
    if (!imageData) {
      return '';
    }
  
    let base64Pic:string = 'data:image/png;base64,' + imageData;
    return base64Pic;
  }

}
