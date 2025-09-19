package com.example.collaboration_service.repository;

import com.example.collaboration_service.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    // Finds the specific membership record for a user on a given list.
    // This will be essential for checking a user's role on a list.
    Optional<Membership> findByTaskListIdAndUserId(Long taskListId, Long userId);

    // Finds all membership records for a specific user.
    // This will be used to get all the lists a user is a member of.
    List<Membership> findByUserId(Long userId);
}
