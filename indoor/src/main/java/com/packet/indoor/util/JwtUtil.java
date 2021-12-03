package com.packet.indoor.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.packet.indoor.config.JwtConfig;
import com.packet.indoor.domain.UserDetail;
import com.packet.indoor.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class JwtUtil {

    private JwtConfig jwtConfig;

    public DecodedJWT verifyAndDecodeJWT(String token){
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtConfig.getIssuer())
                .withClaimPresence("email")
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
        Claim emailClaim = decodedJWT.getClaim("email");
        Claim roleClaim = decodedJWT.getClaim("role");
        Claim isActiveClaim = decodedJWT.getClaim("isActive");
        Claim isDeletedClaim = decodedJWT.getClaim("isDeleted");

        String email = emailClaim.asString();
        List<String> roles = roleClaim.asList(String.class);
        Boolean isActive = isActiveClaim.asBoolean();
        Boolean isDeleted = isDeletedClaim.asBoolean();

        return UserDetail.create(email, roles, isActive, isDeleted);
    }
}
