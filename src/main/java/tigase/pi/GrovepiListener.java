package tigase.pi;

import tigase.pi.events.SensorEvent;
import tigase.pi.events.StatusEvent;
import java.util.EventListener;

public interface GrovepiListener extends EventListener {
  public void onStatusEvent(StatusEvent event);
  public void onSensorEvent(SensorEvent event);
}
