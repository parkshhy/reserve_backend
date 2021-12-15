package jp.co.ubinet.reserve.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

//토큰의 생성과 유효성을 검증
@Component
@Slf4j
@Getter
public class TokenProvider implements InitializingBean{

	private static final String AUTHORITIES_KEY = "auth";

	private final String secret;
	private final long tokenValidityInMilliseconds;
	
	private Key key;
	
	//의존성 주입받기 
	public TokenProvider(
			@Value("${jwt.secret}") String secret,
			@Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
		this.secret = secret;
		this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
	}
	   
	//오버라이드 한 이유는 빈이 생성되고 주입을 받은 후에 secret값을 base64 Decode 해서 key변수에 할당  
	@Override
	public void afterPropertiesSet() throws Exception {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		
	}

	 //JWT 토큰 생성
	  public String createToken(Authentication authentication) {
	      String authorities = authentication.getAuthorities().stream()
	         .map(GrantedAuthority::getAuthority)
	         .collect(Collectors.joining(","));

	      long now = (new Date()).getTime();
	      Date validity = new Date(now + this.tokenValidityInMilliseconds);

	      
	      return Jwts.builder()
	         .setSubject(authentication.getName())
	         .claim(AUTHORITIES_KEY, authorities)
	         .signWith(key, SignatureAlgorithm.HS512)
	         .setExpiration(validity)
	         .compact();
	   }

	//토큰을 파라미로 받아 Authentication 리턴 
	  public Authentication getAuthentication(String token) {
	      Claims claims = Jwts
	              .parserBuilder()
	              .setSigningKey(key)
	              .build()
	              .parseClaimsJws(token)
	              .getBody();

	      Collection<? extends GrantedAuthority> authorities =
	         Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
	            .map(SimpleGrantedAuthority::new)
	            .collect(Collectors.toList());
	      
	      //유저 객체 생성
	      User principal = new User(claims.getSubject(), "", authorities);

	      return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	   }
	  //토큰 유효성 검사 (파싱)
	   public boolean validateToken(String token) {
	      try {
	         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	         return true;
	      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
	         log.info("잘못된 JWT 서명입니다.");
	      } catch (ExpiredJwtException e) {
	         log.info("만료된 JWT 토큰입니다.");
	      } catch (UnsupportedJwtException e) {
	         log.info("지원되지 않는 JWT 토큰입니다.");
	      } catch (IllegalArgumentException e) {
	         log.info("JWT 토큰이 잘못되었습니다.");
	      }
	      return false;
	   }
}
