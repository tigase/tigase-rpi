package tigase.rpi.sensors;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;
import tigase.rpi.sensors.base.Device;
import tigase.rpi.sensors.base.SensorValue;
import tigase.rpi.utils.Commands;

public class DHTDigitalSensor extends Device implements Sensor {

	public static final String TEMP_DESCR = "DHT Temperature";
	public static final String TEMP_NAME = TEMP_SHORT_NAME;
	public static final String HUMD_DESCR = "DHT Humidity";
	public static final String HUMD_NAME = HUMD_SHORT_NAME;

	public static final int MODULE_DHT11 = 0;
  public static final int MODULE_DHT22 = 1;
  public static final int MODULE_DHT21 = 2;
  public static final int MODULE_AM2301 = 3;

  public static final int SCALE_C = 0;
  public static final int SCALE_F = 1;

  private int moduleType = 0;
  private int scale = 0;
  //private byte[] bytes;
	private Map<String, SensorValue> results = new HashMap();
	private SensorValue temp;
	private SensorValue hum;

  public DHTDigitalSensor(int pin, int moduleType, int scale) throws IOException, InterruptedException, Exception {
		super(pin);
		this.moduleType = moduleType;
		this.scale = scale;
		String sc = (scale == SCALE_C ? TEMP_UNIT_C : TEMP_UNIT_F);
		temp = new SensorValue(TEMP_DESCR, TEMP_NAME, sc, 0f);
		hum = new SensorValue(HUMD_DESCR, HUMD_NAME, HUMD_UNIT, 0f);
		results.put(TEMP_NAME, temp);
		results.put(HUMD_NAME, hum);
  }

  private float convertCtoF(double temp) {
	  return (float) temp * 9 / 5 + 32;
  }

  private float convertFtoC(double temp) {
	  return (float) ((temp - 32) * 5 / 9);
  }

  private float getHeatIndex(float temp, float hum, int scale) {
	  boolean needsConversion = scale == DHTDigitalSensor.SCALE_C;
	  temp = needsConversion ? convertCtoF(temp) : temp;

	  double hi = -42.379 +
		           2.04901523  * temp +
		           10.14333127 * hum +
		          -0.22475541  * temp * hum +
		          -0.00683783  * Math.pow(temp, 2) +
		          -0.05481717  * Math.pow(hum, 2) +
		           0.00122874  * Math.pow(temp, 2) * hum +
		           0.00085282  * temp * Math.pow(hum, 2) +
		          -0.00000199  * Math.pow(temp, 2) * Math.pow(hum, 2);

	  return (float) (needsConversion ? convertFtoC(hi) : hi);
  }

	private synchronized byte[] storeBytes() throws IOException {
		board.sleep(200);
		write(convertToBytes(Commands.DHT_TEMP, getPort(), moduleType, Commands.UNUSED));
		board.sleep(500);

		read(1);
		board.sleep(200);

		return read(9);
  }

	public float[] read() {
		byte[] bytes = null;
		try {
			bytes = storeBytes();
		} catch(IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}

		float temp = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat(1);
		float hum = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat(5);
		float heatIndex = getHeatIndex(temp, hum, this.scale);

		return new float[]{temp, hum , heatIndex};
	}

	@Override
	public Map<String, SensorValue> getValues() throws IOException {
		float[] res = read();
		//System.out.println(Arrays.toString(res));
		// Only update values when data reading was successful.
		if (!Float.isNaN(res[0])) {
			temp.updateValue(res[0]);
		}
		if (!Float.isNaN(res[1])) {
			hum.updateValue(res[1]);
		}
		return results;
	}

}