package tigase.pi.sensors.base;

import tigase.pi.utils.I2CScanner;
import java.io.IOException;

public abstract class I2cDevice extends Device {

	private int device;

  public I2cDevice(int pin) throws IOException, InterruptedException, Exception {
		super(pin);
		device = pin;
		if (!I2CScanner.isConnected(device)) {
			throw new IOException("Device " + Integer.toHexString(device) + " not connected.");
		}
  }

	public int write(int devAddr, int regAddr, int... data) throws IOException {
		return board.writeI2c(devAddr, regAddr, convertToBytes(data));
	}

	public int getDeviceAddress() {
		return device;
	}

}