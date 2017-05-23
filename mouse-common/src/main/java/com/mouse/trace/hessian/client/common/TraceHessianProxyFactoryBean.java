package com.mouse.trace.hessian.client.common;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Created by rui on 17/05/09.
 */
public class TraceHessianProxyFactoryBean extends HessianProxyFactoryBean {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }
}
