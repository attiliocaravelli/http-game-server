/*
* © 2014 Attilio Caravelli
*
* Java Back End Developer Test
*
*/

package com.gameserver;

import com.gameserver.interfaces.ConfInterface;
import com.gameserver.processors.HttpGameRequestImpl;
import com.gameserver.utils.Configuration;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Game server
 */
public class GameServer implements ConfInterface{
  /** Sun Http Server   */
  protected final  HttpServer           httpServer_;
  /**  Handler of the game server*/
  protected final  HttpGameRequestImpl playersRequestsHandler_;
  
  /**
   * default En Language 
   * @throws IOException
   */
  public GameServer() 
          throws IOException 
  {    
    int port = getPort();
    playersRequestsHandler_ = new HttpGameRequestImpl(); 
    httpServer_ = HttpServer.create(new InetSocketAddress(port), 0);
    httpServer_.createContext("/", playersRequestsHandler_);
  }
  
  /**
   * Start the Server and define a common handler for every request
   */
  public void start() {
    httpServer_.start();
  }
  
  /**
   * Stop the server even if there are threads running
   */
  public void stop() {
    playersRequestsHandler_.stop();
    httpServer_.stop(0);
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
