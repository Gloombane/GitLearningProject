package com.example.gitlearningprojectwithspringandreact.repositories.sessionRepository;

import com.example.gitlearningprojectwithspringandreact.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findBySessionId(String sessionId);
}
