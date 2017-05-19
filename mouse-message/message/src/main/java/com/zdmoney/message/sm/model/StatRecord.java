package com.zdmoney.message.sm.model;

import com.zdmoney.zdqd.util.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/29.
 */
@Data
@NoArgsConstructor
public class StatRecord implements Serializable {
    private String smChannelType;
    private String sendStatus;
    private int sendNum;

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof StatRecord) {
            StatRecord other = (StatRecord) obj;
            return StringUtils.equals(other.getSmChannelType(), ((StatRecord) obj).getSmChannelType()) &&
                    StringUtils.equals(other.getSendStatus(), ((StatRecord) obj).getSendStatus());
        }
        return false;
    }

    public int hashCode() {
        return smChannelType.hashCode() * sendStatus.hashCode() * 20;
    }

}
