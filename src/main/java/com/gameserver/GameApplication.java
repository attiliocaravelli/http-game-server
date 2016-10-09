/*
 * © 2014 Attilio Caravelli
 *
 * Java Back End Developer Test
 *
 */

package com.gameserver;

import java.io.IOException;

/**
 *
 * @author Attilio Caravelli
 */
public class GameApplication {
  public static void main(String[] args) throws IOException {
    new GameServer().start();
  }
}
