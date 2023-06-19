import { Component } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../../models/Post';
import { HomeService } from '../../../services/home.service';
import { resizeImage } from '../utils/Utils';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  private MAX_SIZE_BYTES = 1048576;
  
  errorMessage:string = '';

  isCreatePostOpen = false;

  selectedImage: File | null = null;
  newPostText: string = '';
  newPost: Post = {
    username: '',
    idPost:0,
    postContent: '',
    text: '',
    nlikes: 0,
    likeImage: false
  };

  constructor(private homeService: HomeService, private router: Router) { }

  ngOnInit() {
  }

  getCurrentUrl(): string {
    return this.router.url;
  }

  openCreatePost() {
    this.isCreatePostOpen = true;
  }

  closeCreatePost() {
    this.isCreatePostOpen = false;
    this.newPost = { postContent: '',  idPost:0, text: '', likeImage:false, nlikes:0, username:'' };
  }

  fileInput(event: any) {
    this.selectedImage = event.target.files[0];
    if (this.selectedImage) {
      console.log('Original image:', this.selectedImage);
      if (this.selectedImage.size > this.MAX_SIZE_BYTES) {
        resizeImage(this.selectedImage).then((resizedFile) => {
          // Resize
          console.log('Resized image:', resizedFile);
          this.selectedImage = resizedFile;
        }).catch((error) => {
          console.error('Error when resizing the image:', error);
        });
      }
    }
  }

  createPost() {
    if (this.selectedImage) {
      const formData: FormData = new FormData();
      formData.append('image', this.selectedImage);
      formData.append('header', this.newPostText)
      this.homeService.saveImage(formData).subscribe(
        (response: any) => {
          console.log('Imagen guardada correctamente', response);

          this.newPost.postContent = response.image;
          this.newPost.text = this.newPost.text;
          this.closeCreatePost();
        },
        (error) => {
          console.error('Error al guardar la imagen', error);
        }
      );
    }
  }

  logOut(){
    localStorage.removeItem('token');
    this.router.navigateByUrl('/loginhome');
  }


}
