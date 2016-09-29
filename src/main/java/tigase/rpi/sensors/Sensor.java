/**
 * TODO: Implement "watch" and "stream" methods, such as the Node.js library
 */
package tigase.rpi.sensors;

import java.io.IOException;
import java.util.Map;
import tigase.rpi.sensors.base.SensorValue;

public interface Sensor {

	Map<String, SensorValue> getValues() throws IOException;

}