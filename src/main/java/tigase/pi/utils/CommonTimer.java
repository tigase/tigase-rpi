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
package tigase.pi.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class CommonTimer {

		private static final Timer timer = new Timer("Common Sensors Timer", true);

	public static void schedule(TimerTask task, long delay, long period) {
		timer.schedule(task, delay, period);
	}



}
