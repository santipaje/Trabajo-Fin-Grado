package com.socialhub.tfg.exceptions;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerSH implements ErrorController {

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase(), message);
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @ExceptionHandler(EmailUsedException.class)
    public ResponseEntity<HttpResponse> emailUsedException(EmailUsedException e){
        return createHttpResponse(HttpStatus.CONFLICT,e.getMessage());
    }

    @ExceptionHandler(UserUsedException.class)
    public ResponseEntity<HttpResponse> userUsedException(UserUsedException e){
        return createHttpResponse(HttpStatus.CONFLICT,e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException e){
        return createHttpResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(UserAlreadyEnabledException.class)
    public ResponseEntity<HttpResponse> userAlreadyEnabledException(UserAlreadyEnabledException e){
        return createHttpResponse(HttpStatus.CONFLICT,e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException e){
        return createHttpResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(ImagePostException.class)
    public ResponseEntity<HttpResponse> imagePostException(ImagePostException e){
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<HttpResponse> postNotFoundException(PostNotFoundException e){
        return createHttpResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

    @ExceptionHandler(IncorrectPwdException.class)
    public ResponseEntity<HttpResponse> incorrectPwdException(IncorrectPwdException e){
        return createHttpResponse(HttpStatus.UNAUTHORIZED,e.getMessage());
    }

    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<HttpResponse> emptyFileException(EmptyFileException e){
        return createHttpResponse(HttpStatus.NO_CONTENT,e.getMessage());
    }

    @ExceptionHandler(FriendRequestNotFoundException.class)
    public ResponseEntity<HttpResponse> friendRequestNotFoundException(FriendRequestNotFoundException e){
        return createHttpResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }

}
