package com.packet.indoor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.packet.indoor.config.JwtConfig;
import com.packet.indoor.domain.UserDetail;
import com.packet.indoor.domain.user.User;
import com.packet.indoor.domain.user.UserStatus;
import com.packet.indoor.domain.user.Username;
import com.packet.indoor.domain.user.dto.LoginResponseDto;
import com.packet.indoor.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class JwtUtil {

    private JwtConfig jwtConfig;

    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;

    public LoginResponseDto verifyAndGenerateJsonWebToken(User user){
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("svc", "Eugene");

        Username username = user.getUsername();
        String role = user.getRole().value();
        UserStatus userStatus = user.getUserStatus();

        if (userStatus.getIsDeleted()) throw new AuthenticationException(ErrorMessage.USER_DELETED);

        String token = JWT.create()
                .withHeader(header)
                .withClaim("username", username.getName())
                .withClaim("groupname", username.getGroup())
                .withClaim("role", role)
                .withClaim("isActive", userStatus.getIsActive())
                .withClaim("isDeleted", userStatus.getIsDeleted())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .withIssuer(jwtConfig.getIssuer())
                .sign(algorithm);

        LoginResponseDto responseDto = LoginResponseDto.create(token, JWT_TOKEN_VALIDITY, role, userStatus.getIsActive());
        return responseDto;
    }

    public DecodedJWT verifyAndDecodeJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtConfig.getIssuer())
                .withClaimPresence("username")
                .withClaimPresence("groupname")
                .withClaimPresence("role")
                .withClaimPresence("isActive")
                .withClaimPresence("isDeleted")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        Claim typ = jwt.getHeaderClaim("typ");
        Claim svc = jwt.getHeaderClaim("svc");
        if(typ.isNull() || !typ.asString().equals("JWT")) throw new AuthenticationException(ErrorMessage.INVALID_JWT_TOKEN);
        if(svc.isNull() || !svc.asString().equals("Eugene")) throw new AuthenticationException(ErrorMessage.INVALID_JWT_TOKEN);
        return jwt;
    }

    public UserDetail getUserDetail(DecodedJWT decodedJWT) {
        Claim usernameClaim = decodedJWT.getClaim("username");
        Claim groupnameClaim = decodedJWT.getClaim("groupname");
        Claim roleClaim = decodedJWT.getClaim("role");
        Claim isActiveClaim = decodedJWT.getClaim("isActive");
        Claim isDeletedClaim = decodedJWT.getClaim("isDeleted");

        String username = usernameClaim.asString();
        String groupname = groupnameClaim.asString();
        List<String> roles = roleClaim.asList(String.class);
        Boolean isActive = isActiveClaim.asBoolean();
        Boolean isDeleted = isDeletedClaim.asBoolean();

        return UserDetail.create(username, groupname, roles, isActive, isDeleted);
    }
}
