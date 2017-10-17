package com.mouse.study.utils;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * Created by gaojc on 2016/8/19.
 */
@Slf4j
public class CommonUtils {

    /**
     * 集合拆分
     *
     * @param groupSize
     * @param list
     * @param groupNo
     * @param modNum
     * @param times
     * @return
     */
    public static List devide(int groupSize, List list, int groupNo, int modNum, int times) {
        int startIndex = groupNo * groupSize;
        int toIndex = (groupNo == times - 1 ?
                (modNum > 0 ? (modNum + groupSize * groupNo) : groupSize * (groupNo + 1)) :
                groupSize * (groupNo + 1));
        return list.subList(startIndex, toIndex);
    }

    /**
     * 不为空
     *
     * @param list
     * @return
     */
    public static Boolean isNotEmpty(List list) {
        if (list == null || list.size() == 0){
            return false;
        }
        return true;
    }

    /***
     * 将对象序列化为JSON文本
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    /***
     * 将JSON对象转换为传入类型的对象
     *
     * @param
     * @param jsonString
     * @param beanClass
     * @return
     */
    public static <T> T toBean(String jsonString, Class<T> beanClass) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(jsonString);
            return (T) JSONObject.toBean(jsonObject, beanClass);
        } catch (Exception e) {
            log.error("CommonUtils.toBean exception ", e);
        }
        return null;
    }

    public static String processRegexText(String text, String regex, String replaceText) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(text);

        boolean result = m.find();
        boolean deletedIllegalChars = false;
        StringBuffer sb = new StringBuffer();
        while (result) {
            //如果找到了非法字符那么就设下标记
            deletedIllegalChars = true;
            //如果里面包含非法字符，把他们消去，加到SB里面
            m.appendReplacement(sb, replaceText);
            result = m.find();
        }
        //匹配的结尾追加到sb中
        m.appendTail(sb);
        if (deletedIllegalChars) {
            text = sb.toString();
        }
        return text;
    }
}
