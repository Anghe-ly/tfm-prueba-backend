package com.example.demo.servicio;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServicio {
	//clave secreta hecha en base64
	@Value("${security.jwt.secret-base64}")
	private String claveSecreta;
	
	//tiempo de expiracion fijado en 1H
	@Value("${security.jwt.expiration-time}")
	private long jwtExpiracion;

	
	
	//metodo para firmar y verificar JWT con clave HMAC
	private Key obtenerKey() {
		byte[] claveBytes = Decoders.BASE64.decode(claveSecreta);
		return Keys.hmacShaKeyFor(claveBytes);
	}
	
	
	//metodo que devuelve la duracion del token
	public long obtenerExpiracion() {
		return jwtExpiracion;
	}
	
	

	/*
	 * METODOS DE GENERAR TOKENS
	 * */
	//genera un token con solo username
	public String generarTokenConUser(String user) {
		return Jwts.builder()
				.setSubject(user)
				.signWith(obtenerKey())
				.compact();
	} 
	
	//genera un token con varios Claims
	public String generarTokenVarios(Map<String, Object> extraClaims, String username) {
		return Jwts.builder()
				.setClaims(extraClaims != null ? extraClaims : new HashMap<>())
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiracion))
				.signWith(obtenerKey(), SignatureAlgorithm.HS256) 
				.compact();
	}
	public String generarTokenConIdYRol(long idUsuario, String username, String rol) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("idUsuario", idUsuario);
		claims.put("rol", rol);
		
		return generarTokenVarios(claims, username);
	} 
	
	
	
	/*
	 * METODOS DE EXTRACCION DE DATOS 
	 * */
	
	//metodo que extrae el username del token
	public String extractUsername(String token){
		return extractClaim(token, Claims::getSubject);
	}
	
	//metodo que extrae la expiracion del token
	public Date extractExpiration(String token){
		return extractClaim(token, Claims::getExpiration);
	}
	
	//metodo que obtiene todos los claims del token
	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(obtenerKey())
					.build()
					.parseClaimsJws(token)
					.getBody();		
		} catch (JwtException | IllegalArgumentException e) {
			System.out.println("Error en el JwtServicio Extract All Claims");
			return null;
		}
	}

	
	//metodo que extrae un claim usando una funcion
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		
		if(claims == null) {
			return null;
		}
		try {
			return claimsResolver.apply(claims);
		} catch (Exception e) {

			System.out.println( "Error en JwtServicio en Extract Claims");
			return null;
			
		}
		
		
	
	}
		
	
	/*
	 * METODOS DE VALIDACION DE TOKENS
	 * */
	
	private boolean isTokenExpired(String token) {
		
		Date expiration = extractExpiration(token);
		if (expiration == null) {
			return true;
			
		}else {
			return extractExpiration(token).before(new Date());
		}
	} 
	
	public boolean isTokenValid(String token, String username) {
		
		if (token == null || token.trim().isEmpty()) {
			return false;
		}
		
		try {
			final String usernameExtraido = extractUsername(token);
			return (usernameExtraido.equals(username) && !isTokenExpired(token));
		} catch (Exception e) {
			System.out.println("ERROR DESDE EL JwtServicio al validar el token");
			return false;
		}
		
	}
}
