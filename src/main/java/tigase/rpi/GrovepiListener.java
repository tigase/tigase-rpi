package tigase.rpi;

import tigase.rpi.events.SensorEvent;
import tigase.rpi.events.StatusEvent;
import java.util.EventListener;

public interface GrovepiListener extends EventListener {
  public void onStatusEvent(StatusEvent event);
  public void onSensorEvent(SensorEvent event);
}
