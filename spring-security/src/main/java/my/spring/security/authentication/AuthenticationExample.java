package my.spring.security.authentication;

import java.util.Scanner;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Пример простой программы для обработки
 */
public class AuthenticationExample {

    private static final AuthenticationManager am = new SampleAuthenticationManager();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Please enter your Name:");
            String name = in.nextLine();
            System.out.println("Please enter password:");
            String password = in.nextLine();
            try {
                final Authentication request =
                        new UsernamePasswordAuthenticationToken(name, password);
                final Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
            } catch (AuthenticationException e) {
                System.out.println("Authentication Exception: " + e.getMessage());
                continue;
            }
            System.out.println("Successfully authenticated. Security context contains:" +
                    SecurityContextHolder.getContext().getAuthentication());
            break;
        }
    }
}

