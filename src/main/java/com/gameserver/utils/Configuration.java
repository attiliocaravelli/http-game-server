/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.utils;


/**
 * Configuration of the server with default parameters
 */
public class Configuration {
	private static int port_ = 8080;
	private static int timeSession_ = 10;
	
	/**
	 *  Default Port of configuration (8080)
	 * @return
	 */
	public static int getPort() {
		return port_;
	}
	
	/**
	 *  Default time of session (10 minutes)
	 * @return
	 */
	public static int getTimeOfSession() {
		return timeSession_;
	}
	
	/**
	 *  Default Port of configuration (8080)
	 * @param port
	 */
	public static void setPort(int port) {
		port_=port;
	}
	
	/**
	 *  Default time of session (10 minutes)
	 * @param sessionTime
	 */
	public static void setTimeOfSession(int sessionTime) {
	 timeSession_=sessionTime;
	}
}
