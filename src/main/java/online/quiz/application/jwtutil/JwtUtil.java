package online.quiz.application.jwtutil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
public class JwtUtil
{
    final String SECRET_KEY="bdjalsjd783j4kfkdjfirmoeralskvmxnfjfkgoeu348tjgdmjf";
    private String generateJwtToken(Map<String,Object> extractClaims, UserDetails userDetails)
    {

        String compact = Jwts.builder().
                setClaims(extractClaims).
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSigningkey(), SignatureAlgorithm.HS256).compact();
        return compact;
    }
    public String generateToken(UserDetails userDetails)
    {
        return generateJwtToken(new HashMap<>(),userDetails);
    }
    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder().setSigningKey(getSigningkey()).build().parseClaimsJws(token).getBody();
    }

    public String extractUserName(String token)
    {
        return extractClaims(token,Claims::getSubject);
    }

    private Date extractExpiration(String token)
    {
        return extractClaims(token,Claims::getExpiration);
    }
    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsResolvers)
    {
        final Claims claims=extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSigningkey()
    {

        //Key secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

}
