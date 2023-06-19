import { Post } from "./Post";

export interface ProfileInfo {

    username: string;
    fullname: string;
    nfollowers: number;
    nfollowing: number;
    nposts: number;
    profilePic: string;
    
    posts: Post[];

    followRequests: string[];

}