import { Component, Input } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { HomeService } from '../../../services/home.service';
import { Post } from '../../../models/Post';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent {
  
  existError: boolean = false;
  errorMessage:string = '';

  posts: Post[] = [];
  newPost: Post = {
    username:'',
    idPost:0,
    postContent: '',
    text: '',
    nlikes: 0,
    likeImage: false
  };

  noPostsMessage: string = 'People you follow have no posts';
  noPosts: boolean = false;

  constructor(private homeService: HomeService) { }

  ngOnInit() {
    this.getPosts();
  }

  getPosts() {
    this.homeService.getPosts().subscribe(
      (response: Post[]) => {
        console.log(response);
        if(response.length == 0){
          this.noPosts=true;
        }else{
          this.posts = response;
        }
      },
      (error: HttpErrorResponse) => {
        if(error.status === 0){
          this.existError = true;
          this.errorMessage = 'YOU MUST BE LOGGED IN TO USE SOCIALHUB';
        }
        console.error('Error getting publications', error);
        console.error(error.headers);
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
