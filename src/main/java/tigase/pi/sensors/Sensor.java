/**
 * TODO: Implement "watch" and "stream" methods, such as the Node.js library
 */
package tigase.pi.sensors;

import java.io.IOException;
import java.util.Map;
import tigase.pi.sensors.base.SensorValue;

public interface Sensor {

	public static final String TEMP_SHORT_NAME = "T";
	public static final String HUMD_SHORT_NAME = "H";
	public static final String PRES_SHORT_NAME = "P";

	public static final String TEMP_UNIT_C = "C";
	public static final String TEMP_UNIT_F = "F";
	public static final String HUMD_UNIT = "%";
	public static final String PRES_UNIT = "hPa";

	Map<String, SensorValue> getValues() throws IOException;

}