package org.csource.utils;


import java.io.File;

public class FdfsUtil {

	/** 获取文件名
	 * @param file
	 * @return
	 */
	public static String getFileName(String file){
		int index = file.lastIndexOf("/") + 1;
		if(index>-1){
			return file.substring(index);
		}else{
			return new File(file).getName();
		}
	}

	/** 从文件名获取扩展名
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName){
		if(CommonUtil.isEmpty(fileName)) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	/**
	 * 获取文件前缀
	 * @param fileName
	 * @return
	 */
	public static String getPrefix(String fileName){
		if(CommonUtil.isEmpty(fileName)) {
			return null;
		}
		return fileName.substring(0,fileName.lastIndexOf("."));
	}



}
