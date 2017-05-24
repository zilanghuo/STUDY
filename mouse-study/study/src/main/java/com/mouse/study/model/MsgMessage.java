package com.mouse.study.model;

import com.mouse.common.BaseEntity;
import lombok.Data;

/**
 * Created by lwf on 2017/5/23.
 */
@Data
public class MsgMessage extends BaseEntity {

    //商户流水号
    private String merchantSerialNo;
    //用户编号
    private String userId;
    //用户编号
    private String userNo;
    //用户姓名
    private String userName;
    //用户手机号
    private String userPhone;
    //标题
    private String title;
    //摘要
    private String summary;
    //内容
    private String content;

}
