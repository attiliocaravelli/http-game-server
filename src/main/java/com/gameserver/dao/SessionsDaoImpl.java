/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.dao;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.gameserver.interfaces.ConfInterface;
import com.gameserver.interfaces.LogInterface;
import com.gameserver.logger.GameServerLogger;
import com.gameserver.utils.Configuration;
import com.gameserver.utils.TimeUtils;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 
 *
 *  The Data structure is a implementation of a concurrent Sorted Map
 *  sorted by Session Id and value the User Id.
 *  The null pointer check of the method parameters is made 
 *  before of the call method  throwing a SessionExpiredException
 *  In order to reuse or to distribute (TDD development) 
 *  this interface supplies the check of the parameters 
 *  of the methods
 *              
 */
public class SessionsDaoImpl
implements SessionsDao<Long, Integer>, LogInterface, ConfInterface
{
   /** Multi-thread sessions handler */
  private final ScheduledExecutorService  removerSessionsExpired_;
  /** Multi-thread daemon session handler */
  private final ScheduledFuture<?>        removerHandle_;
  /** A concurrent map to handle the valid sessions */
  private ConcurrentSkipListMap<Long, Integer> validSessions_;
  /** Time of session in nano seconds*/
  private final long timeOfSession_;
  
  /**
   * Constructor
   */
  public SessionsDaoImpl() {
     int timeOfSessionInMinutes = getTimeOfSession();
     timeOfSession_ =  TimeUtils.minutesToNanoSeconds(timeOfSessionInMinutes);
     validSessions_ = new ConcurrentSkipListMap<>();
     removerSessionsExpired_ = Executors.newScheduledThreadPool(1);
     final Runnable remover = new Runnable() {
      @Override
      public void run() {
        removeAllSessionsExpired();
      }
    };
    // scheduled for each minute after an initial delay equal to the time of session
    removerHandle_ =  removerSessionsExpired_.scheduleAtFixedRate(remover, timeOfSessionInMinutes ,
            1 , MINUTES);
  }
  
  /**
   *  Save a session
   * @param sessionKey
   * @param userId
   */
  @Override
  public void saveSession(Long sessionKey, Integer userId)
  { 
    boolean isNullSessionKey = (sessionKey == null);
    boolean isNullUser = (userId == null);
    
    if ( isNullSessionKey ||
            isNullUser) {
      debugging("saveSession NULL: " +
              isNullSessionKey + " " + isNullUser);
      throw new NullPointerException("sessionKey or userId is NULL");
    }
    validSessions_.putIfAbsent(sessionKey, userId);
  }
  
  /**
   *  Create a new session as the nanotime of the system
   *  @return Long
   */
  @Override
  public Long createSession()
  {
    return System.nanoTime();
  }
  
  /**
   *  Get an user by session or null if the key is null
   *  @return Integer 
   */
  @Override
  public Integer getUserIdBySessionId(Long sessionKey)
  {
    boolean isNullSessionKey = (sessionKey == null);
   
    if ( isNullSessionKey) {
      debugging("getUserIdBySessionId NULL: " +
              isNullSessionKey);
      throw new NullPointerException("sessionKey is NULL");
    }
    return validSessions_.get(sessionKey);
  }
  
  /**
   *  Get a session by user. If the userId is NULL throw NullPointerException
   *  @return Long
   */
  @Override
  public Long getSessionIdByUserId(Integer user) {
    if (user == null) throw new NullPointerException("userId is NULL");
    Set< Entry<Long, Integer> > sessions = validSessions_.entrySet();
    Long sessionNumber = null;
    for (Entry<Long, Integer> session : sessions) {
      Integer existentUser= session.getValue();
      boolean isUserFound = existentUser.equals(user);
      if (isUserFound) {
        debugging("IsUserFound user " + user + " Session " + session.getKey());
        sessionNumber = session.getKey();
        break;
      }
    }
    return sessionNumber;
  }
  
  /**
   *  Remove all sessions expired  
   */
  @Override
  public void removeAllSessionsExpired()
  {
    if (validSessions_.isEmpty()) {
      debugging("removeAllSessionsExpired sessions empty");
      return;
    }
    debugging("starting removeAllSessionsExpired TimeElapsed");
    Long nanoTimeElapsed    = getTimeElapsedOlderSession();
    while (nanoTimeElapsed > timeOfSession_ ) {
      Entry <Long, Integer > oldestSession = removeOldestSession();
      debugging("removeAllSessionsExpired SessionRemoved: " 
              + oldestSession.getValue());
      nanoTimeElapsed = getTimeElapsedOlderSession();
    }
  }
  
  /**
   * Remove the oldest session
   * Entry removed. If validSessions is empty return null
   */
  @Override
  public Entry<Long, Integer> removeOldestSession()
  {
    return validSessions_.pollFirstEntry();
  }
  
  /**
   *  Get the oldest session or null if the session doesn't exist
   *  The map is sorted in ascending sessionID order. The first key is the oldest.
   *  @return Long
   */     
  @Override
  public Long getOldestSession()
  {
    return validSessions_.firstKey();
  }
  
  /**
   * Get the time elapsed since the starting of the server 
   * @return
   */
  private Long getTimeElapsedOlderSession()
  {
    // first session key stored - oldest in time
    Long nanoTimeOldestSession = getOldestSession(); 
    Long nanoCurrentTime = TimeUtils.nanoCurrentTime();
    return nanoCurrentTime - nanoTimeOldestSession;
  }
  
  /**
   *   Stop services
   */
  public void stop()
  {
    removerSessionsExpired_.shutdownNow();
    removerHandle_.cancel(true);
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

  
  @Override
  public int getPort() {
   return Configuration.getPort();
  }

  @Override
  public int getTimeOfSession() {
   return Configuration.getTimeOfSession();
  }
}


