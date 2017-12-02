/*
 * DigitalDevice.java
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


public abstract class DigitalDevice extends Device {

	public DigitalDevice(int port) throws IOException, InterruptedException, Exception {
		super(port);
  }

	public byte[] readBytes() throws IOException {
		write(convertToBytes(Commands.DREAD, getPort(), Commands.UNUSED, Commands.UNUSED));
		return this.board.read(1);
	}

	public int write(int value) throws IOException {
		return board.digitalWriteToPin(getPort(), value);
  }

}