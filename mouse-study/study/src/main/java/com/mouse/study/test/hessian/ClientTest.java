package com.mouse.study.test.hessian;

import com.caucho.hessian.client.HessianProxyFactory;
import com.mouse.study.utils.JackJsonUtil;
import com.zdmoney.data.core.api.common.dto.PageResultDto;
import com.zdmoney.data.core.api.dto.dmp.DmpBusinessReportDto;
import com.zdmoney.data.core.api.dto.dmp.DmpBusinessReportPageReqDto;
import com.zdmoney.data.core.api.facade.IDataCoreDmpFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;

/**
 * Created by lwf on 2017/9/15.
 * use to do:hessian客户端
 */
public class ClientTest {

    public static String url = "http://172.17.34.103:8095/data-core/dataCoreDmpFacade";

    private static Logger log = LogManager.getLogger(ClientTest.class);

    public static void main(String[] args) {
        HessianProxyFactory factory = new HessianProxyFactory();
        try {
            IDataCoreDmpFacade dataCoreDmpFacade = (IDataCoreDmpFacade) factory.create(IDataCoreDmpFacade.class, url);
            DmpBusinessReportPageReqDto dto = new DmpBusinessReportPageReqDto();
            PageResultDto<DmpBusinessReportDto> resultDto = dataCoreDmpFacade.searchBusinessReport(dto);
            for (DmpBusinessReportDto reportDto : resultDto.getDataList()) {
                log.info("result:{}", JackJsonUtil.objToStr(reportDto));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testHessian() throws Exception {


    }

}
