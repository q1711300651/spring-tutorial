package my.spring.security.about;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * описание API
 */
public class AuthenticationSample {


    /**
     * Получить доступ к текущему зарегестрированному пользователю
     *
     * UserDetails - это ключевой елемент Spring Security. Адаптер между пользователем системы в дб и
     * тем что нужно в SecurityContextHolder
     */
    public void getUser() {
        final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            System.out.println(user.getUsername());
        } else {
            String userName = principal.toString();
            System.out.println();
        }
    }
}
