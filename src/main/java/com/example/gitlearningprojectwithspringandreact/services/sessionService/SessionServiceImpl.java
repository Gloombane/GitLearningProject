package com.example.gitlearningprojectwithspringandreact.services.sessionService;

import com.example.gitlearningprojectwithspringandreact.entities.Session;
import com.example.gitlearningprojectwithspringandreact.entities.User;
import com.example.gitlearningprojectwithspringandreact.repositories.sessionRepository.SessionRepository;
import com.example.gitlearningprojectwithspringandreact.repositories.userRepository.UserRepository;
import com.example.gitlearningprojectwithspringandreact.services.sessionService.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, UserRepository userRepository) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Session createSession(String principalName, long maxInactiveIntervalSeconds) {
        long now = Instant.now().toEpochMilli();
        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .principalName(principalName)
                .creationTime(now)
                .lastAccessTime(now)
                .maxInactiveInterval(maxInactiveIntervalSeconds * 1000) // миллисекунды
                .expiryTime(now + maxInactiveIntervalSeconds * 1000)
                .build();

        return sessionRepository.save(session);
    }

    @Override
    public Optional<Session> getSession(String sessionId) {
        Optional<Session> sessionOpt = sessionRepository.findBySessionId(sessionId);
        sessionOpt.ifPresent(this::updateLastAccessTime); // обновляем время последнего доступа
        return sessionOpt;
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionRepository.findBySessionId(sessionId).ifPresent(sessionRepository::delete);
    }

    @Override
    public void updateLastAccessTime(Session session) {
        long now = Instant.now().toEpochMilli();
        session.setLastAccessTime(now);
        session.setExpiryTime(now + session.getMaxInactiveInterval());
        sessionRepository.save(session);
    }
    @Override
    public boolean isValid(String sessionId) {
        Optional<Session> optional = sessionRepository.findBySessionId(sessionId);
        if (optional.isEmpty()) {
            return false;
        }
        Session session = optional.get();
        return session.getExpiryTime() != null && session.getExpiryTime() > System.currentTimeMillis();
    }

    @Override
    public String getUsername(String sessionId) {
        return sessionRepository.findBySessionId(sessionId)
                .map(Session::getPrincipalName)  // в твоей сущности поле `PRINCIPAL_NAME`
                .orElse(null);
    }

    @Override
    public String getRole(String sessionId) {
        Optional<Session> optionalSession = sessionRepository.findBySessionId(sessionId);
        if (optionalSession.isEmpty()) {
            return null;
        }

        String principalName = optionalSession.get().getPrincipalName();
        Optional<User> optionalUser = userRepository.findByEmail(principalName);

        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get().getRole();
    }

}

