package com.showflix.app.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.showflix.app.constants.ApplicationConstants;
import com.showflix.app.controller.exception.UnauthorizedException;
import com.showflix.app.util.AuthenticationUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

@WebFilter({ "/users/*", "/shows/*", "/rating/*" })
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		try {

			if (request.getMethod().equalsIgnoreCase("options")) {
				response.addHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
				response.addHeader("Access-Control-Allow-Methods", "GET PUT POST");
				response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
				request.setAttribute("Options", "true");
			} else {
				if (!AuthenticationUtil.hasAuthenticationToken(request)) {
					throw new UnauthorizedException();
				}
				try {
					final Claims claims = AuthenticationUtil.parseToken(request, ApplicationConstants.secretKey);
					if (AuthenticationUtil.isExpired(claims)) {
						throw new UnauthorizedException();
					}

					request.setAttribute("role", claims.get("role"));
					request.setAttribute("user", claims.getSubject());
				} catch (final SignatureException e) {
					throw new UnauthorizedException();
				}
			}

			chain.doFilter(request, response);
		} catch (UnauthorizedException uae) {
			response.setStatus(401);
			System.out.println("Token is either invalid or expired");
		}

	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}