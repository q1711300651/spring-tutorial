package my.spring.security.tests.example.impl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import my.spring.security.tests.example.MessageService;

@Service
public class HelloMessageService implements MessageService {

    @Override
    @PreAuthorize("authenticated")
    public String getMessage() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello " + authentication;
    }
}
