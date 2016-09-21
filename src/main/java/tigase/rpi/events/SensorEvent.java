package tigase.rpi.events;

import java.util.EventObject;

public class SensorEvent extends EventObject {
  private static final long serialVersionUID = 5992043874332938157L;
  public String value;

  public SensorEvent(Object source) {
	  super(source);
  }

  public SensorEvent(Object source, String value) {
	  this(source);
	  this.value = value;
  }
}