package com.produtoapi.historicoservice;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Base64;
import javax.crypto.SecretKey;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        String encoded = Base64.getEncoder().encodeToString(key.getEncoded());

        System.out.println(encoded);
    }
}