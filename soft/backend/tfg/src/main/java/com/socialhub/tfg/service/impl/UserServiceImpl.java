package com.socialhub.tfg.service.impl;

import com.socialhub.tfg.domain.*;
import com.socialhub.tfg.domain.dto.PostDTO;
import com.socialhub.tfg.domain.vo.*;
import com.socialhub.tfg.exceptions.*;
import com.socialhub.tfg.repository.*;
import com.socialhub.tfg.security.UserSecurity;
import com.socialhub.tfg.security.UserSecurityServiceImpl;
import com.socialhub.tfg.service.EmailService;
import com.socialhub.tfg.service.UserService;
import com.socialhub.tfg.utils.JWTUtils;
import jakarta.persistence.PersistenceContext;
import org.apache.tomcat.util.codec.binary.Base64;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BCryptPasswordEncoder bCrypt;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserSecurityServiceImpl userSecurityService;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${defaultProfilePic}")
    private String defProfPicPath;

    @Override
    public ResponseRegisterVO register(RequestRegisterVO request)
            throws EmailUsedException, UserUsedException, IOException {
        log.info("Start register method");
        // Validaciones de email y usuario
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserUsedException("There is already a user with this username: " + request.getUsername());
        }
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailUsedException("There is already a user with this email " + request.getEmail());
        }
        String encodedPassword = bCrypt.encode(request.getPassword());
        User user = modelMapper.map(request, User.class);
        user.setPassword(encodedPassword);
        user.setProfilePic(getDefaultProfilePic());
        userRepository.save(user);
        String token = jwtUtils.createToken(user.getUsername(), user.getEmail());
        emailService.sendEmailVerification(user,token);

        ResponseRegisterVO response = modelMapper.map(user, ResponseRegisterVO.class);
        log.info("End register method");
        return response;
    }

    @Override
    public ResponseVerifyEmail verifyEmail(String token)
            throws UserNotFoundException, UserAlreadyEnabledException, TokenExpiredException {
        log.info("Start verifyEmail method");
        String username;
        try{
            username = jwtUtils.getAuthentication(token).getName();
        }catch (NullPointerException e){
            throw new TokenExpiredException("Token has expired");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("Username:" + username + "not found"));
        if(user.isEnabled()){
            throw new UserAlreadyEnabledException("The account has already been verified");
        }
        user.setEnabled(true);
        // update de user para poner el Enabled a true
        userRepository.save(user);
        ResponseVerifyEmail response = new ResponseVerifyEmail();
        response.setVerified(true);
        log.info("End verifyEmail method");
        return response;
    }

    @Override
    public ResponseLoginVO login(RequestLoginVO request)
            throws IncorrectPwdException, UserNotFoundException{
        log.info("Start login method");
        User u = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UserNotFoundException("username " + request.getUsername() + " does not exist in the system"));
        if (!bCrypt.matches(request.getPassword(), u.getPassword())){
            throw new IncorrectPwdException("Password is not correct, please try again");
        }
        ResponseLoginVO response = new ResponseLoginVO();
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword(),
                Collections.emptyList()
        );
        authenticationManager.authenticate(usernamePAT);
        HttpHeaders header  = new HttpHeaders();
        UserSecurity userSecurity = (UserSecurity)userSecurityService.loadUserByUsername(request.getUsername());
        User user = userSecurity.getUser();
        log.info(user.getUsername());
        String token = jwtUtils.createToken(user.getUsername(), user.getEmail());
        log.info(token);
        header.add("Authorization", "Bearer " + token);
        response.setId_user(user.getIdUser());
        response.setHeader(header);
        log.info("End login method");
        return response;
    }

    @Override
    public void forgotPass(String emailRequest) throws UserNotFoundException{
        User user = userRepository.findByEmail(emailRequest).orElseThrow(
                () -> new UserNotFoundException("User with email: " + emailRequest + "not found"));
        String token = jwtUtils.createToken(user.getUsername(), user.getEmail());
        emailService.sendPassResetEmail(user,token);
    }

    @Override
    public ResponseAuthToken authToken(String token) throws TokenExpiredException {
        log.info("Start authToken method");
        String username;
        try{
            username = jwtUtils.getAuthentication(token).getName();
        }catch (NullPointerException e){
            throw new TokenExpiredException("Token has expired");
        }
        log.info("End authToken method");
        ResponseAuthToken response = new ResponseAuthToken();
        response.setIsAuth(Boolean.TRUE);
        response.setUsername(username);
        return response;
    }

    @Override
    public void changePass(RequestChangePassword request)
            throws UserNotFoundException {
        log.info("Start changePass method");
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new UserNotFoundException("Username:" + request.getUsername() + "not found"));
        String encodedPassword = bCrypt.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        log.info("End changePass method");
    }

    @Override
    public ResponseCreatePostVO createPost(String username, RequestCreatePostVO request)
            throws UserNotFoundException, ImagePostException, EmptyFileException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        MultipartFile image = request.getPostContent();
        if(image.isEmpty()){
            throw new EmptyFileException("Failed to store, empty file");
        }
        String text = request.getHeader();
        Post post = new Post();
        try {
            byte[] imageBytes = image.getBytes();
            post.setPostContent(imageBytes);
            post.setHeader(text);
            post.setCreationDate(new Date(System.currentTimeMillis()));
            post.setUser(user);
            postRepository.save(post);
        } catch (IOException e) {
            throw new ImagePostException("image could not be converted to bytes");
        }
        return modelMapper.map(post, ResponseCreatePostVO.class);
    }

    @Override
    public String changeHeader(String username, RequestChangeHeaderVO request)
            throws UserNotFoundException, PostNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        Post post = postRepository.findById(request.getIdPost()).orElseThrow(
                ()-> new PostNotFoundException("Post not found"));
        if(!post.getUser().equals(user)) {
            throw new PostNotFoundException("Post posted by: " + username + "not found");
        }
        post.setHeader(request.getHeader());
        postRepository.save(post);
        return request.getHeader();

    }

    @Override
    public List<PostDTO> getFollowingPosts(String username)
            throws UserNotFoundException, PostNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        Integer idUser = user.getIdUser();
        List<Post> followingPosts = followRepository.followingPosts(idUser, entityManager);
        List<PostDTO> response = new ArrayList<>();
        for ( Post p : followingPosts ){
            PostDTO dto = postEntityToDTO(p);
            Boolean liked = isPostLiked(user,p);
            if(liked){
                dto.setLikeImage(Boolean.TRUE);
            }
            response.add(dto);
        }
        // primero comprobar isEmpty para ver si esta vacia
        return response;
    }

    @Override
    public ResponseSearchUserVO searchUser(String logged, String username)
            throws UserNotFoundException {
        User userSearched = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        User userLogged = userRepository.findByUsername(logged).orElseThrow(
                () -> new UserNotFoundException("Username:" + logged + "not found"));

        Boolean existFriendReq = friendRequestRepository.existsByUserRequesterAndUserReceiver(userLogged,userSearched);

        ResponseSearchUserVO response = new ResponseSearchUserVO();
        String profilePicBase64 = Base64.encodeBase64String(userSearched.getProfilePic());
        Integer nFollowers = userSearched.getFollowers().size();
        Integer nFollowing = userSearched.getFollowing().size();
        response.setUsername(username);
        response.setProfilePic(profilePicBase64);
        response.setNFollowers(nFollowers);
        response.setNFollowing(nFollowing);
        response.setNPosts(userSearched.getPostedByUser().size());

        //userSearched.getFriendRequestsMade().forEach(u -> u.getUsername().equals(username));
        String status = "Follow";
        if(existFriendReq){
            status = "Pending";
        }else{
            Boolean existFollow = followRepository.existsByFollowerAndFollowing(userLogged,userSearched);
            if (existFollow) {
                status = "Followed";
            }
        }
        response.setRequestStatus(status);
        return response;
    }

    @Override
    public String followRequest(String follower, String followed) throws UserNotFoundException {
        User userFollower = userRepository.findByUsername(follower).orElseThrow(
                () -> new UserNotFoundException("Username:" + follower + "not found"));
        User userFollowed = userRepository.findByUsername(followed).orElseThrow(
                () -> new UserNotFoundException("Username:" + followed + "not found"));
        String response = "Followed";
        if(userFollowed.isPrivate()){
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setUserRequester(userFollower);
            friendRequest.setUserReceiver(userFollowed);
            friendRequest.setRequestDate(new Date(System.currentTimeMillis()));
            friendRequest.setAccepted(false);
            response = "Pending";
            friendRequestRepository.save(friendRequest);
        }else{
            Follow follow = new Follow();
            follow.setFollowing(userFollowed);
            follow.setFollower(userFollower);
            follow.setFollowDate(new Date(System.currentTimeMillis()));
            followRepository.save(follow);
        }

        return response;
    }

    @Override
    public String unfollow(String follower, String followed) throws UserNotFoundException {
        User userFollower = userRepository.findByUsername(follower).orElseThrow(
                () -> new UserNotFoundException("Username:" + follower + "not found"));
        User userFollowed = userRepository.findByUsername(followed).orElseThrow(
                () -> new UserNotFoundException("Username:" + followed + "not found"));
        String response = "Follow";
        Follow follow = followRepository.findByFollowerAndFollowing(userFollower,userFollowed);
        if(follow!=null) {
            followRepository.delete(follow);
        }
        return response;
    }

    @Override
    public void acceptRequest(String receiver, String requester)
            throws UserNotFoundException, FriendRequestNotFoundException {
        User userReceiver = userRepository.findByUsername(receiver).orElseThrow(
                () -> new UserNotFoundException("Username:" + receiver + "not found"));
        User userRequester = userRepository.findByUsername(requester).orElseThrow(
                () -> new UserNotFoundException("Username:" + requester + "not found"));

        FriendRequest fr = friendRequestRepository.findByRequesterIdAndReceiverId
                (userReceiver.getIdUser(), userRequester.getIdUser()).orElseThrow(
                    () -> new FriendRequestNotFoundException("Friend Request no found"));
        // it is necessary to delete the follow request because it is not pending anymore
        friendRequestRepository.delete(fr);
        Follow follow = new Follow();
        follow.setFollowing(userReceiver);
        follow.setFollower(userRequester);
        follow.setFollowDate(new Date(System.currentTimeMillis()));
        followRepository.save(follow);
    }

    @Override
    public ResponseShowProfileVO showProfile(String username) throws UserNotFoundException, PostNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User loggedUser = userRepository.findByUsername(loggedUsername).orElseThrow(
                () -> new UserNotFoundException("Username:" + loggedUsername + "not found"));

        ResponseShowProfileVO response = new ResponseShowProfileVO();
        String profilePicBase64 = Base64.encodeBase64String(user.getProfilePic());
        Integer nFollowers = user.getFollowers().size();
        Integer nFollowing = user.getFollowing().size();
        response.setUsername(username);
        response.setProfilePic(profilePicBase64);
        response.setNFollowers(nFollowers);
        response.setNFollowing(nFollowing);
        response.setNPosts(user.getPostedByUser().size());
        List<PostDTO> postlist = new ArrayList<>();
        for ( Post p : user.getPostedByUser() ){
            PostDTO dto = postEntityToDTO(p);
            Boolean liked = isPostLiked(loggedUser,p);
            if(liked){
                dto.setLikeImage(Boolean.TRUE);
            }
            postlist.add(dto);
        }
        //user.getPostedByUser().forEach(post -> postlist.add(modelMapper.map(post,PostDTO.class)));
        response.setPosts(postlist);
        List<String> requestReceivedList = new ArrayList<>();
        user.getFriendRequestsReceived().forEach(f -> requestReceivedList.add(f.getUserReceiver().getUsername()));
        response.setFollowRequests(requestReceivedList);
        return response;
    }

    @Override
    public void editUser(String username, RequestEditUserVO request) throws UserNotFoundException, UserUsedException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        if (!request.getFullname().equals("")) {
            user.setFullname(request.getFullname());
        }
        if (!request.getUsername().equals("")) {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new UserUsedException("There is already a user with this username: " + request.getUsername());
            }
            user.setUsername(request.getUsername());
        }
        userRepository.save(user);
    }

    @Override
    public void like(String username, PostDTO request) throws UserNotFoundException, PostNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        Post post = postRepository.findById(request.getIdPost()).orElseThrow(
                ()-> new PostNotFoundException("Post not found"));
        Boolean done = Boolean.FALSE;
        for( Likes l : user.getLikes() ){
            Post likedpost = postRepository.findById(l.getLikedPost().getIdPost()).orElseThrow(
                    () -> new PostNotFoundException("Post not found"));
            if(post.getIdPost() == likedpost.getIdPost()){
                done = Boolean.TRUE;
                likesRepository.delete(l);
            }
        }
        if(!done){
            Likes like = new Likes();
            like.setLikingUser(user);
            like.setLikedPost(post);
            like.setCreationDate(new Date(System.currentTimeMillis()));
            likesRepository.save(like);
        }


    }

    @Override
    public List<PostDTO> getLiked(String username) throws UserNotFoundException, PostNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        List<Likes> likesUser = user.getLikes();
        List<PostDTO> likedPosts = new ArrayList<>();
        for( Likes like : likesUser ){
            Post post = postRepository.findById(like.getLikedPost().getIdPost()).orElseThrow(
                    () -> new PostNotFoundException("Post not found"));
            PostDTO dto = postEntityToDTO(post);
            Boolean liked = isPostLiked(user,post);
            if(liked){
                dto.setLikeImage(Boolean.TRUE);
            }
            likedPosts.add(dto);
        }
        return likedPosts;
    }

    /*
    @Override
    public ResponseAddCommentVO addComment(String username, RequestAddCommentVO request)
            throws UserNotFoundException, PostNotFoundException{
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Username:" + username + "not found"));
        Post post = postRepository.findById(request.getIdPost()).orElseThrow(
                ()-> new PostNotFoundException("Post not found"));
        if(!post.getUser().equals(user)){
            throw new PostNotFoundException("Post posted by: " + username + "not found");
        }
        Comment comment = modelMapper.map(request, Comment.class);
        comment.setCreationDate(new Date(System.currentTimeMillis()));
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return modelMapper.map(comment,ResponseAddCommentVO.class);
    }
    */
    private byte[] getDefaultProfilePic() throws IOException {
        String filePath = defProfPicPath;
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    private PostDTO postEntityToDTO (Post entity){
        PostDTO dto = new PostDTO();
        dto.setIdPost(entity.getIdPost());
        String postBase64 = Base64.encodeBase64String(entity.getPostContent());
        dto.setPostContent(postBase64);
        dto.setText(entity.getHeader());
        dto.setNLikes(entity.getLikes().size());
        dto.setLikeImage(Boolean.FALSE);
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }

    private Boolean isPostLiked (User user, Post post) throws PostNotFoundException {
        Boolean res = Boolean.FALSE;
        for( Likes l : user.getLikes() ){
            Post likedpost = postRepository.findById(l.getLikedPost().getIdPost()).orElseThrow(
                    () -> new PostNotFoundException("Post not found"));
            if(post.getIdPost() == likedpost.getIdPost()){
                res = Boolean.TRUE;
            }
        }
        return res;
    }
}
