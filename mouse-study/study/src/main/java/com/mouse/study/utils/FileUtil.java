package com.mouse.study.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private final transient static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * 保本文件到本地
	 * @param storagePath
	 * @param in
	 */
	public static void saveFile(String storagePath, InputStream in) {
		try {
			FileUtils.copyInputStreamToFile(in, new File(storagePath));
		} catch (IOException e) {
			logger.error("save inputstream file exception: " + e.getMessage(),e);
		}
	}
	
	/**
	 * 保本文件到本地
	 * @param storagePath
	 * @param data
	 */
	public static void saveFile(String storagePath, byte[] data) {
		try {
			FileUtils.writeByteArrayToFile(new File(storagePath), data);
		} catch (IOException e) {
			logger.error("save byte[] file exception: " + e.getMessage(),e);
		}
	}
	
	/**
	 * 保存url文件到本地
	 * @param storagePath
	 * @param fileName
	 * @param url
	 */
	public static void saveUrlFile(String storagePath, String url) {
		try {
			FileUtils.copyURLToFile(new URL(url), new File(storagePath), 30000, 30000);
			//FileUtils.copyURLToFile(new URL(url), new File(storagePath));
		} catch (Exception e) {
			logger.error("save url file exception: " + e.getMessage(),e);
		}
	}
	
	/**
	 * 获取url bytes
	 * @param url
	 */
	public static byte[] getUrlFileBytes(String url) {
		byte[] data = null;
		InputStream in = null;
		try {
			URL urlCon = new URL(url);
			in = urlCon.openStream();
			data = IOUtils.toByteArray(in);
		} catch (Exception e) {
			logger.error("get url file byte exception: " + e.getMessage(),e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close url inputstream" + e.getMessage(),e);
				}
			}
		}
		return data;
	}

//	/**
//	 * 保存url文件到本地
//	 * @param storagePath
//	 * @param fileName
//	 * @param url
//	 */
//	public static void saveUrlFile(String storagePath, String url) {
//		URL urlCon = null;
//		InputStream in = null;
//		try {
//			urlCon = new URL(url);
//			in = urlCon.openStream();
//			byte[] data = IOUtils.toByteArray(in);
//			FileUtils.writeByteArrayToFile(new File(storagePath), data);
//		} catch (Exception e) {
//			logger.error("save local file exception: ", e);
//		} finally {
//			if (in != null) {
//				try {
//					in.close();
//				} catch (IOException e) {
//					logger.error("close url stream", e);
//				}
//			}
//			if (urlCon != null) {
//				urlCon = null;
//			}
//		}
//	}
}
