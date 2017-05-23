package com.mouse.trace.filter;

import com.mouse.trace.common.Constant;
import com.mouse.trace.utils.TraceGenerator;
import com.mouse.trace.common.Constant;
import com.mouse.trace.utils.TraceGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lwf on 2017/5/15.
 */
public class TraceLogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("进入 {} 过滤器",this.getClass().getName());
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        TraceGenerator.initTrackId(httpRequest.getHeader(Constant.TRACE_ID));
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        TraceGenerator.removeTraceId();
    }
}
