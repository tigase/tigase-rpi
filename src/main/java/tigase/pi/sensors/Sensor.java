/*
 * Sensor.java
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

/**
 * TODO: Implement "watch" and "stream" methods, such as the Node.js library
 */
package tigase.pi.sensors;

import java.io.IOException;
import java.util.Map;
import tigase.pi.sensors.base.SensorValue;

public interface Sensor {

	public static final String TEMP_SHORT_NAME = "T";
	public static final String HUMD_SHORT_NAME = "H";
	public static final String PRES_SHORT_NAME = "P";

	public static final String TEMP_UNIT_C = "C";
	public static final String TEMP_UNIT_F = "F";
	public static final String HUMD_UNIT = "%";
	public static final String PRES_UNIT = "hPa";

	Map<String, SensorValue> getValues() throws IOException;

}