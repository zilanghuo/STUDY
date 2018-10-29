package com.mouse.study.test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author laiwufa
 * @date 2018/10/25
 * use:
 */
public class TestConsitentHash {

    public static int[] argumentIndex = new int[2];

    public static int replicaNumber = 160;

    public static String[] address = new String[]{"172.17.34.123", "172.17.34.124"};

    public static TreeMap<Long, String> virtualInvokers = new TreeMap<>();

    public static void main(String[] args) {
        init();
        String peole = "小红1";
        System.out.println(toKey(peole));
        System.out.println(md5(toKey(peole)));
        long parameterLong = hash(md5(toKey(peole)), 0);
        System.out.println(parameterLong);
        System.out.println(selectForKey(parameterLong));
    }

    /**
     * 根据hash，得到对应的服务器节点信息
     * 找不到对应的记录，返回顺时针最近的一个节点
     *
     * @param hash
     * @return
     */
    private static String selectForKey(long hash) {
        String invoker;
        Long key = hash;
        if (!virtualInvokers.containsKey(key)) {
            SortedMap<Long, String> tailMap = virtualInvokers.tailMap(key);
            if (tailMap.isEmpty()) {
                key = virtualInvokers.firstKey();
            } else {
                key = tailMap.firstKey();
            }
        }
        System.out.println(key);
        invoker = virtualInvokers.get(key);
        return invoker;
    }

    /**
     * 初始化节点
     * address：默认两个服务器
     * replicaNumber：虚拟节点，默认为160个，每个服务器扩展为160虚拟节点
     */
    public static void init() {
        for (String add : address) {
            for (int i = 0; i < replicaNumber / 4; i++) {
                byte[] digest = md5(add + i);
                for (int h = 0; h < 4; h++) {
                    long m = hash(digest, h);
                    virtualInvokers.put(m, add);
                }
            }
        }
        System.out.println(virtualInvokers);
    }

    /**
     * 获取对象的tostring对象值
     *
     * @param args
     * @return
     */
    private static String toKey(Object args) {
        StringBuilder buf = new StringBuilder();
        buf.append(args);
        return buf.toString();
    }

    /**
     * 根据string生成信息摘要
     *
     * @param value
     * @return
     */
    private static byte[] md5(String value) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        md5.reset();
        byte[] bytes;
        try {
            bytes = value.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        md5.update(bytes);
        return md5.digest();
    }

    /**
     * 调用hash(digest, 0)，将消息摘要转换为hashCode，这里仅取0-31位来生成HashCode
     *
     * @param digest
     * @param number
     * @return
     */
    private static long hash(byte[] digest, int number) {
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }


}
