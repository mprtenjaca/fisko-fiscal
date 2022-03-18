package com.fisco.fiscal.fiskofiscal.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisco.fiscal.fiskofiscal.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String email;
        String password;

        if(request.getHeader("Content-type").equals("application/json")){
            try {
                Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                email = requestMap.get("username");
                password = requestMap.get("password");
            } catch (IOException e) {
                throw new AuthenticationServiceException(e.getMessage(), e);
            }
        }else{
            email = request.getParameter("username");
            password = request.getParameter("password");
        }

        log.info("Email is: {}", email);
        log.info("Password is: {}", password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        com.fisco.fiscal.fiskofiscal.model.User domainUser = userService.findByEmail(user.getUsername());

        Map<String, Object> data = new HashMap<>();
        data.put("access_token", accessToken);
        data.put("refresh_token", refreshToken);
        data.put("user", domainUser);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");

        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }


}

