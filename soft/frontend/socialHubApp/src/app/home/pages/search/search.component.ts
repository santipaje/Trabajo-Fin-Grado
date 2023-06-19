import { Component } from '@angular/core';
import { HomeService } from '../../../services/home.service';
import { UserInfo } from '../../../models/UserInfo';
import { FriendRequestStatus } from '../../../shared/pages/utils/FriendReqStatus'; 
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  constructor(private homeService: HomeService, private router: Router) { }

  username: string = '';

  user: UserInfo = {
    username: '',
    fullname: '',
    profilePic: '',
    nposts: 0,
    nfollowers: 0,
    nfollowing: 0,
    requestStatus: ''
  };
  errorMessage: string = '';

  userFound: boolean = false;
  //isPrivate: boolean = false;
  isFollowable: boolean = false;
  sameUser: boolean = false;
  isFollowed: boolean = false;

  status: string = '';

  search(): void {
    this.userFound = false;
    console.log(this.username);
    this.homeService.searchUser(this.username).subscribe(
      (user: any) => {
        this.errorMessage = '';
        this.userFound = true;
        this.isFollowable = this.followable(user.requestStatus);
        this.isFollowed = user.requestStatus === FriendRequestStatus.FOLLOWED ? true : false;

        console.log(user);
        this.status = user.requestStatus;
        console.log(this.status);
        this.user = user;
      },
      (error: any) => {
        console.log(error.status);
        if(error.status == 404){
          this.errorMessage = 'Username: ' + this.username + ' not found';
        }
      }
    );
  }

  follow(): void {
    this.homeService.followRequest(this.username).subscribe(
      (newStatus: any) => {
        //this.isPrivate = isPriv;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  unfollow(): void {
    this.homeService.unfollow(this.username).subscribe(
      (newStatus: any) => {
        //this.isPrivate = isPriv;
        this.isFollowable = true;
        this.isFollowed = false;
      },
      (error: any) => {
        console.log(error);
      }
    );
  }

  private followable(status:string): boolean{
    if(status === FriendRequestStatus.FOLLOWABLE){
      return true;
    }
    return false;
  }

  getBase64Image(imageData: any){
    if (!imageData) {
      return '';
    }
  
    let base64Pic:string = 'data:image/png;base64,' + imageData;
    return base64Pic;
  }

  showProfile(){
    console.log(this.user.username);
    this.router.navigate(['/home/profile', this.user.username]);
    //this.router.navigateByUrl('/profile/' + this.user.username);
  }

}
