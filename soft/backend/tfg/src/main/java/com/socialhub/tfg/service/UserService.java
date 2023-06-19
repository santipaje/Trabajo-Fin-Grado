package com.socialhub.tfg.service;

import com.socialhub.tfg.domain.dto.PostDTO;
import com.socialhub.tfg.domain.vo.*;
import com.socialhub.tfg.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface UserService {

    ResponseRegisterVO register(RequestRegisterVO request) throws EmailUsedException, UserUsedException, IOException;

    ResponseVerifyEmail verifyEmail(String token) throws UserNotFoundException, UserAlreadyEnabledException, TokenExpiredException;

    ResponseLoginVO login(RequestLoginVO request) throws IncorrectPwdException, UserNotFoundException;

    void forgotPass(String request) throws UserNotFoundException;

    ResponseAuthToken authToken(String token) throws TokenExpiredException;

    void changePass(RequestChangePassword request) throws UserNotFoundException;

    ResponseCreatePostVO createPost(String username, RequestCreatePostVO request) throws UserNotFoundException, ImagePostException, EmptyFileException;

    String changeHeader(String username, RequestChangeHeaderVO request) throws UserNotFoundException, PostNotFoundException;

    //ResponseAddCommentVO addComment(String username, RequestAddCommentVO request) throws UserNotFoundException, PostNotFoundException;

    List<PostDTO> getFollowingPosts(String username)
            throws UserNotFoundException, PostNotFoundException;

    ResponseSearchUserVO searchUser(String logged, String username) throws UserNotFoundException;

    String followRequest(String follower, String followed) throws UserNotFoundException;

    String unfollow(String follower, String followed) throws UserNotFoundException;

    void acceptRequest(String receiver, String requester) throws UserNotFoundException, FriendRequestNotFoundException;

    ResponseShowProfileVO showProfile(String username) throws UserNotFoundException, PostNotFoundException;

    void editUser(String username, RequestEditUserVO request) throws UserNotFoundException, UserUsedException;

    void like(String username, PostDTO request) throws UserNotFoundException, PostNotFoundException;

    List<PostDTO> getLiked(String username) throws UserNotFoundException, PostNotFoundException;
}
