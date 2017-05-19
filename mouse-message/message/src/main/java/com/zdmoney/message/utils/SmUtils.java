package com.zdmoney.message.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/11/3.
 */
@Data
@Component
public class SmUtils {
    @Value("${sm.test.switch}")
    private String smTestswitch;

    @Value("${blue.msg.account}")
    private String blueMsgName;

    @Value("${blue.msg.password}")
    private String blueMsgPassword;

    @Value("${blue.market.account}")
    private String blueMarketName;

    @Value("${blue.market.password}")
    private String blueMarketPassword;

    @Value("${blue.msg.url}")
    private String blueMsgUrl;

    @Value("${blue.market.url}")
    private String blueMarketUrl;

    @Value("${blue.pkg.url}")
    private String bluePkgUrl;

    @Value("${blue.balance.url}")
    private String blueBalanceUrl;

    @Value("${blue.max.push.number}")
    private Integer blueMaxPushNumber;

    @Value("${bst.sm.account}")
    private String bstMsgName;

    @Value("${bst.sm.password}")
    private String bstMsgPassword;

    @Value("${bst.market.account}")
    private String bstMarketName;

    @Value("${bst.market.password}")
    private String bstMarketPassword;

    @Value("${bst.msg.url}")
    private String bstMsgUrl;

    @Value("${bst.market.url}")
    private String bstMarketUrl;

    @Value("${bst.pkg.url}")
    private String BST_PKG_URL;

    @Value("${bst.balance.url}")
    private String bstBalanceUrl;

    @Value("${dh3t.msg.account}")
    private String dh3tMsgName;

    @Value("${dh3t.msg.password}")
    private String dh3tMsgPassword;

    @Value("${dh3t.market.account}")
    private String dh3tMarketName;

    @Value("${dh3t.market.password}")
    private String dh3tMarketPassword;

    @Value("${dh3t.msg.url}")
    private String dh3tMsgUrl;

    @Value("${dh3t.market.url}")
    private String dh3tMarketUrl;

    @Value("${bst.max.push.number}")
    private Integer bstMaxPushNumber;

    private String questionChar = "?";

    private String andChar = "&";

    @Value("${reset.main.channel.number}")
    private Integer resetMainChannelNumber;

    @Value("${reset.other.channel.number}")
    private Integer resetOtherChannelNumber;

    @Value("${bst.msg.scrphone}")
    private String bstMsgScrphone;

    @Value("${bst.market.scrphone}")
    private String bstMarketScrphone;


}
