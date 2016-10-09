/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.dao;

import java.util.Map.Entry;

/**
 *  Abstract interface to handle the sessions
 * @param <K> key type
 * @param <V> value type
 */
public interface SessionsDao<K, V>
{
  /** 
   *  Save a session
   * @param sessionKey
   * @param userId 
   */
  public void    saveSession(K sessionKey, V userId);
  /** 
   *  Create a new session
   * @return K  
   */
  public K       createSession();
  /** 
   *  Get user by session
   * @param sessionKey
   * @return V
   */
  public V       getUserIdBySessionId(K sessionKey);
  /** 
   *  Get session by user
   * @param userId
   * @return  K
   */
  public K       getSessionIdByUserId(V userId);
  /**
   *  Get the oldest session
   * @return K
   */
  public K       getOldestSession();
  /**
   *  Remove the oldest session
   * @return Entry
   */
  @SuppressWarnings("rawtypes")
  public Entry   removeOldestSession();
  /** 
   *  Remove all sessions expired 
   */
  public void    removeAllSessionsExpired();
}
