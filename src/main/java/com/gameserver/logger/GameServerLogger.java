/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.logger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.gameserver.annotations.CompanyInfo;
import com.gameserver.i18n.Messages;
import com.gameserver.utils.Configuration;

/**
 * Logger class
 * The Logger class is thread safe as default implementation
 * The simplest way to initialize the logger variable is in a static
 * initializer, which is guaranteed to only execute once at
 * class loading time
 *
 */
@CompanyInfo (
				developer = "Attilio Caravelli",
				company   = "© 2014 Attilio Caravelli",
				date      = "09/21/2014",
				currentRevision = "0.1"
)
public class GameServerLogger
{
	/** Logging */
	private static Logger LOGGER = null;
	private static final boolean DEBUG = true;
	
	private GameServerLogger() {}
	/** Not necessary a synchronized initialization */
	static {
		initialize();
	}
	
	/**
	 * Debugging
	 * @param debugMessage
	 */
	public static void debugging(String debugMessage)
	{
		if (DEBUG) {
			String messageDebug = Messages.ALERT_DEBUG.toString();
			LOGGER.log(Level.WARNING, "{0} {1}", new Object[]{messageDebug, debugMessage});
		}
	}
	
	/**
	 * Log an error on sending data by HTTP protocol
	 * @param data
	 *
	 */
	public static void errorOnSendingData(String data)
	{
		String messageError = Messages.ALERT_ON_SENDING_DATA.toString();
		LOGGER.log(Level.SEVERE, "{0} {1}", new Object[]{messageError, data});
	}
	
	/**
	 * Logging the malformed url error
	 */
	public static void errorMalformedUrl()
	{
		String messageError = Messages.ERROR_MESSAGE_MALFORMED_URL.toString();
		LOGGER.log(Level.SEVERE, "{0}", new Object[]{messageError});
	}
	
	/**
	 * Logging the session expired error
	 */
	public static void errorSessionExpired()
	{
		String messageError = Messages.ERROR_SESSION_EXPIRED.toString();
		LOGGER.log(Level.SEVERE, "{0}", new Object[]{messageError});
	}
	
	/**
	 *  Initialize the logger with a Default File Name
	 */
	private static void initialize() {
		try {
			LOGGER = Logger.getLogger("Server Log");
			FileHandler fileHandler = new FileHandler(getDefaultNameLogFile());
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
			LOGGER.addHandler(fileHandler);
			loggingInfoOfStartServer();
		}
		catch (SecurityException | IOException exception) {
			System.out.println(exception.getMessage());
		}
	}
	
	/**
	 *  Default Name of the log file
	 * @return
	 */
	private static String getDefaultNameLogFile()
	{
		return "Server.log";
	}
	
	private static void loggingInfoOfStartServer()
	{
		if (GameServerLogger.class.isAnnotationPresent(CompanyInfo.class)) {
			Annotation annotation = GameServerLogger.class.getAnnotation(CompanyInfo.class);
			CompanyInfo companyInfo = (CompanyInfo) annotation;
			
			LOGGER.log(Level.INFO, "Developer: {0}", companyInfo.developer());
			LOGGER.log(Level.INFO, "Company: {0}", companyInfo.company());
			LOGGER.log(Level.INFO, "Date: {0}", companyInfo.date());
			LOGGER.log(Level.INFO, "Current Revision: {0}", companyInfo.currentRevision());
		}
		
		String alertMessage = Messages.ALERT_START_SERVER.toString();
		int port = Configuration.getPort();
		LOGGER.log(Level.INFO, "{0} {1}", new Object[]{alertMessage, port});
	}
	
	
}
