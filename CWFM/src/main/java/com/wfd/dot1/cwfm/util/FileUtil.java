package com.wfd.dot1.cwfm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileUtil {

	private static final Logger log = LoggerFactory.getLogger(FileUtil.class.getName());
	public static Properties getPoPropertiesFile(){
		ResourceBundle labels = ResourceBundle.getBundle("doc_path_config");   
		Properties prop = null;
		InputStream inputStream=null;
		try {
			prop=new Properties();  
			String fileLocation=MessageFormat.format(labels.getString("query.doc.path"),"default");   
			log.info("client wise po  properties file------------"+fileLocation);   
			inputStream = new FileInputStream(fileLocation);   
			prop.load(inputStream);
		} catch (FileNotFoundException e1) {
			log.info("FileNotFoundException--"+e1.getMessage());
		} catch (IOException e) {
			log.info("IOException--"+e.getMessage());
		}finally{
			try{
				if(inputStream !=null){
					inputStream.close();
				}
			}catch(Exception e){
				log.error(e.getMessage());
			}
		}
		return prop;
	}
}
