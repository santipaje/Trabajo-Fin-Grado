package com.socialhub.tfg.repository;

import com.socialhub.tfg.domain.Comment;
import com.socialhub.tfg.domain.Follow;
import com.socialhub.tfg.domain.Post;
import com.socialhub.tfg.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    /*
        select * from tfg_post post where post.id_user in (
        select follow.id_following from tfg_follow follow where id_follower = :FOLLOWER_ID)
        order by post.creation_date desc;
     */
     default List<Post> followingPosts(Integer followerId, EntityManager entityManager) {
         CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
         CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
         Root<Post> postRoot = criteriaQuery.from(Post.class);
         Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
         Root<Follow> followRoot = subquery.from(Follow.class);

         subquery.select(followRoot.get("following").get("id"));
         subquery.where(criteriaBuilder.equal(followRoot.get("follower").get("id"), followerId));

         criteriaQuery.select(postRoot);
         criteriaQuery.where(postRoot.get("user").get("id").in(subquery));
         criteriaQuery.orderBy(criteriaBuilder.desc(postRoot.get("creationDate")));

         TypedQuery<Post> query = entityManager.createQuery(criteriaQuery);
         return query.getResultList();
     }

    Boolean existsByFollowerAndFollowing(User follower, User following);

    Follow findByFollowerAndFollowing(User follower, User following);

}
