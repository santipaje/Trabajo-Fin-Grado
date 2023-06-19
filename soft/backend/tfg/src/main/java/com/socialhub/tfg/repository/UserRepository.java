package com.socialhub.tfg.repository;

import com.socialhub.tfg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.followers FROM User u WHERE u.id = :userId")
    List<User> getFollowers(@Param("userId") Integer userId);

    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    List<User> getFollowing(@Param("userId") Integer userId);

}
