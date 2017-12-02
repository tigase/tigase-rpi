/*
 * Grovepi.java
 *
 * Tigase RPi Library
 * Copyright (C) 2016-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */

package tigase.pi;

import tigase.pi.events.SensorEvent;
import tigase.pi.events.StatusEvent;
import tigase.pi.utils.Status;
import tigase.pi.utils.I2CScanner;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public final class Grovepi {
  private static Grovepi instance;

  private boolean isInit = false;
  private boolean isHalt = false;

  private static Board board;
  private final CopyOnWriteArrayList<GrovepiListener> listeners;

  //private Debug debug;

  public Grovepi() throws Exception {
//    debug = new Debug("com.dexterind.gopigo.Grovepi");
//    debug.log(Debug.FINEST, "Instancing a new GrovePi");

    try {
			try {
				board = Board.getInstance(Board.GROVEPI_BOARD, true);
				board.init();
				System.out.println("Board version: " + board.version());
			} catch( Exception e) {
				System.out.println("Cannot access GrovePI board: " + e.getMessage() );
				System.out.println("Creating direct Raspberry PI access.");
				board = Board.getInstance(Board.PI2GROVER_BOARD, true);
				board.init();
				System.out.println("Board version: " + board.version());
			}

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
		I2CScanner.printI2CInfo();
    listeners = new CopyOnWriteArrayList<GrovepiListener>();
  }

  public static Grovepi getInstance() throws Exception {
    if(instance == null) {
      instance = new Grovepi();
    }
    return instance;
  }

	public static Board getBoard() throws Exception {
		return board;
	}

  public void init() {
//    debug.log(Debug.FINE, "Init " + isInit);
    //board.init();
    isInit = true;
    StatusEvent statusEvent = new StatusEvent(this, Status.INIT);
    fireEvent(statusEvent);
  }

  public void addListener(GrovepiListener listener) {
//    debug.log(Debug.INFO, "Adding listener");
    listeners.addIfAbsent(listener);
  }

  public void removeListener(GrovepiListener listener) {
    if (listeners != null) {
//      debug.log(Debug.INFO, "Removing listener");
      listeners.remove(listener);
    }
  }

  protected void fireEvent(EventObject event) {
    int i = 0;
//    debug.log(Debug.INFO, "Firing event [" + listeners.toArray().length + " listeners]");

    for (GrovepiListener listener : listeners) {
//      debug.log(Debug.INFO, "listener[" + i + "]");
//      debug.log(Debug.INFO, event.getClass().toString());

      if (event instanceof StatusEvent) {
        listener.onStatusEvent((StatusEvent) event);
      } else if (event instanceof SensorEvent) {
        listener.onSensorEvent((SensorEvent) event);
      }
      i++;
    }
  }
}
