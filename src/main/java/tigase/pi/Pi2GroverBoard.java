/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.pi;

import tigase.pi.utils.Status;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.RaspiGpioProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.RaspiPinNumberingScheme;
import com.pi4j.wiringpi.Gpio;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class Pi2GroverBoard extends Board {

  private static Board instance = null;
  private GpioController gpio = null;
	private HashMap<Integer, GpioPin> pins = null;

  private Pi2GroverBoard() throws IOException, InterruptedException, Exception {
		pins = new HashMap<>();

		// Activate the GrovePi compatible pin numbering mode
		GpioFactory.setDefaultProvider(new RaspiGpioProvider(RaspiPinNumberingScheme.BROADCOM_PIN_NUMBERING));
		gpio = GpioFactory.getInstance();

  }

  public static Board getInstance() throws IOException, InterruptedException, Exception {
    if(instance == null) {
      instance = new Pi2GroverBoard();
    }
    return instance;
  }

  public String version() throws IOException {
		return "Pi2Grover NEW";
  }

	@Override
	public void init() {
//		try {
//			// Make sure I2C is enabled on the Pi2Grover board
//			setPinMode(3, Gpio.OUTPUT);
//			digitalWriteToPin(3, PinState.HIGH.getValue());
//		} catch (IOException ex) {
//			Logger.getLogger(Pi2GroverBoard.class.getName()).log(Level.SEVERE, null, ex);
//		}
	}

//	@Override
//	public byte[] readFromPin(int port) throws IOException {
//		Gpio.pinMode(RaspiPin.getPinByAddress(port).getAddress(), Gpio.INPUT);
//		int res = Gpio.digitalRead(RaspiPin.getPinByAddress(port).getAddress());
//		return ByteBuffer.allocate(4).putInt(res).array();
//	}

	@Override
	public int setPinMode(int pin, int pinMode) throws IOException {
		Gpio.pinMode (RaspiPin.getPinByAddress(pin).getAddress(), pinMode);
		return Status.OK;
	}

	@Override
	public byte[] digitalReadFromPin(int port) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public byte[] analogReadFromPin(int port) throws IOException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int digitalWriteToPin(int port, int val) throws IOException {
		Gpio.digitalWrite(RaspiPin.getPinByAddress(port).getAddress(), val);
		return Status.OK;
	}

	@Override
	public int analogWriteToPin(int port, int val) throws IOException {
		Gpio.analogWrite(RaspiPin.getPinByAddress(port).getAddress(), val);
		return Status.OK;
	}

}
