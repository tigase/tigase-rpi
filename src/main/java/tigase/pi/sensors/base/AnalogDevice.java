package tigase.pi.sensors.base;

import tigase.pi.utils.Commands;
import java.io.IOException;


public abstract class AnalogDevice extends Device {
	private int length = 4;

	public AnalogDevice(int port, int length) throws IOException, InterruptedException, Exception {
		super(port);
		this.length = length == -1 ? this.length : length;
  }

	public byte[] readFromPin() throws IOException {
		return board.analogReadFromPin(getPort());
	}

	public byte[] readBytes() throws IOException {
		write(convertToBytes(Commands.AREAD, getPort(), Commands.UNUSED, Commands.UNUSED));
		board.read(1);
		return board.read(length);
  }

	public boolean write(int value) throws IOException {
		write(convertToBytes(Commands.AWRITE, getPort(), value, Commands.UNUSED));
		return true;
	}

}