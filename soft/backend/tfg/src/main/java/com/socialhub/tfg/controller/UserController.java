package com.socialhub.tfg.controller;

import com.socialhub.tfg.domain.dto.PostDTO;
import com.socialhub.tfg.domain.vo.*;
import com.socialhub.tfg.exceptions.*;
import com.socialhub.tfg.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("tfg")
public class UserController extends ExceptionHandlerSH {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseRegisterVO> register(@Valid @RequestBody RequestRegisterVO request)
            throws EmailUsedException, UserUsedException, IOException {
        log.info("Start register method -> Parameters [{}]",request);
        ResponseRegisterVO response = userService.register(request);
        log.info("End register method -> Result [{}]",response);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ResponseVerifyEmail> verifyEmail(@RequestParam("token") String token)
            throws UserNotFoundException, UserAlreadyEnabledException, TokenExpiredException {
        log.info("Start verifyEmail method");
        ResponseVerifyEmail response = userService.verifyEmail(token);
        log.info("End verifyEmail method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestLoginVO request)
            throws UserNotFoundException, IncorrectPwdException {
        log.info("Start login method -> Parameters [{}]",request);
        ResponseLoginVO response = userService.login(request);
        log.info("End login method -> Response [{}]", response.getHeader());
        return new ResponseEntity<>(response.getId_user(),response.getHeader(),HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPass(@Valid @RequestBody String emailRequest)
            throws UserNotFoundException{
        log.info("Start forgotPass method -> Parameters [{}]",emailRequest);
        userService.forgotPass(emailRequest);
        log.info("End forgotPass method");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/authenticate-token")
    public ResponseEntity<ResponseAuthToken> authToken(@RequestParam("token") String token)
            throws TokenExpiredException{
        log.info("Start authToken method -> Parameters [{}]",token);
        ResponseAuthToken response = userService.authToken(token);
        log.info("End authToken method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePass(@Valid @RequestBody RequestChangePassword request)
            throws UserNotFoundException{
        log.info("Start changePass method -> Parameters [{}]",request);
        userService.changePass(request);
        log.info("End changePass method");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create-post")
    public ResponseEntity<ResponseCreatePostVO> createPost(@RequestPart("image") MultipartFile image,
                                                           @RequestPart("header") String header)
            throws ImagePostException, UserNotFoundException, EmptyFileException {
        RequestCreatePostVO request = new RequestCreatePostVO();
        request.setHeader(header);
        request.setPostContent(image);
        log.info("Start createPost method -> Parameters [{}]",request);
        ResponseCreatePostVO response = userService.createPost(getLogedUser(),request);
        log.info("End createPost method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/change-header")
    public ResponseEntity<String> changeHeader(@Valid @RequestBody RequestChangeHeaderVO request)
            throws UserNotFoundException, PostNotFoundException {
        log.info("Start changeHeader method -> Parameters [{}]",request);
        String response = userService.changeHeader(getLogedUser(),request);
        log.info("End changeHeader method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDTO>> getFollowingPosts()
            throws UserNotFoundException, PostNotFoundException {
        log.info("Start getHomePosts method");
        List<PostDTO> response = userService.getFollowingPosts(getLogedUser());
        log.info("End getHomePosts method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/search-user")
    public ResponseEntity<ResponseSearchUserVO> searchUser(@RequestParam String username)
            throws UserNotFoundException {
        log.info("Start searchUser method");
        ResponseSearchUserVO response = userService.searchUser(getLogedUser(),username);
        log.info("End searchUser method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/follow-request")
    public ResponseEntity<String> followRequest (@Valid @RequestBody String request) throws UserNotFoundException {
        log.info("Start followRequest method");
        String response = userService.followRequest(getLogedUser(),request);
        log.info("End followRequest method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow (@Valid @RequestBody String request) throws UserNotFoundException {
        log.info("Start unfollow method");
        String response = userService.unfollow(getLogedUser(),request);
        log.info("End unfollow method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/accept-request")
    public ResponseEntity<?> acceptRequest (@Valid @RequestBody String userRequest)
            throws UserNotFoundException, FriendRequestNotFoundException {
        log.info("Start acceptRequest method");
        userService.acceptRequest(getLogedUser(),userRequest);
        log.info("End acceptRequest method");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/show-profile")
    public ResponseEntity<ResponseShowProfileVO> showProfile(@RequestParam String username)
            throws UserNotFoundException, PostNotFoundException {
        log.info("Start showProfile method -> Parameters [{}]",username);
        String usernameRequest = username;
        if(usernameRequest.equals("")){
            usernameRequest = getLogedUser();
        }
        ResponseShowProfileVO response = userService.showProfile(usernameRequest);
        log.info("End showProfile method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/edit-user")
    public ResponseEntity<?> editUser(@Valid @RequestBody RequestEditUserVO request)
            throws UserNotFoundException, UserUsedException {
        log.info("Start editUser method -> Parameters [{}]",request);
        userService.editUser(getLogedUser(),request);
        log.info("End editUser method");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(@Valid @RequestBody PostDTO request)
            throws UserNotFoundException, PostNotFoundException {
        log.info("Start like method -> Parameters [{}]",request);
        userService.like(getLogedUser(),request);
        log.info("End like method");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getLiked")
    public ResponseEntity<List<PostDTO>> getLiked() throws UserNotFoundException, PostNotFoundException {
        log.info("Start getLiked method");
        List<PostDTO> likedPosts = userService.getLiked(getLogedUser());
        log.info("End getLiked method");
        return new ResponseEntity<>(likedPosts,HttpStatus.OK);
    }

    /*
    @PostMapping("/post/add-comment")
    public ResponseEntity<ResponseAddCommentVO> addComment(@Valid @RequestBody RequestAddCommentVO request)
            throws UserNotFoundException, PostNotFoundException {
        log.info("Start addComment method -> Parameters [{}]",request);
        ResponseAddCommentVO response = userService.addComment(getLogedUser(),request);
        log.info("End addComment method");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    */
    private String getLogedUser(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

}
