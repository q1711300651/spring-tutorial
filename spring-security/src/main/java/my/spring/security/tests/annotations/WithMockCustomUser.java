package my.spring.security.tests.annotations;

import java.util.Collection;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import my.spring.security.tests.example.MessageService;


/**
 * Пример реализации собственной аннотации, что использует WithSecurityContext
 * WithSecurityContext - обязывает создать собственный SecurityContext
 */
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    String name();
    String username();
}


/**
 * Кастомная фабрика для создания SecurityContext для тестовых методов
 */
class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    /**
     * Так же WithSecurityContextFactory, являються частю контекста, так что вполне могут загружать
     * внедренные зависимости
     */
    @Inject
    private MessageService messageService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        CustomUserDetails principal =
                new CustomUserDetails(customUser.name(), customUser.username());
        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }

}


class CustomUserDetails implements UserDetails {

    public CustomUserDetails(final String name, final String username) {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


}