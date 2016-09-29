/*
 * Copyright (C) 2016 Artur Hefczyc <artur.hefczyc at tigase.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tigase.rpi.devices;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import tigase.rpi.sensors.Sensor;
import tigase.rpi.sensors.base.DeviceStatus;
import tigase.rpi.sensors.base.SensorValue;
import tigase.rpi.utils.CommonTimer;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class BlinkingLed extends TimerTask implements Sensor {

	public static final String LED_DESCR = "Blinking Led";
	public static final String LED_NAME = "BL";
	public static final String LED_UNIT = "";

	private long delay;
	private Led led;
	private SensorValue val;
	private Map<String, SensorValue> results = new HashMap();


	public BlinkingLed(int pin, long delay) throws Exception {
		super();
		this.delay = delay;

		led = new Led(pin);
		val = new SensorValue(LED_DESCR, LED_NAME, LED_UNIT, (led.getStatus() == DeviceStatus.ON ? led.getOnValue() : led.getOffValue()));
		results.put(LED_DESCR, val);
		CommonTimer.schedule(this, delay, delay);
	}

	public DeviceStatus getStatus() {
		return led.getStatus();
	}

	@Override
	public Map<String, SensorValue> getValues() throws IOException {
		val.updateValue((led.getStatus() == DeviceStatus.ON ? led.getOnValue() : led.getOffValue()));
		return results;
	}

	public void run() {
		try {
			led.toggle();
		} catch (IOException ex) {
			Logger.getLogger(BlinkingLed.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
