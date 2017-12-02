/*
 * CPUTemp.java
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
package tigase.pi.sensors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tigase.pi.sensors.base.SensorValue;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class CPUTemp implements Sensor {

	public static final String TEMP_DESCR = "CPU Temp";
	public static final String TEMP_NAME = "CPU";
	public static final String TEMP_UNIT = "C";

	private Map<String, SensorValue> results = new HashMap();
	private SensorValue temp = new SensorValue(TEMP_DESCR, TEMP_NAME, TEMP_UNIT, 0f);
	private static CPUTemp cpuTemp = null;

	private CPUTemp() {
		results.put(TEMP_DESCR, temp);
	}

	public static CPUTemp getInstance() {
		if (cpuTemp == null) {
			cpuTemp = new CPUTemp();
		}
		return cpuTemp;
	}

	public float getCPUTemp() throws IOException {
			List<String> lines = Files.readAllLines(new File("/sys/class/thermal/thermal_zone0/temp").toPath());
			if (lines.size() > 0) {
				return Float.parseFloat(lines.get(0))/1000;
			} else {
				return Float.NaN;
			}
		}

	@Override
	public Map<String, SensorValue> getValues() throws IOException {
		temp.updateValue(getCPUTemp());
		return results;
	}

}
