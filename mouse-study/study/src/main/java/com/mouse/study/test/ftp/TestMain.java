package com.mouse.study.test.ftp;

/**
 * @author lwf
 * @date 2018/7/2
 * use:
 */
public class TestMain {

    public static void main(String[] args) throws Exception {
        String url = "172.17.34.165";
        String userName = "zd_credit";
        Integer port = 22;
        String user_psw = "zd_credit";
        String path = "fuyou";
        SFTPUtil sftpUtil = new SFTPUtil();
        sftpUtil.connect(url, port, userName, user_psw, path);
    }

}
