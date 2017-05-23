package com.mouse.trace.hessian.client.common;


import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.io.HessianRemoteObject;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Proxy;
import java.net.URL;

/**
 * Created by rui on 17/05/09.
 */
public class TraceHessianProxyFactory extends HessianProxyFactory {

    @Setter
    @Getter
    private String channel;

    @Override
    public Object create(Class api, URL url, ClassLoader loader) {

        if (channel == null) {
            throw new NullPointerException("channel must be config for TranceHessianProxyFactory.create()");
        }
        if (api == null){
            throw new NullPointerException("api must not be null for TranceHessianProxyFactory.create()");
        }
        TraceHessianProxy handler = new TraceHessianProxy(url, this, api,channel);
        return Proxy.newProxyInstance(loader, new Class[]{api, HessianRemoteObject.class}, handler);
    }

}
