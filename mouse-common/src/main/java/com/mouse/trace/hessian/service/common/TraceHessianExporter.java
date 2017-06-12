package com.mouse.trace.hessian.service.common;

import com.caucho.hessian.server.HessianSkeleton;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by rui on 15/8/22.1111
 */
@Slf4j
public class TraceHessianExporter extends HessianExporter  {


    private HessianSkeleton skeleton;

    @Override
    public void prepare() {
        log.info(this.getClass().getName()+" prepare");
        checkService();
        checkServiceInterface();
        this.skeleton = new TraceHessianSkeleton(getProxyForService(), getServiceInterface());
    }


    public void invoke(InputStream inputStream, OutputStream outputStream) throws Throwable {
        Assert.notNull(this.skeleton, "Hessian exporter has not been initialized");
        doInvoke(this.skeleton, inputStream, outputStream);
    }

}
