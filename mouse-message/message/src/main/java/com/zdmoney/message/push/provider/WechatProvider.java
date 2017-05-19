package com.zdmoney.message.push.provider;

import com.google.common.collect.Lists;
import com.zdmoney.message.api.common.dto.MessageResultDto;
import com.zdmoney.message.api.dto.push.MsgWechatPushDto;
import io.github.elkan1788.mpsdk4j.api.WechatAPI;
import io.github.elkan1788.mpsdk4j.vo.api.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 推送消息服务-微信
 * Created by gaojc on 2016/8/23.
 */
@Slf4j
@Service
public class WechatProvider implements InitializingBean {
    @Autowired
    private WechatAPI wechatAPI;

    @Value("${global.config.path}")
    private String globalConfigPath;

    private Properties prop = new Properties();

    /**
     * 发送微信消息
     * @param wechatPushDto
     * @return
     */
    public MessageResultDto sendWechatMsg(MsgWechatPushDto wechatPushDto) {
        String tmlId = prop.getProperty(wechatPushDto.getTmlShortId() + ".tplId");
        if (tmlId == null) {
            return MessageResultDto.FAIL("推送失败，没有找到对应的模板！");
        }
        List<Template> templates = Lists.newArrayList();
        templates.add(new Template("first", prop.getProperty(wechatPushDto.getTmlShortId() + ".first")));
        templates.add(new Template("remark", prop.getProperty(wechatPushDto.getTmlShortId() + ".remark")));

        for (Map.Entry<String, String> entry : wechatPushDto.getParams().entrySet()) {
            templates.add(new Template(entry.getKey(), entry.getValue()));
        }
        wechatAPI.sendTemplateMsg(wechatPushDto.getOpenId(), tmlId, "", wechatPushDto.getUrl(), templates.toArray(new Template[templates.size()]));
        return MessageResultDto.SUCCESS();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            prop.load(new FileInputStream(globalConfigPath + File.separator + "message.properties"));
            log.info("mp_msg properties Path = " + globalConfigPath + File.separator + "message.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
