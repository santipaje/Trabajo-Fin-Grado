package com.socialhub.tfg.repository;

import com.socialhub.tfg.domain.Post;
import com.socialhub.tfg.domain.dto.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findById(Integer id);

}
