package com.oyameen.apigateway.filter;

import com.oyameen.apigateway.exception.UnauthorizedAccessException;
import com.oyameen.apigateway.service.JWTService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = null;
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Authorization header not exist.");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    if (jwtService.validateToken(authHeader)) {
                        serverHttpRequest = populateRequestWithHeaders(exchange, authHeader);
                    }
                    else {
                        System.out.println("invalid/UnAuthorized access!");
                    }
                } catch (Exception e) {
                    throw new UnauthorizedAccessException("invalid/UnAuthorized access.");
                }
            }
            assert serverHttpRequest != null;
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        });
    }
    private ServerHttpRequest populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtService.extractAllClaims(token);
        return exchange.getRequest()
                .mutate()
                .header("userEmail",claims.getSubject())
                .header("roles", String.valueOf(claims.get("roles")))
                .build();
    }
}
