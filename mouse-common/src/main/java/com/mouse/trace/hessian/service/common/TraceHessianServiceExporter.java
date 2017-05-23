package com.mouse.trace.hessian.service.common;

import com.mouse.trace.common.Constant;
import com.mouse.trace.utils.TraceGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rui on 15/8/21.
 */
public class TraceHessianServiceExporter extends TraceHessianExporter implements HttpRequestHandler {


    protected final transient org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TraceGenerator.initTrackId(request.getHeader(Constant.TRACE_ID));
        if (!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(),
                    new String[]{"POST"}, "HessianServiceExporter only supports POST requests");
        }

        response.setContentType(CONTENT_TYPE_HESSIAN);
        try {
            invoke(request.getInputStream(), response.getOutputStream());
        } catch (Throwable ex) {
            throw new NestedServletException("Hessian skeleton invocation failed", ex);
        }
    }

}

