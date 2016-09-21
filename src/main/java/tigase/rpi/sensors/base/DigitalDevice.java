package tigase.rpi.sensors.base;

import tigase.rpi.utils.Commands;
import java.io.IOException;


public abstract class DigitalDevice extends Device {

	public DigitalDevice(int port) throws IOException, InterruptedException, Exception {
		super(port);
  }

	public byte[] readBytes() throws IOException {
		write(convertToBytes(Commands.DREAD, getPort(), Commands.UNUSED, Commands.UNUSED));
		return this.board.read(1);
	}

	public int write(int value) throws IOException {
		return board.digitalWriteToPin(getPort(), value);
  }

}