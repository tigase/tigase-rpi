/*
 * CPUFanController.java
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
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tigase.pi.sensors.CPUTemp;
import tigase.pi.sensors.Sensor;
import tigase.pi.sensors.base.DeviceStatus;
import tigase.pi.sensors.base.SensorValue;
import tigase.pi.utils.CommonTimer;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class CPUFanController extends TimerTask implements Sensor {

	public static final String CPU_TEMP_TRESHOLD = "cpu-threshold";
	public static final String LED_DESCR = "CPU FanController Led";
	public static final String LED_NAME = "FL";
	public static final String LED_UNIT = "";
	public static final String RELAY_DESCR = "CPU FanController Relay";
	public static final String RELAY_NAME = "FR";
	public static final String RELAY_UNIT = "";

	private static CPUFanController fc = null;

	private CPUTemp cpu = null;
	private Led led = null;
	private Relay relay = null;
	private SensorValue ledVal;
	private SensorValue relayVal;
	private Map<String, SensorValue> results = new HashMap();

	public static CPUFanController getInstance(int ledPin, int relayPin) throws Exception {
		if (fc == null) {
			fc = new CPUFanController(ledPin, relayPin);
		}
		return fc;
	}

	private CPUFanController(int ledPin, int relayPin) throws Exception {
		cpu = CPUTemp.getInstance();
		led = new Led(ledPin);
		relay = new Relay(relayPin);
		ledVal = new SensorValue(LED_DESCR, LED_NAME, LED_UNIT, (led.getStatus() == DeviceStatus.ON ? led.getOnValue() : led.getOffValue()));
		results.put(LED_DESCR, ledVal);
		relayVal = new SensorValue(RELAY_DESCR, RELAY_NAME, RELAY_UNIT, (relay.getStatus() == DeviceStatus.ON ? relay.getOnValue() : relay.getOffValue()));
		results.put(RELAY_DESCR, relayVal);
		CommonTimer.schedule(this, TimeUnit.SECONDS.toMillis(30), TimeUnit.SECONDS.toMillis(30));
	}

	@Override
	public void run() {
		try {
			float temp = cpu.getCPUTemp();
			if (Math.round(temp) <= Float.parseFloat(System.getProperty(CPU_TEMP_TRESHOLD, "45"))) {
				led.turnOff();
				relay.turnOff();
			} else {
				led.turnOn();
				relay.turnOn();
			}
		} catch (IOException ex) {
			Logger.getLogger(CPUFanController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public Map<String, SensorValue> getValues() throws IOException {
		ledVal.updateValue((led.getStatus() == DeviceStatus.ON ? led.getOnValue() : led.getOffValue()));
		relayVal.updateValue((relay.getStatus() == DeviceStatus.ON ? relay.getOnValue() : relay.getOffValue()));
		Map<String, SensorValue> res = new HashMap<String, SensorValue>(results);
		res.putAll(cpu.getValues());
		return res;
	}

}
