/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.processors;

import com.gameserver.exceptions.MalformedRequestException;
import com.gameserver.exceptions.SessionExpiredException;
import com.sun.net.httpserver.HttpExchange;

/**
 * Processing the game requests
 * 
 */
public interface HttpGameRequest extends HttpRequest<Object>{
	 /**
   * Processor for the Login request
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   */
	public void processLoginRequest(HttpExchange httpExchange) throws MalformedRequestException;
  /**
   * Processor for User Score To A Level
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   * @throws com.gameserver.exceptions.SessionExpiredException
   */
	public void	processUserScoreToLevel(HttpExchange httpExchange) throws MalformedRequestException, SessionExpiredException;
	/**
   *  Processor for High Scores list request
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   */
	public void processHighScoresListRequest(HttpExchange httpExchange) throws MalformedRequestException;
}
