package tigase.rpi.sensors;

import tigase.rpi.sensors.base.Device;
import tigase.rpi.sensors.Sensor;
import tigase.rpi.utils.Commands;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DHTDigitalSensor extends Device implements Sensor {
  private int moduleType = 0;
  private int scale = 0;
  public static final int MODULE_DHT11 = 0;
  public static final int MODULE_DHT22 = 1;
  public static final int MODULE_DHT21 = 2;
  public static final int MODULE_AM2301 = 3;

  public static final int SCALE_C = 0;
  public static final int SCALE_F = 1;

  private byte[] bytes;

  private float convertCtoF(double temp) {
	  return (float) temp * 9 / 5 + 32;
  }

  private float convertFtoC(double temp) {
	  return (float) ((temp - 32) * 5 / 9);
  }

  private float getHeatIndex(float temp, float hum, int scale) {
	  boolean needsConversion = scale == DHTDigitalSensor.SCALE_C;
	  temp = needsConversion ? this.convertCtoF(temp) : temp;

	  double hi = -42.379 +
		           2.04901523  * temp +
		           10.14333127 * hum +
		          -0.22475541  * temp * hum +
		          -0.00683783  * Math.pow(temp, 2) +
		          -0.05481717  * Math.pow(hum, 2) +
		           0.00122874  * Math.pow(temp, 2) * hum +
		           0.00085282  * temp * Math.pow(hum, 2) +
		          -0.00000199  * Math.pow(temp, 2) * Math.pow(hum, 2);

	  return (float) (needsConversion ? this.convertFtoC(hi) : hi);
  }

  public DHTDigitalSensor(int pin, int moduleType, int scale) throws IOException, InterruptedException, Exception {
		super(pin);
		this.moduleType = moduleType;
		this.scale = scale;
  }

	private void storeBytes() throws IOException {
		byte[] buff = new byte[]{};
		write(convertToBytes(Commands.DHT_TEMP, getPort(), moduleType, Commands.UNUSED));
		board.sleep(500);

		read(1);
		board.sleep(200);

		bytes = read(9);
  }

	public float[] read() {
		try {
			this.storeBytes();
		} catch(IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}

		float temp = ByteBuffer.wrap(this.bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat(1);
		float hum = ByteBuffer.wrap(this.bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat(5);
		float heatIndex = this.getHeatIndex(temp, hum, this.scale);

		return new float[]{temp, hum , heatIndex};
	}

	@Override
	public float[] getValues() throws IOException {
		return read();
	}

}