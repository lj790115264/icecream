package com.yl;

import com.yl.service.DevUrlTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author andre.lan
 */
public class DevUrlFilter extends OncePerRequestFilter {


    public static final String DEV_URL_TRACE_ID_HEADER = "x-dev-url-header";
    public static final String DEV_URL_TRACE_ID_CROSS_HEADER = "x-dev-url-cross-header";

    public static ThreadLocal<String> urlThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<Set<String>> tableThreadLocal = new ThreadLocal<>();
    public static ThreadLocal<List<String>> urlCrossThreadLocal = new ThreadLocal<>();

    @Autowired
    private DevUrlTableService service;

    public DevUrlFilter() {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getHeader(DEV_URL_TRACE_ID_HEADER);
        if (StringUtils.isEmpty(uri)) {
            uri = request.getRequestURI();
        }
        urlThreadLocal.set(uri);

        String uriCross = request.getHeader(DEV_URL_TRACE_ID_CROSS_HEADER);
        List<String> uriCrossList = new ArrayList<>();
        if (!StringUtils.isEmpty(uriCross)) {
            uriCrossList = JsonUtil.fromJson(uriCross, new JsonUtil.TypeReference<List<String>>() {});
        }
        uriCrossList.add(request.getRequestURI());
        urlCrossThreadLocal.set(uriCrossList);

        tableThreadLocal.set(new HashSet<>());
        try {
            filterChain.doFilter(request, response);
        } finally {

            service.doing();

            urlThreadLocal.remove();
            urlCrossThreadLocal.remove();
            tableThreadLocal.remove();
        }
    }
}
