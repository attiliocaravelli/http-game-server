/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.processors;

/**
 * Http Game Validator interface
 */
public interface HttpGameRequestValidator {
	/**
   * Match the uri by login request with a pattern
   * in order to check the GET request in coming
   * @param requestUri
   * @return boolean
   */
	public boolean isLoginRequest(String requestUri);
 
  /**
   * Match the uri by highscore request with a pattern
   * in order to check the GET request in coming
   * @param requestUri
   * @return boolean
   */
	public boolean isHighScoreListRequest(String requestUri);
	  /**
   * Match the uri by score post request with a pattern
   * in order to check the POST request in coming
   * @param requestUri
   * @return boolean
   */
	public boolean isUserScoreToLevelRequest(String requestUri);
	/**
   * Extract the User Id from the request
   * @param requestUri
   * @return String null if it is not possible
   */
	public String  getUserIdFromRequest(String requestUri);
	/**
   * Extract the Level from the request
   * @param requestUri
   * @param isForHighScoreList GET request is equal True
   * @return String null if it is not possible
   */
	public String  getLevelIdFromRequest(String requestUri, boolean isForHighScoreList);
	  /**
   * Extract the Session Id from request
   * @param requestUri
   * @return String null if it is not possible
   */
	public String  getSessionIdFromRequest(String requestUri); 
}
