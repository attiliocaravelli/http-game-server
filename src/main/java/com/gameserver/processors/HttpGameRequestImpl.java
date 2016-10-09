/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.processors;

import com.gameserver.dao.GameDaoImpl;
import com.gameserver.dao.SessionsDaoImpl;
import com.gameserver.exceptions.MalformedRequestException;
import com.gameserver.exceptions.SessionExpiredException;
import com.gameserver.i18n.Messages;
import com.gameserver.interfaces.LogInterface;
import com.gameserver.logger.GameServerLogger;
import com.gameserver.utils.HttpCodeUtils;
import com.gameserver.utils.RequestGameType;
import com.gameserver.utils.RequestMethodType;
import com.gameserver.utils.StringUtils;
import com.gameserver.utils.ThreadingUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Server requests handler
 *  Implemented with a Executor Service to manage the requests and to send the responses
 *  Implemented with a Scheduled Executor Service to handle the sessions
 *
 *  Interface Implemented: HttpHandler as custom handling of the requests
 *  Exception throws: MalformedRequestException, SessionExpiredException
 */
public class HttpGameRequestImpl
implements HttpHandler, LogInterface, HttpGameRequest, HttpGameRequestValidator
{
  /** Multi-thread game handler */
  private final ExecutorService           executorService_;
  /** Game Database */
  private final GameDaoImpl               database_;
  /** Session Database */
  private final SessionsDaoImpl           sessions_;
  
  /** Pattern for Request with Method HTTP GET: login */
  private final Pattern patternLogin_              = Pattern.compile("/(\\d*)/login");
  /** Pattern for Request with Method HTTP GET: highscoreList */
  private final Pattern patternHighScoreForLevel_  = Pattern.compile("/(\\d*)/highscorelist");
  /** Pattern for Request with Method HTTP POST: store the score */
  private final Pattern patternPlayerScoreToLevel_ = Pattern.compile("/(\\d*)/score\\?sessionkey=(.*)");
  
  /**
   *  Constructor
   */
  public HttpGameRequestImpl() {
    int maxThreads = ThreadingUtils.estimateMaxThread();
    database_               = new GameDaoImpl();
    sessions_               = new SessionsDaoImpl();
    executorService_        = new ThreadPoolExecutor(
            maxThreads, maxThreads, 1, TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(maxThreads, true),
            new ThreadPoolExecutor.CallerRunsPolicy());
  }
  
  /**
   * Multithread handling of the requests
   * @param httpExchange
   * @throws java.io.IOException
   */
  @Override
  public void handle(final HttpExchange httpExchange)
          throws IOException
  {
    executorService_.submit(new Runnable() {
      @Override
      public void run() {
        processRequest(httpExchange);
      }
    });
  }
  
  /**
   *  Shutdown all multi thread framework services
   */
  public void stop()
  {
    executorService_.shutdownNow();
    sessions_.stop();
  }
  
  @Override
  public void errorOnSendingData(String message)
  {
    GameServerLogger.errorOnSendingData(message);
  }
  
  @Override
  public void errorMalformedUrl()
  {
    GameServerLogger.errorMalformedUrl();
  }
  
  @Override
  public void debugging(String message)
  {
    GameServerLogger.debugging(message);
  }
  
  @Override
  public void errorSessionExpired()
  {
    GameServerLogger.errorSessionExpired();
  }
  
	/**
   * Processor of an incoming request
   * @param httpExchange
   * @return RequestMethodType 
   */
  public RequestMethodType processRequest(HttpExchange httpExchange)
  {
    String requestMethod = httpExchange.getRequestMethod();
    switch (requestMethod) {
      case "GET":
        doGet(httpExchange);
        return RequestMethodType.GET;
      case "POST":
        doPost(httpExchange);
        return RequestMethodType.POST;
      default:
        errorMalformedUrl();
        sendResponse(httpExchange, Messages.ERROR_MESSAGE_MALFORMED_URL.toString());
        return null;
    }
  }
  
  /**
   * Process for the GET requests
   * @param httpExchange
   * @return RequestMethodType
   */
	@Override
  public RequestGameType doGet(HttpExchange httpExchange) {
    String requestUri = httpExchange.getRequestURI().toASCIIString();
    debugging("doGet: " + requestUri);
    try {
      if (isLoginRequest(requestUri)) {
        processLoginRequest(httpExchange);
        return RequestGameType.LOGIN;
      }
      else if (isHighScoreListRequest(requestUri)) {
        processHighScoresListRequest(httpExchange);
        return RequestGameType.HIGHSCORE_LIST;
      }
      throw new MalformedRequestException();
    }
    catch (MalformedRequestException e) {
      errorMalformedUrl();
      sendResponse(httpExchange, Messages.ERROR_MESSAGE_MALFORMED_URL.toString());
      return null;
    }
  }
  
  /**
   * Process for the POST requests
   * @param httpExchange
	 * @return RequestMethodType
   */
	@Override
  public RequestGameType doPost(HttpExchange httpExchange) {
    String requestUri = httpExchange.getRequestURI().toASCIIString();
    debugging("doGet: " + requestUri);
    try {
      if (isUserScoreToLevelRequest(requestUri)) {
        processUserScoreToLevel(httpExchange);
        return RequestGameType.SCORE_POST;
      }
      throw new MalformedRequestException();
    }
    catch (MalformedRequestException e) {
      errorMalformedUrl();
      sendResponse(httpExchange, Messages.ERROR_MESSAGE_MALFORMED_URL.toString());
      return null;
    }
    catch (SessionExpiredException e) {
      errorSessionExpired();
      sendResponse(httpExchange, Messages.ERROR_MESSAGE_MALFORMED_URL.toString());
      return null;
    }
  }
  
	/**
   * Extract the data from the requestBody
   * @param requestBody
   * @throws NullPointerException
   * @return String
   */
	@Override
  public String extractDataFromPostRequest(InputStream requestBody)
          throws NullPointerException
  {
    boolean isNullrequestBody = (requestBody == null);
    if ( isNullrequestBody ) {
      throw new NullPointerException();
    }
    
    try (BufferedReader buffer = new BufferedReader(new InputStreamReader(requestBody, "utf-8"))) {
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = buffer.readLine()) != null)
        stringBuilder.append(line);
      return stringBuilder.toString();
    }
    catch (IOException ex ) {
      debugging("ExtractScore exception " + ex.getMessage());
      return null;
    }
  }
  
  /**
   * Send a response to the client
   * @param httpExchange
   * @param responseContent
   * @throws NullPointerException
   */
	@Override
  public void sendResponse(HttpExchange httpExchange, String responseContent)
          throws NullPointerException
  {
    boolean isNullHttpExchange = (httpExchange == null);
    boolean isNullResponseContent = (responseContent == null);
    if ( isNullHttpExchange || isNullResponseContent ) {
      throw new NullPointerException();
    }
    
    try (OutputStream os = httpExchange.getResponseBody()) {
      int codeResponse = HttpCodeUtils.codeOfResponse();
      httpExchange.sendResponseHeaders(codeResponse, responseContent.length());
      os.write(responseContent.getBytes());
    }
    catch (IOException e) {
      errorOnSendingData("ResponseContent: " + responseContent +
              " exception: " + e.getMessage());
    }
  }
  
  /**
   * Processor for the Login request
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   */
	@Override
  public void processLoginRequest(HttpExchange httpExchange)
          throws MalformedRequestException
  {
    String requestUri = httpExchange.getRequestURI().toASCIIString();
    Integer userId = StringUtils.stringToInteger(getUserIdFromRequest(requestUri));
    if ( userId == null ) {
      debugging("UserLoginRequest: " + userId);
      throw new MalformedRequestException();
    }
    Long sessionKey = sessions_.getSessionIdByUserId(userId);
    if (sessionKey == null) {
      sessionKey = sessions_.createSession();
      sessions_.saveSession(sessionKey, userId);
    }
    sendResponse(httpExchange, sessionKey.toString());
  }
  
  /**
   * Processor for User Score To A Level
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   * @throws com.gameserver.exceptions.SessionExpiredException
   */
	@Override
  public void processUserScoreToLevel(HttpExchange httpExchange)
          throws MalformedRequestException, SessionExpiredException
  {
    String requestUri = httpExchange.getRequestURI().toASCIIString();
    
    Integer levelId    = StringUtils.stringToInteger(getLevelIdFromRequest(requestUri, false));
    Long    sessionKey = StringUtils.stringToLong(getSessionIdFromRequest(requestUri));
    Integer score      = StringUtils.stringToInteger(getScoreFromRequest(httpExchange));
    if (levelId    == null ||
            sessionKey == null ||
            score      == null) {
      debugging("ScoreToLevel: Level " + levelId + " session: "
              + sessionKey + " score: " + score);
      throw new MalformedRequestException();
    }
    Integer userId = sessions_.getUserIdBySessionId(sessionKey);
    if (userId == null) throw new SessionExpiredException();
    boolean isValidSession = isSessionGreaterThanZero(sessionKey);
    if (!isValidSession) throw new MalformedRequestException();
    database_.updateLevel(levelId, score, userId);
    
    String messageEmpty = "";
    sendResponse(httpExchange, messageEmpty);
  }
  
  /**
   *  Processor for High Scores list request
   * @param httpExchange
   * @throws com.gameserver.exceptions.MalformedRequestException
   */
	@Override
  public void processHighScoresListRequest(HttpExchange httpExchange)
          throws MalformedRequestException
  {
    String requestUri = httpExchange.getRequestURI().toASCIIString();
    Integer levelId   = StringUtils.stringToInteger(getLevelIdFromRequest(requestUri, true));
    if (levelId    == null)
    {
      debugging("HighScoresList: Level " + levelId);
      throw new MalformedRequestException();
    }
    String highScoresList = database_.getHighScoresList(levelId);
    sendResponse(httpExchange, highScoresList);
  }
  
  /**
   * Match the uri by login request with a pattern
   * in order to check the GET request in coming
   * @param requestUri
   * @return boolean
   */
	@Override
  public boolean isLoginRequest(String requestUri) {
    return patternLogin_.matcher(requestUri).matches();
  }
  
  /**
   * Match the uri by highscore request with a pattern
   * in order to check the GET request in coming
   * @param requestUri
   * @return boolean
   */
	@Override
  public boolean isHighScoreListRequest(String requestUri) {
    return patternHighScoreForLevel_.matcher(requestUri).matches();
  }
  
  /**
   * Match the uri by score post request with a pattern
   * in order to check the POST request in coming
   * @param requestUri
   * @return boolean
   */
	@Override
  public boolean isUserScoreToLevelRequest(String requestUri) {
    return patternPlayerScoreToLevel_.matcher(requestUri).matches();
  }
  
  /**
   * Extract the User Id from the request
   * @param requestUri
   * @return String null if it is not possible
   */
	@Override
  public String getUserIdFromRequest(String requestUri) {
    Matcher matcher = patternLogin_.matcher(requestUri);
    if (matcher.matches()) return matcher.group(1);
    return null;
  }
  
  /**
   * Extract the Level from the request
   * @param requestUri
   * @param isForHighScoreList GET request is equal True
   * @return String null if it is not possible
   */
	@Override
  public String getLevelIdFromRequest(String requestUri, boolean isForHighScoreList) {
    Matcher matcher = isForHighScoreList ? patternHighScoreForLevel_.matcher(requestUri) :
            patternPlayerScoreToLevel_.matcher(requestUri);
    if (matcher.matches()) return matcher.group(1);
    return null;
  }
   /**
   * Extract the Session Id from request
   * @param requestUri
   * @return String null if it is not possible
   */
	@Override
  public String getSessionIdFromRequest(String requestUri) {
    Matcher matcher = patternPlayerScoreToLevel_.matcher(requestUri);
    if (matcher.matches()) return matcher.group(2);
    return null;
  }
	
	/**
   * Get the score from the body of the POST request
   * @param httpExchange
   * @throws NullPointerException
   * @return String = null if the body is malformed
   */
  protected String getScoreFromRequest(HttpExchange httpExchange)
          throws NullPointerException
  {
    boolean isNullHttpExchange = (httpExchange == null);
    if ( isNullHttpExchange ) {
      throw new NullPointerException();
    }
    
    InputStream requestBody = httpExchange.getRequestBody();
    String scores = extractDataFromPostRequest(requestBody);
    return scores;
  }
	
	/**
   * Check a session key is greater than zero
   * @param sessionKey
   * @return boolean True if session is greater than zero
   */
  private boolean isSessionGreaterThanZero(Long sessionKey) {
    if (sessionKey == null) return false;
    return sessionKey > 0;
  }
}
