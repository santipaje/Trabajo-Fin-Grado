
/* Utils */

import { Login } from "../../../models/Login";
import { Register } from "../../../models/Register";

export function validateEmail(email: string): boolean {
    const emailPattern  = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailPattern.test(email);
  }

export function isFullFilled (user: Register): boolean{
  if(user.email.length == 0 || user.fullname.length == 0
    || user.password.length == 0 || user.username.length == 0){
      return false;
    }
    return true;
}

export function isFullFilledLogin (user: Login): boolean{
    if( user.password.length == 0 || user.username.length == 0){
        return false;
      }
      return true;
  }

export function resizeImage(file: File): Promise<File> {
  return new Promise<File>((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (event: any) => {
      const img = new Image();
      img.src = event.target.result;
      img.onload = () => {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        const MAX_WIDTH = 800;
        const MAX_HEIGHT = 600;
        let width = img.width;
        let height = img.height;
        if (width > height) {
          if (width > MAX_WIDTH) {
            height *= MAX_WIDTH / width;
            width = MAX_WIDTH;
          }
        } else {
          if (height > MAX_HEIGHT) {
            width *= MAX_HEIGHT / height;
            height = MAX_HEIGHT;
          }
        }
        canvas.width = width;
        canvas.height = height;
        if(ctx){
            ctx.drawImage(img, 0, 0, width, height);
        }else{
            reject(new Error('Error when resizing the image'));
        }
        canvas.toBlob((blob) => {
            if (blob) {
                const resizedFile = new File([blob], file.name, { type: 'image/jpeg' });
                resolve(resizedFile);
              } else {
                reject(new Error('Error when resizing the image'));
              }
        }, 'image/jpeg', 1); // 1: quality number, higher the number, higher the quality
      };
    };
    reader.readAsDataURL(file);
  });
}