package tigase.pi;

import tigase.pi.utils.Status;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.system.SystemInfo;
import java.io.IOException;

public abstract class Board {

	public static final String GROVEPI_BOARD = "grovepi-board";
	public static final String PI2GROVER_BOARD = "pi2grover-board";

	private static Board instance = null;
	private static I2CBus bus;

  public static Board getInstance(String board, boolean reset) throws IOException, InterruptedException, Exception {
    if(instance == null || reset) {
			if (board.equals(GROVEPI_BOARD)) {
				instance = GrovePiBoard.getInstance();
			}
			if (board.equals(PI2GROVER_BOARD)) {
				instance = Pi2GroverBoard.getInstance();
			}
    }
    return instance;
  }

	protected Board() throws IOException {
    int busId;

		try {
			String type = SystemInfo.getBoardType().name();

			if (type.indexOf("ModelA") > 0) {
				busId = I2CBus.BUS_0;
			} else {
				busId = I2CBus.BUS_1;
			}

			bus = I2CFactory.getInstance(busId);
		} catch (IOException | InterruptedException | UnsupportedOperationException | I2CFactory.UnsupportedBusNumberException ex) {
			throw new IOException(ex);
		}
	}

	public int write(int devAddress, byte... bytes) throws IOException {
		return writeI2c(devAddress, bytes);
	}

	public int write(byte... bytes) throws IOException {
		return writeI2c(getDefDeviceAddress(), bytes);
	}

//	public int write(int... bytes) throws IOException {
//		return write(convertToBytes(bytes));
//	}

	public byte[] read(int deviceAddr, int numberOfBytes) throws IOException {
		return read(deviceAddr, 1, numberOfBytes);
  }

	public byte[] read(int numberOfBytes) throws IOException {
		return read(getDefDeviceAddress(), numberOfBytes);
  }

	public byte[] read(int deviceAddr, int regAddr, int numberOfBytes) throws IOException {
    return readI2c(deviceAddr, regAddr, numberOfBytes);
  }


  public int writeI2c(int deviceAddr, byte... buffer) throws IOException {
		I2CDevice dev = bus.getDevice(deviceAddr);
    dev.write(buffer);
    return Status.OK;

  }

  public int writeI2c(int deviceAddr, int regAddr, byte... buffer) throws IOException {
		I2CDevice dev = bus.getDevice(deviceAddr);
    dev.write(regAddr, buffer);
    return Status.OK;

  }

	public byte[] convertToBytes(int... ints) {
    byte[] buffer = new byte[ints.length];
    for (int i = 0; i < ints.length; i++) {
      buffer[i] = (byte) ints[i];
    }
		return buffer;
	}

  public byte[] readI2c(int deviceAddr, int regAddr, int numberOfBytes) throws IOException {
		I2CDevice dev = bus.getDevice(deviceAddr);
    byte[] buffer = new byte[numberOfBytes];
    dev.read(regAddr, buffer, 0, buffer.length);
    return buffer;
  }

  public abstract int setPinMode(int pin, int pinMode) throws IOException;

  public abstract void init();

  public abstract String version() throws IOException;

  public void sleep(long msec) {
    try {
      Thread.sleep(msec);
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

//	public abstract byte[] readFromPin(int port) throws IOException;

	public abstract byte[] digitalReadFromPin(int port) throws IOException;

	public abstract byte[] analogReadFromPin(int port) throws IOException;

	public abstract int digitalWriteToPin(int port, int val) throws IOException;

	public abstract int analogWriteToPin(int port, int val) throws IOException;

	public int getDefDeviceAddress() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}