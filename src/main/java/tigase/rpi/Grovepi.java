package tigase.rpi;

import tigase.rpi.events.SensorEvent;
import tigase.rpi.events.StatusEvent;
import tigase.rpi.utils.Status;
import tigase.rpi.utils.I2CScanner;
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
