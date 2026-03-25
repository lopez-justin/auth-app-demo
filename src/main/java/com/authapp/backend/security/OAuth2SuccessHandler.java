package com.authapp.backend.security;

import com.authapp.backend.model.AuthProvider;
import com.authapp.backend.model.Role;
import com.authapp.backend.model.UserEntity;
import com.authapp.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtilsService jwtUtilsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        OAuth2AuthenticationToken tokenAuth = (OAuth2AuthenticationToken) authentication;
        String provider = tokenAuth.getAuthorizedClientRegistrationId();

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email;
        String name;

        if ("google".equals(provider)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");

        } else if ("github".equals(provider)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("login");

            if (email == null) {
                email = name + "@github.com";
            }
        } else {
            throw new RuntimeException("Provider not supported");
        }

        AuthProvider authProvider = AuthProvider.valueOf(provider.toUpperCase());

        String finalEmail = email;
        UserEntity user = userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        UserEntity.builder()
                                .email(finalEmail)
                                .name(name)
                                .provider(authProvider)
                                .role(Role.USER)
                                .build()
                ));

        String token = jwtUtilsService.generateToken(user.getEmail());
        String redirectUrl = "http://localhost:4200/oauth-success?token=" + token;

        response.sendRedirect(redirectUrl);
    }

}
