package com.zdmoney.message.api.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwf on 2016/8/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsgMessageReadDto implements Serializable {

    private static final long serialVersionUID = -8407932391955035327L;
    private List<Integer> ids = new ArrayList();

    private Boolean isAllRead = false;

    private String userNo;

    private String userId;

    private MsgMessageType messageType;

    @NotNull(message = "回调地址不能为空")
    private String callbackUrl;
}
