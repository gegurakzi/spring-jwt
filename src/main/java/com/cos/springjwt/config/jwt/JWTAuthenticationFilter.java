package com.cos.springjwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.springjwt.config.auth.PrincipalDetails;
import com.cos.springjwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// /login 요청해서 username, password 전송하면
// 스프링에서 구현된 UsernamePasswordAuthenticationFilter가 동작함
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {


        try{
            // 1. username, password를 받아서 로그인을 시도함
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 2. authenticationManager로 로그인 시도를 하면  PrincipalDetailService가 호출되어 loadUserByUsername() 함수가 실행됨
            Authentication authentication
                    = authenticationManager.authenticate(authenticationToken);

            // 3. 반환 시 authentication 객체를 세션에 담음 -> JWT는 세션을 만들 이유가 없지만 시큐리티가 제공하는 사용자의 권한 관리를 위해 만듬
            return authentication;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // AttemptAuthentication실행 후 인증이 정상적으로 진행되었으면 successfulAuthentication 함수가 실행됨
    // 4. JWT를 발행하고 request한 사용자에게 토큰은 응답해줌
   @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATIONAL_TIME))
                .withClaim("id", principalDetails.getId())
                .withClaim("username", principalDetails.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader("Authorization", "Bearer "+jwtToken);
    }
}
