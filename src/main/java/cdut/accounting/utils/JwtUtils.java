package cdut.accounting.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Jwt工具类
 */
public class JwtUtils {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 生成token
     */
    public static String generateToken(String name) {
        return Jwts.builder().setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 24))
                .signWith(key).compact();
    }

    /**
     * 认证并刷新token
     *
     * @param response httpResponse,在refreshToken header中存入新的token
     */
    public static Authentication authAndRefreshToken(String token, HttpServletResponse response) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        // 判断token是否快要过期
        Date exp = claims.getExpiration();
        Date now = new Date();
        if (exp.getTime() - now.getTime() < 1000 * 60 * 10) { // 10分钟
            String newToken = generateToken(claims.getSubject());
            response.setHeader("refreshToken", newToken);
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

    /**
     * 从token中获取认证信息
     */
    public static Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

    /**
     * 从SecurityContext获取当前的用户
     */
    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
