package com.packet.indoor.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.packet.indoor.config.JwtConfig;
import com.packet.indoor.domain.UserDetail;
import com.packet.indoor.exception.AuthenticationException;
import com.packet.indoor.util.ErrorMessage;
import com.packet.indoor.util.JwtUtil;
import com.packet.indoor.util.Role;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (jwtConfig.getEnabled()) {
            String authorizationHeader = request.getHeader("Authorization");
            if (StringUtils.isEmpty(authorizationHeader)) throw new AuthenticationException(ErrorMessage.MISSING_AUTHENTICATION);
            if (authorizationHeader.startsWith("Bearer ")) throw new AuthenticationException(ErrorMessage.WRONG_AUTHORIZATION_FORMAT);
            String encodedJWT = authorizationHeader.substring(7);

            DecodedJWT decodedJWT = jwtUtil.verifyAndDecodeJWT(encodedJWT);
            UserDetail userDetail = jwtUtil.getUserDetail(decodedJWT);

            if (!userDetail.getIsActive()) throw new AuthenticationException(ErrorMessage.USER_NOT_ACTIVE);
            if (userDetail.getIsDeleted()) throw new AuthenticationException(ErrorMessage.USER_DELETED);;

            request.setAttribute("user", userDetail);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            List<String> allRoles = Stream.of(Role.values())
                    .map(Role::value)
                    .collect(Collectors.toList());
            UserDetail userDetail = UserDetail.create("non-jwt", allRoles, true, false);

            request.setAttribute("user", userDetail);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
