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
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import tigase.rpi.sensors.CPUTemp;
import tigase.rpi.utils.CommonTimer;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class CPUFanController extends TimerTask {

	public static final String CPU_TEMP_TRESHOLD = "cpu-threshold";

	private static CPUFanController fc = null;

	private CPUTemp cpu = null;
	private Led led = null;
	private Relay relay = null;

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

}
