package org.fosstrak.ale.server.persistence;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * this class manage path to get webapp path
 * @author benoit.plomion@orange.com
 *
 */
public class Config {

	/**	logger. */
	private static final Logger LOG = Logger.getLogger(Config.class.getName());
	
	/**
     *  default path, modified by servlet startup
     *  @see org.fosstrak.ale.server.persistence.PersistenceServlet 
     */
	private static String realPathWebapp = "C:\\Program Files\\apache-tomcat-6.0.14\\webapps\\fc-server";
	
	private static final String LR_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "lrspecs" + File.separator;
	private static final String EC_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "ecspecs" + File.separator;
	private static final String EC_SPEC_SUBSCRIBER_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "subscriber" + File.separator + "ecspec" + File.separator;
	private static final String RO_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "rospecs" + File.separator;
	private static final String ACCESS_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "accessspecs" + File.separator;
	private static final String LLRP_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "llrp" + File.separator;
	
	/**
	 * this method is called at the startup tomcat by the PersistenceServlet and set the real path of the webapp
	 * @param realPathWebapp
	 */
	public static void setRealPathWebapp(String realPathWebapp) {
		Config.realPathWebapp = realPathWebapp;
		LOG.debug("set real path of webapp: " + realPathWebapp);
	}
	
	protected static String getRealPathWebapp() {
		return Config.realPathWebapp;		
	}
		
	protected static String  getRealPathLRSpecDir() {
		return Config.realPathWebapp + Config.LR_SPECS_WEBAPP_PATH;
	}
	
	protected static String  getRealPathECSpecDir() {
		return Config.realPathWebapp + Config.EC_SPECS_WEBAPP_PATH;
	}
	
	protected static String  getRealPathECSpecSubscriberDir() {
		return Config.realPathWebapp + Config.EC_SPEC_SUBSCRIBER_WEBAPP_PATH;
	}
		
	protected static String  getRealPathROSpecDir() {
		return Config.realPathWebapp + Config.RO_SPECS_WEBAPP_PATH;
	}
	
	protected static String  getRealPathAccessSpecDir() {
		return Config.realPathWebapp + Config.ACCESS_SPECS_WEBAPP_PATH;
	}
	
	public static String  getRealPathLLRPSpecDir() {
		return Config.realPathWebapp + Config.LLRP_WEBAPP_PATH;
	}
	
	protected static boolean fileExist(String fileName, String filePath) {
		
		boolean fileExist = false;
		
		String[] filesName;		
		filesName = new File(filePath).list();		
		int i;		
		
		if (filesName != null) {
			for(i=0; i<filesName.length; i++){				
				if (fileName.trim().equalsIgnoreCase(filesName[i].trim())) {
					fileExist = true;	
					break;
				}			
			}
		}
		
		return fileExist;
		
	}
	
	protected static ArrayList<String> getFilesName(String directoryPath) {
		
		String[] filesName;		
		filesName = new File(directoryPath).list();		
		int i;		
		
		ArrayList<String> filesNameList = new ArrayList<String>();		
				
		if (filesName != null) {
		
			for(i=0; i<filesName.length; i++){		
					
				String fileName = filesName[i];
				
				if (!fileName.contains(".m4")) {
					filesNameList.add(fileName);	
					LOG.debug("add file " + fileName + " to list to read");
				}
						
			}
		
		}
		
		LOG.info("list of file: " + filesNameList);
		
		return filesNameList;
			
	}
	
	protected static boolean removeFile(String directoryPath, String fileName) {
		
		boolean result = false;
		
		File[] files = new File(directoryPath).listFiles();		
		int i;		
			
		if (files != null) {
		
			for(i=0; i<files.length; i++){			
				
				File file = files[i];
				
				if (file.getName().equalsIgnoreCase(fileName)) {
					result = file.delete();
				}
									
				LOG.debug("remove file " + fileName + " = " + result + " on path = " + directoryPath);
				
			}
		
		}
		
		return result;
			
	}
	
}
