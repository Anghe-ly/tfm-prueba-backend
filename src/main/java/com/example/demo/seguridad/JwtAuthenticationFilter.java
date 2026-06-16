package com.example.demo.seguridad;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.servicio.JwtServicio;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtServicio jwtServicio;
	private final UserDetailsService userDetailsService;

	public JwtAuthenticationFilter(JwtServicio jwtServicio, UserDetailsService userDetailsService) {
		this.jwtServicio = jwtServicio;
		this.userDetailsService = userDetailsService;

	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String username; 
		
		
		  // Excluir explícitamente la ruta de login (por si acaso)
	    String path = request.getServletPath();
	    if ("/login/auth".equals(path) || path.startsWith("/public/")) {
	        filterChain.doFilter(request, response);
	        return;
	    }
	    
	    
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		jwt = authHeader.substring(7).trim(); //el .trim quita espacios en blanco 
		
		if(jwt.isEmpty()) {
	        filterChain.doFilter(request, response);
	        return;
		}
		
		try {
			username = jwtServicio.extractUsername(jwt);

		} catch (Exception e) {
		      // token malformado: solo log y continuar sin autenticar
	        System.out.println("JwtAuthenticationFilter: token inválido al extraer username -> " + e.getMessage());
	        filterChain.doFilter(request, response);
	        return;		}
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(username != null && auth == null) {
			try {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(jwtServicio.isTokenValid(jwt, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
						userDetails, 
						null,
						userDetails.getAuthorities()
						);
				
				authToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request)
						);
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			
				}
			}catch(Exception e){
				
	            System.out.println("JwtAuthenticationFilter: error validando token o cargando UserDetails -> " + e.getMessage());
	
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
