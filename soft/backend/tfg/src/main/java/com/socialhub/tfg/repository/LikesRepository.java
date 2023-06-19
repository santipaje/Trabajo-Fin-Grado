package com.socialhub.tfg.repository;

import com.socialhub.tfg.domain.Comment;
import com.socialhub.tfg.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Integer> {



}
