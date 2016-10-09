/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver.i18n;

import static org.junit.Assert.*;
import org.junit.Test;

import com.gameserver.i18n.Messages;
/**
 *
 * @author Attilio Caravelli
 */
public class MessagesTest {
  
  public MessagesTest() {
  }

  /**
   * Test of values method, of class Messages.
   */
  @Test
  public void testValues() {
    System.out.println("values");
    Messages[] expResult = {Messages.ALERT_DEBUG, Messages.ALERT_START_SERVER,
      Messages.ALERT_ON_PROCESSING_REQUEST, Messages.ALERT_CLEANING_SESSIONS,
      Messages.ALERT_ON_SENDING_DATA, Messages.ERROR_MESSAGE_MALFORMED_URL,
      Messages.ERROR_ON_SENDING_DATA, Messages.ERROR_SESSION_EXPIRED};
    Messages[] result = Messages.values();
    assertArrayEquals(expResult, result);
  }

  /**
   * Test of valueOf method, of class Messages.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValueOf_InexistentValue() {
    System.out.println("valueOf of a value inexistent");
    String name = "test";
    Messages.valueOf(name);
  }
  
  /**
   * Test of valueOf method, of class Messages.
   */
  @Test(expected = NullPointerException.class)
  public void testValueOf_NULL() {
    System.out.println("valueOf NULL value");
    String name = null;
    Messages.valueOf(name);
  }
  
  /**
   * Test of valueOf method, of class Messages.
   */
  @Test
  public void testValueOf() {
    System.out.println("valueOf");
    String name = "ALERT_DEBUG";
    Messages expResult = Messages.ALERT_DEBUG;
    Messages result = Messages.valueOf(name);
    assertEquals(expResult, result);
  }

  /**
   * Test of toString method, of class Messages.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    String expResult = "Debug: ";
    String result = Messages.ALERT_DEBUG.toString();
    assertEquals(expResult, result);
  }
}
