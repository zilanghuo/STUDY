package com.mouse.study.test.ftp;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by lwf on 2017/1/18.
 */
public class SFTPUtil {

    ChannelSftp sftp = null;
    /**
     * 连接服务器
     * @return
     */
    public  void connect(String host, int port, String username, String password, String remotePath) throws Exception {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        sftp = (ChannelSftp) channel;
        if (remotePath.length() != 0) {
            sftp.cd(remotePath);
        }
        System.out.println("Connected to " + host + ".");
    }
    /**
     * 上传文件
     * @param uploadFile 要上传的文件
     */
    public void upload(File uploadFile) throws Exception {
        sftp.put(new FileInputStream(uploadFile), uploadFile.getName());
    }
    /**
     * 下载文件
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String downloadFile, String saveFile) throws Exception {
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * 删除文件
     * @param deleteFile 要删除的文件
     */
    public void delete(String deleteFile) {
        try {
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }
    /**
     * 读取目标文件，返回stringBuffer格式
     * @param fileName
     * @return
     * @throws Exception
     */
    public InputStream getInput(String fileName) throws Exception {
        return sftp.get(fileName);
    }
    /**
     * 关闭sftp连接
     * @throws Exception
     */
    public void disconnect() throws Exception {
        if(!sftp.isClosed()){
            sftp.disconnect();
        }
    }
}
