import { Component } from '@angular/core';
import { UserInfo } from '../../../models/UserInfo';
import { HomeService } from '../../../services/home.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfileInfo } from '../../../models/ProfileInfo';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent {

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

  username: string | null = '';

  existError: boolean = false;
  errorMessage:string = '';

  constructor(private homeService: HomeService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.username = this.route.snapshot.paramMap.get('username');
    console.log(this.username);
    this.getProfile();
  }

  getProfile(){
    let user: string = '' + this.username;
    this.homeService.getProfile(user).subscribe(
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

  getBase64Image(imageData: any){
    if (!imageData) {
      return '';
    }
  
    let base64Pic:string = 'data:image/png;base64,' + imageData;
    return base64Pic;
  }

}
