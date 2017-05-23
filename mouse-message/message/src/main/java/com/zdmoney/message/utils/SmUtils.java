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
    @Value("${test.size}")
    private String testSize;

}
