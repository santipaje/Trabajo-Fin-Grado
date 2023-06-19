package com.socialhub.tfg.repository;

import com.socialhub.tfg.domain.Comment;
import com.socialhub.tfg.domain.FriendRequest;
import com.socialhub.tfg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query("SELECT fr FROM FriendRequest fr WHERE fr.userRequester.idUser = :requesterId AND fr.userReceiver.idUser = :receiverId")
    Optional<FriendRequest> findByRequesterIdAndReceiverId(@Param("requesterId") int requesterId, @Param("receiverId") int receiverId);

    Boolean existsByUserRequesterAndUserReceiver(User requester, User receiver);

}
