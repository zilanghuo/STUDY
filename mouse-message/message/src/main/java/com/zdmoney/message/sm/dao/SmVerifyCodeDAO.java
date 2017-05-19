package com.zdmoney.message.sm.dao;

import com.zdmoney.message.common.base.BaseDAO;
import com.zdmoney.message.sm.model.SmVerifyCode;
import com.zdmoney.message.sm.model.StatRecord;

import java.util.List;
import java.util.Map;

/**
 * 验证码 数据访问对象
 * Created by Administrator on 2016/11/3.
 */
public interface SmVerifyCodeDAO extends BaseDAO<SmVerifyCode> {

    List<StatRecord> statSmVerifyCode(Map<String, Object> map);

    Integer batchDelete(List<SmVerifyCode> smVerifyCodes);

    Integer updateVerifyCode(SmVerifyCode smVerifyCode);

}
