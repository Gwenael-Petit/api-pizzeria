package utils;
// code pompé ici : https://developer.okta.com/blog/2018/10/31/jwts-with-java
// lui-même inspiré par : https://www.baeldung.com/java-json-web-tokens-jjwt
// et sinon la doc : https://github.com/jwtk/jjwt/blob/master/README.md


import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dao.UsersDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtManager {
	
	private static UsersDAO usersDao = new UsersDAO();

    public static String createJWT(String login, String pwd) {
        // The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(pwd);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // Let's set the JWT Claims
        JwtBuilder token = Jwts.builder()
                .setId(UUID.randomUUID().toString().replace("-", ""))
                .setIssuedAt(now)
                .setSubject(login)
                .signWith(signingKey, signatureAlgorithm);

        // if it has been specified, let's add the expiration
        long ttlMillis = 1000 * 60 * 30;
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            token.setExpiration(exp); // 20mn par defaut
        }
        // Builds the JWT and serializes it to a compact, URL-safe string
        return token.compact();
    }

    public static Claims decodeJWT(String jwt, String pwd) throws Exception {
        // This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Base64.getDecoder().decode(pwd))
                .build()
                // verifie la signature et l'iat
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
    
    public static boolean verifToken(String authorization) throws IOException {
		if(authorization == null || !authorization.startsWith("Bearer")) {
			return false;
		}
		String tokenB64 = authorization.substring("Bearer".length()).trim();
		String body64 = tokenB64.split("\\.")[1];
		String body = new String(Base64.getDecoder().decode(body64));
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json = mapper.readTree(body);
		
		String userLogin = json.get("sub").asText();
		String pwd = usersDao.findPasswordByLogin(userLogin);
		try {
    		Claims claims = JwtManager.decodeJWT(tokenB64, pwd);
    	} catch(Exception e) {
			return false;
    	}
		return true;
    }
    
}
