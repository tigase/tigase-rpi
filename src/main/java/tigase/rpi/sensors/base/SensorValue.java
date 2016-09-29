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
package tigase.rpi.sensors.base;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class SensorValue {

	private String description;
	private String shorName;
	private String unit;
	private float value;

	public SensorValue(String desc, String name, String unit, float value) {
		this.description = desc;
		this.shorName = name;
		this.unit = unit;
		this.value = value;
	}

	public void updateValue(float value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public String getShortName() {
		return shorName;
	}

	public String getUnit() {
		return unit;
	}

	public float getValue() {
		return value;
	}

	public String getStrValue() {
		return String.format("%1$.2f", value);
	}

	public String toString() {
		return description + ": " + getStrValue() + unit;
	}

}
