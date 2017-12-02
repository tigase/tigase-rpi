/*
 * BlinkingLed.java
 *
 * Tigase RPi Library
 * Copyright (C) 2016-2017 "Tigase, Inc." <office@tigase.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.pi.devices;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import tigase.pi.sensors.Sensor;
import tigase.pi.sensors.base.DeviceStatus;
import tigase.pi.sensors.base.SensorValue;
import tigase.pi.utils.CommonTimer;

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
