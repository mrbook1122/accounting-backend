package cdut.accounting.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * Jwt工具类
 */
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}
