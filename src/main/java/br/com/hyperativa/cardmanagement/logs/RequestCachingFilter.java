package br.com.hyperativa.cardmanagement.logs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "requestLoggingFilter")
public class RequestCachingFilter extends OncePerRequestFilter {
	private final static Logger LOGGER = LoggerFactory.getLogger(CachedServletInputStream.class);

    private static final String REQUEST_DATA_PREFIX = "REQUEST DATA: ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request instanceof HttpServletRequestWrapper) {
            filterChain.doFilter(request, response);
            return;
        }

        CachedHttpServletRequest cachedRequest = new CachedHttpServletRequest(request);
        filterChain.doFilter(cachedRequest, response);

        String payload = new String(cachedRequest.getCachedPayload());
        LOGGER.debug(REQUEST_DATA_PREFIX + payload);
    }
}
