package org.fosstrak.ale.server.persistence;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * this class manage path to get webapp path
 * @author benoit.plomion@orange.com
 * @author swieland
 *
 */
public class Config {

	public static final String FILE_ENDING_XML = "xml";
	public static final String FILE_ENDING_LLRP = "llrp";
	public static final String FILE_ENDING_PROPERTES = "properties";
	
	/**	logger. */
	private static final Logger LOG = Logger.getLogger(Config.class.getName());
	
	/**
     *  default path, modified by servlet startup
     *  @see org.fosstrak.ale.server.persistence.PersistenceServlet 
     */
	private static String realPathWebapp = null;
	
	private static final String LR_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "lrspecs" + File.separator;
	private static final String EC_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "ecspecs" + File.separator;
	private static final String EC_SPEC_SUBSCRIBER_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "subscriber" + File.separator + "ecspec" + File.separator;
	private static final String RO_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "rospecs" + File.separator;
	private static final String ACCESS_SPECS_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "accessspecs" + File.separator;
	private static final String LLRP_WEBAPP_PATH = File.separator + "WEB-INF" + File.separator + "config" + File.separator + "llrp" + File.separator;
	
	/**
	 * this method is called at the startup of the application server by the PersistenceServlet and set the real path of the webapp
	 * @param realPathWebapp
	 */
	public static void setRealPathWebapp(String realPathWebapp) {
		Config.realPathWebapp = realPathWebapp;
		LOG.debug("set real path of webapp: " + realPathWebapp);
	}
	
	/**
	 * @return absolute path of the web applications deployment directory.
	 */
	public static String getRealPathWebapp() {
		return Config.realPathWebapp;		
	}
	
	/**
	 * @return absolute path to the LRSpecs persistence path.
	 */
	public static String getRealPathLRSpecDir() {
		return Config.realPathWebapp + Config.LR_SPECS_WEBAPP_PATH;
	}
	
	/**
	 * @return absolute path to the LRSpecs persistence path.
	 */
	public static String getRealPathECSpecDir() {
		return Config.realPathWebapp + Config.EC_SPECS_WEBAPP_PATH;
	}
	
	/**
	 * @return absolute path to the ECSpecSubscriber persistence path.
	 */
	public static String getRealPathECSpecSubscriberDir() {
		return Config.realPathWebapp + Config.EC_SPEC_SUBSCRIBER_WEBAPP_PATH;
	}

	/**
	 * @return absolute path to the RoSpec persistence path.
	 */
	public static String getRealPathROSpecDir() {
		return Config.realPathWebapp + Config.RO_SPECS_WEBAPP_PATH;
	}
	
	/**
	 * @return absolute path to the AccessSpec persistence path.
	 */
	public static String getRealPathAccessSpecDir() {
		return Config.realPathWebapp + Config.ACCESS_SPECS_WEBAPP_PATH;
	}
	
	/**
	 * @return absolute path to the LLRP messages persistence path.
	 */
	public static String getRealPathLLRPSpecDir() {
		return Config.realPathWebapp + Config.LLRP_WEBAPP_PATH;
	}
	
	/**
	 * check if a given file exists on the given path.
	 * @param fileName the filename to check.
	 * @param filePath the path where the file should be located.
	 * @return true if the file exists, false otherwise.
	 */
	public static boolean fileExist(String fileName, String filePath) {
		File f = new File(filePath + File.separator + fileName);
		if (f.exists()) {
			return true;
		}
		return false;		
	}
	
	/**
	 * get the filenames contained in the given directory.
	 * @param directoryPath the path of the directory.
	 * @param fileEnding the fileEnding of the files. if null, return all the files.
	 * @return a list of all contained filenames.
	 */
	public static ArrayList<String> getFilesName(String directoryPath, String fileEnding) {		
		String[] filesName;		
		filesName = new File(directoryPath).list();		
		ArrayList<String> filesNameList = new ArrayList<String>();		
				
		if (filesName != null) {		
			for (String fileName : filesName) {
				if (null == fileEnding) {
					filesNameList.add(fileName);
					LOG.debug("add file " + fileName + " to list to read");
				} else if (fileName.endsWith("." + fileEnding)) {
					filesNameList.add(fileName);	
					LOG.debug("add file " + fileName + " to list to read");					
				} else {
					LOG.debug("not adding file " + fileName + " to list to read");
				}		
			}
		}
		
		LOG.debug("list of file: " + filesNameList);
		return filesNameList;
	}
	
	/**
	 * delete a file from the given path.
	 * @param directoryPath the directory path.
	 * @param fileName the files name.
	 * @return whether the file was deleted or not.
	 */
	public static boolean removeFile(String directoryPath, String fileName) {
		return new File(directoryPath + File.separator + fileName).delete(); 
	}
	
}
