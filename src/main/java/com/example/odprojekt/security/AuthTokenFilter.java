package com.example.odprojekt.security;

import com.example.odprojekt.entity.BlockedToken;
import com.example.odprojekt.repository.BlockedTokensRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final BlockedTokensRepository blockedTokensRepository;

    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService, BlockedTokensRepository blockedTokensRepository) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.blockedTokensRepository = blockedTokensRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/api") && !request.getRequestURI().startsWith("/api/auth")) {
            try {
                String jwt = parseJwt(request);
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    Date date = jwtUtils.getDateFromToken(jwt);
                    List<BlockedToken> tokens = blockedTokensRepository.findByUsernameAndDateAfter(username, date);

                    if (!tokens.isEmpty()) {
                        throw new RuntimeException("Token blocked");
                    }

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                System.err.println("Cannot authenticate token " + e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        String cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("authSignature"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ") && cookie != null) {
            String jwtClaims = headerAuth.substring(7);
            return jwtClaims.concat(cookie);
        }
        return null;
    }
}
