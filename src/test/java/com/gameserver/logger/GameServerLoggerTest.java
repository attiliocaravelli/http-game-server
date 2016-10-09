/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver.logger;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import static org.junit.Assert.*;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import com.gameserver.i18n.Messages;
import com.gameserver.logger.GameServerLogger;
/**
 *
 * @author Attilio Caravelli
 */
public class GameServerLoggerTest {
  
  /** Private static field and methods of the class under tests*/
 
  private static final Method methodLoggingInfoOfStartServer_ = 
                    Whitebox.getMethod(GameServerLogger.class, "loggingInfoOfStartServer"); 
  private static final Field fieldLogger_ = Whitebox.getField(GameServerLogger.class, "LOGGER");
  
  
  
  public GameServerLoggerTest() {
  }
  
  /**
   * Test of logger instance is not null
   * @throws java.lang.Exception
   */
  @Test
  public void testInitializeLogger_NOT_NULL() throws Exception {
    System.out.println("testInitializeLogger_NOT_NULL");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class);
    assertNotNull(logger);
  }
  
  /**
   * Test of logger name is Server Log
   * @throws java.lang.Exception
   */
  @Test
  public void testInitialize_LoggerName() throws Exception {
    System.out.println("testInitialize_LoggerName");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class);
    String result = logger.getName();
    String expResult = "Server Log";
    assertEquals(expResult, result);  
  }
  
  /**
   * Test of logger name is Server Log
   * @throws java.lang.Exception
   */
  @Test
  public void testInitialize_FileHandler() throws Exception {
    System.out.println("testInitialize_FileHandler");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class);
    Handler[] handlers = logger.getHandlers();
    boolean isFileHandlerFound = false;
    for (Handler handler : handlers) {
      if (handler instanceof FileHandler) {
        isFileHandlerFound = true;
        break;
      }  
    }
    assertTrue(isFileHandlerFound);  
  }
  
  
    
  /**
   * Test of LoggingInfoOfStartServer method, of class GameServerLogger.
   * @throws java.lang.Exception
   */
  @Test
  public void testLoggingInfoOfStartServer() throws Exception {
    System.out.println("testLoggingInfoOfStartServer");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Handler handler = new StreamHandler(out, new SimpleFormatter());
    logger.addHandler(handler);
    try {
      methodLoggingInfoOfStartServer_.invoke(null);
      handler.flush();
      String logMsg = out.toString();
      assertNotNull(logMsg);
      assertTrue(logMsg.contains("Company: © 2014 Attilio Caravelli"));
    } 
    finally {
      logger.removeHandler(handler);
    }
  }
  
  /**
   * Test of debugging method, of class GameServerLogger.
   * @throws java.lang.Exception
   */
  @Test
  public void testDebugging() throws Exception {
    System.out.println("testDebugging");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Handler handler = new StreamHandler(out, new SimpleFormatter());
    logger.addHandler(handler);
    try {
      GameServerLogger.debugging("Test Debug Company: © 2014 Attilio Caravelli");
      handler.flush();
      String logMsg = out.toString();
      assertNotNull(logMsg);
      assertTrue(logMsg.contains("Test Debug Company: © 2014 Attilio Caravelli"));
    } 
    finally {
      logger.removeHandler(handler);
    }
  }
  
   /**
   * Test of error On Sending Data method, of class GameServerLogger.
   * @throws java.lang.Exception
   */
  @Test
  public void testErrorOnSendingData() throws Exception {
    System.out.println("testErrorOnSendingData");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Handler handler = new StreamHandler(out, new SimpleFormatter());
    logger.addHandler(handler);
    try {
      GameServerLogger.errorOnSendingData("Test Data Error Sending Company: © 2014 Attilio Caravelli");
      handler.flush();
      String logMsg = out.toString();
      assertNotNull(logMsg);
      assertTrue(logMsg.contains("Test Data Error Sending Company: © 2014 Attilio Caravelli"));
    } 
    finally {
      logger.removeHandler(handler);
    }
  }
  
    /**
   * Test of error Malformed Url method, of class GameServerLogger.
   * @throws java.lang.Exception
   */
  @Test
  public void testErrorMalformedUrl() throws Exception {
    System.out.println("testErrorMalformedUrl");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Handler handler = new StreamHandler(out, new SimpleFormatter());
    logger.addHandler(handler);
    try {
      GameServerLogger.errorMalformedUrl();
      handler.flush();
      String logMsg = out.toString();
      assertNotNull(logMsg);
      assertTrue(logMsg.contains(Messages.ERROR_MESSAGE_MALFORMED_URL.toString()));
    } 
    finally {
      logger.removeHandler(handler);
    }
  }
  
    /**
   * Test of error Session Expired method, of class GameServerLogger.
   * @throws java.lang.Exception
   */
  @Test
  public void testErrorSessionExpired() throws Exception {
    System.out.println("testErrorSessionExpired");
    Logger logger = (Logger)fieldLogger_.get(GameServerLogger.class); 
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Handler handler = new StreamHandler(out, new SimpleFormatter());
    logger.addHandler(handler);
    try {
      GameServerLogger.errorSessionExpired();
      handler.flush();
      String logMsg = out.toString();
      assertNotNull(logMsg);
      assertTrue(logMsg.contains(Messages.ERROR_SESSION_EXPIRED.toString()));
    } 
    finally {
      logger.removeHandler(handler);
    }
  }
}
