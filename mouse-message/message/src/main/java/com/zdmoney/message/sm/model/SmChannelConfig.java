package com.zdmoney.message.sm.model;

import com.zdmoney.message.api.dto.sm.SendSmType;
import com.zdmoney.message.api.dto.sm.SmChannelType;
import com.zdmoney.message.utils.SmConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public enum SmChannelConfig {
    BLUE_MSG(SmConfig.getSmUtils().getBlueMsgName(), SmConfig.getSmUtils().getBlueMsgPassword(), SmConfig.getSmUtils().getBlueMsgUrl()),
    BLUE_MARKET(SmConfig.getSmUtils().getBlueMarketName(), SmConfig.getSmUtils().getBlueMarketPassword(), SmConfig.getSmUtils().getBlueMarketUrl()),
    BST_MSG(SmConfig.getSmUtils().getBstMsgName(), SmConfig.getSmUtils().getBstMsgPassword(), SmConfig.getSmUtils().getBstMsgUrl()),
    BST_MARKET(SmConfig.getSmUtils().getBstMarketName(), SmConfig.getSmUtils().getBstMarketPassword(), SmConfig.getSmUtils().getBstMarketUrl()),
    DH3T_MSG(SmConfig.getSmUtils().getDh3tMsgName(), SmConfig.getSmUtils().getDh3tMsgPassword(), SmConfig.getSmUtils().getDh3tMsgUrl()),
    DH3T_MARKET(SmConfig.getSmUtils().getDh3tMarketName(), SmConfig.getSmUtils().getDh3tMarketPassword(), SmConfig.getSmUtils().getDh3tMarketUrl());

    private String userName;
    private String password;
    private String url;

    SmChannelConfig(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static List<SmChannelConfig> getSmChannelConfig(String channelType) {
        List<SmChannelConfig> channelConfigs = new ArrayList<SmChannelConfig>();

        if (channelType.equals("BST")) {
            channelConfigs.add(BST_MSG);
            channelConfigs.add(BST_MARKET);
        } else if (channelType.equals("BLUE")) {
            channelConfigs.add(BLUE_MARKET);
            channelConfigs.add(BLUE_MSG);
        } else if (channelType.equals("DH3T")) {
            channelConfigs.add(DH3T_MARKET);
            channelConfigs.add(DH3T_MSG);
        }
        return channelConfigs;
    }

    SmChannelConfig(String userName, String password, String url) {
        this(userName, password);
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public SendSmType getSendSmType() {
        if (this == SmChannelConfig.BLUE_MSG || this == SmChannelConfig.BST_MSG) {
            return SendSmType.NOTIFY;
        }
        if (this == SmChannelConfig.BLUE_MARKET || this == SmChannelConfig.BST_MARKET) {
            return SendSmType.MARKET;
        }
        return null;
    }

    public SmChannelType getSmChannelType() {
        if (SmChannelConfig.getSmChannelConfig("BST").contains(this)) {
            return SmChannelType.BST;
        } else if (SmChannelConfig.getSmChannelConfig("BLUE").contains(this)) {
            return SmChannelType.BLUE;
        } else if (SmChannelConfig.getSmChannelConfig("DH3T").contains(this)) {
            return SmChannelType.DH3T;
        }
        return null;
    }
}
