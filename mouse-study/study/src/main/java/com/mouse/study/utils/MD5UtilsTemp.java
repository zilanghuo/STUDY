package com.mouse.study.utils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by lwf on 2016/11/29.
 */
public class MD5UtilsTemp {

    private static final String openReqKey = "c33367701511b4f6020ec61ded352059";

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static void main(String[] args) throws Exception {
        TreeMap<String,String> treeMap = new TreeMap();
        treeMap.put("accountNo","01170609000004255");
        treeMap.put("merchantCode","888888");
        treeMap.put("key","c33367701511b4f6020ec61ded352059");
        System.out.println(getSign(treeMap));

    }

    /**
     * 生成签名
     * <p>
     * a.对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）后，
     * 使用 URL 键值对的格式（即 key1=value1&key2=value2…）拼接成字符串 string1,
     * 注意：值为空的参数不参不签名
     * b.在 string1 最后拼接上 key=KEY(密钥)得到 stringSignTemp
     * 字符串，并对stringSignTemp 进行 md5 运算，再将得到的字符串所有字符转换为大写，得到 sign值
     * </p>
     *
     * @param reqParams
     * @return
     */
    public static String getSign(TreeMap<String, String> reqParams) throws Exception {
        StringBuilder sbl = new StringBuilder();
        Iterator<String> itr = reqParams.keySet().iterator();
        String key = null;
        String value = null;
        while (itr.hasNext()) {
            key = itr.next();
            value = reqParams.get(key);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(value)
                    && !org.apache.commons.lang3.StringUtils.equals(key, "sign")
                    && !org.apache.commons.lang3.StringUtils.equals(key, "key")) {
                sbl.append(key).append("=").append(value).append("&");
            }
        }
        String stringSignTemp = sbl.append("key=").append(openReqKey).toString();
        System.out.println(stringSignTemp);
        return encode(stringSignTemp, "UTF-8").toUpperCase();
    }

    /**
     * 根据编码加密
     * @param origin
     * @param charsetName
     * @return
     */
    public static String encode(String origin, String charsetName) {
        if (origin != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.reset();
                if (org.apache.commons.lang3.StringUtils.isBlank(charsetName)) {
                    digest.update(origin.getBytes());
                } else {
                    digest.update(origin.getBytes(charsetName));
                }
                String resultString = toHex(digest.digest());
                return resultString.toLowerCase();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return origin;
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_DIGITS[(bytes[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }
}
