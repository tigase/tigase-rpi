/**
 * TODO: Implement "watch" and "stream" methods, such as the Node.js library
 */
package tigase.rpi.sensors;

import java.io.IOException;

public interface Sensor {

	float[] getValues() throws IOException;

}