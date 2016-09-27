/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.rpi.devices;

import tigase.rpi.sensors.base.DeviceStatus;
import tigase.rpi.sensors.base.DigitalDevice;
import tigase.rpi.utils.Status;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class StateDevice  extends DigitalDevice {
  private DeviceStatus status = DeviceStatus.ON;

	public StateDevice(int port) throws IOException, InterruptedException, Exception {
		super(port);
		init();
	}

	public StateDevice(int port, int length) throws IOException, InterruptedException, Exception {
		super(port);
		init();
	}

	private void init() throws IOException {
		try {
			setPinMode(getPort(), 1);
			Thread.sleep(100);
			setStatus(DeviceStatus.OFF);
		} catch (InterruptedException ex) {
			Logger.getLogger(StateDevice.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public int getOnValue() {
		return 1;
	}

	public int getOffValue() {
		return 0;
	}

	public int getMaxValue() {
		return 1;
	}

	public int getMinValue() {
		return 0;
	}

	/**
   * Turns the StateDevice on to the maximum brightness.
   * @throws IOException
   */
  public void turnOn() throws IOException{
    if (status != DeviceStatus.ON) {
			write(getOnValue());
		}
  }

  /**
   * Turns the StateDevice off.
   * @throws IOException
   */
  public void turnOff() throws IOException{
    if (status != DeviceStatus.OFF) {
			write(getOffValue());
		}
  }

  /**
   * Returns the current status of the StateDevice.
   * @return Returns the status of the StateDevice
   */
  public DeviceStatus getStatus(){
    return status;
  }

  /**
   * Set the status of the StateDevice to the passed status.
   * @param status
   * @throws IOException
   */
  public void setStatus(DeviceStatus status) throws IOException{

		if (status == DeviceStatus.OFF) {
      turnOff();
		} else {
      turnOn();
    }
  }

	/**
   * Toggles the LED on/off.
   * @return Returns the new status of the LED
   * @throws IOException
   */
  public DeviceStatus toggle() throws IOException{
    setStatus(DeviceStatus.toggle(status));
    return status;
  }

	/**
   * Set the analog value of the StateDevice and sensor status.
   * @param value Expects a value from 0 to MAX_BRIGHTNESS
   * @return
   * @throws IOException
   */
  @Override
  public int write(int value) throws IOException {
    int res = Status.ERROR;
		if (value <= getMinValue()){
      status = DeviceStatus.OFF;
      res = super.write(getOffValue());
    }else{
      status = DeviceStatus.ON;
      res = super.write(Math.min(value, getMaxValue()));
    }
    return res;
  }
}
