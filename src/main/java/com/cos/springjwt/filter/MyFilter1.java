package com.cos.springjwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class MyFilter1  implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //아이디와 패스워드가 정상적으로 들어와서 로그인이 완료되면 토큰을 생성, 응답해준다

        String HeaderAuth = req.getHeader("Authorization");
        if(Objects.equals(req.getMethod(), "POST")){
            System.out.println("필터1 - HeaderAuth: " + HeaderAuth);

            if(Objects.equals(HeaderAuth, "hello")){
                chain.doFilter(req, res);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증 불가");
            }
        }
    }
}
