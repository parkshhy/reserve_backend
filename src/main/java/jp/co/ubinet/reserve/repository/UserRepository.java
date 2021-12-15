package jp.co.ubinet.reserve.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import jp.co.ubinet.reserve.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   
   Optional<User> findByUserEmail(String userEmail);
}
