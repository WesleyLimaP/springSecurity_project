package com.gestao.projeto.master.service;

import com.gestao.projeto.master.entity.Role;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    JwtEncoder encoder;
    @Autowired
    JwtDecoder decoder;



    @Transactional
    public String generateToken(User userDetails) {
        var expireToken = 300L;
        var now = Instant.now();
        var scopes = userDetails.getRoles()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.joining(" "));
        var claims = JwtClaimsSet.builder()
                .issuer("myBackend")
                .subject(userDetails.getId().toString())
                .claim("scope", scopes)
                .expiresAt(now.plusSeconds(expireToken))
                .issuedAt(now)
                .build();

        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }
}


