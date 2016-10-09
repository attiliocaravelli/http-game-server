/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.processors;

import com.sun.net.httpserver.HttpExchange;
import java.io.InputStream;

/**
 *
 * Interface for a general HTTP request
 * @param <T> Return an implementation type useful for TDD development
 */
public interface HttpRequest<T> {
	 /**
   * Process for the GET requests
   * @param httpExchange
   * @return
   */
  public T doGet(HttpExchange httpExchange);
	/**
   * Process for the POST requests
   * @param httpExchange
	 * @return 
   */
  public T doPost(HttpExchange httpExchange);
		/**
   * Extract the data from the requestBody
   * @param requestBody
   * @throws NullPointerException
   * @return String
   */
  public String extractDataFromPostRequest(InputStream requestBody);
	 /**
   * Send a response to the client
   * @param httpExchange
   * @param responseContent
   * @throws NullPointerException
   */
	public void   sendResponse(HttpExchange httpExchange, String responseContent);
}
