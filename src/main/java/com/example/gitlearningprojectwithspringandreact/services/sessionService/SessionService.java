package com.example.gitlearningprojectwithspringandreact.services.sessionService;

import com.example.gitlearningprojectwithspringandreact.entities.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SessionService {
    Session createSession(String principalName, long maxInactiveIntervalSeconds);
    Optional<Session> getSession(String sessionId);
    void deleteSession(String sessionId);
    void updateLastAccessTime(Session session);
    boolean isValid(String sessionId);
    String getUsername(String sessionId);

    String getRole(String sessionId);
}
