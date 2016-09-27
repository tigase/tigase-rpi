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
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import tigase.rpi.utils.CommonTimer;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class BlinkingLed extends TimerTask {

	private long delay;
	private Led led;

	public BlinkingLed(int pin, long delay) throws Exception {
		super();
		this.delay = delay;

		led = new Led(pin);
		CommonTimer.schedule(this, delay, delay);
	}

	public void run() {
		try {
			led.toggle();
		} catch (IOException ex) {
			Logger.getLogger(BlinkingLed.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
