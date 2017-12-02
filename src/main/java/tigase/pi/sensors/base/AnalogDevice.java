/*
 * AnalogDevice.java
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

package tigase.pi.sensors.base;

import tigase.pi.utils.Commands;
import java.io.IOException;


public abstract class AnalogDevice extends Device {
	private int length = 4;

	public AnalogDevice(int port, int length) throws IOException, InterruptedException, Exception {
		super(port);
		this.length = length == -1 ? this.length : length;
  }

	public byte[] readFromPin() throws IOException {
		return board.analogReadFromPin(getPort());
	}

	public byte[] readBytes() throws IOException {
		write(convertToBytes(Commands.AREAD, getPort(), Commands.UNUSED, Commands.UNUSED));
		board.read(1);
		return board.read(length);
  }

	public boolean write(int value) throws IOException {
		write(convertToBytes(Commands.AWRITE, getPort(), value, Commands.UNUSED));
		return true;
	}

}