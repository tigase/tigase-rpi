/*
 * Device.java
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.pi.sensors.base;

import tigase.pi.Board;
import tigase.pi.Grovepi;
import java.io.IOException;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class Device {

	protected Board board;
	private int port = 0;

  public Device(int port) throws IOException, InterruptedException, Exception {
    this.port = port;
		board = Grovepi.getBoard();
  }

	public int getPort() {
		return port;
	}

	public int setPinMode(int pin, int pinMode) throws IOException {
		return board.setPinMode(pin, pinMode);
	}

	public int write(int deviceAddr, byte... bytes) throws IOException {
		return board.write(deviceAddr, bytes);
	}

	public int write(byte... bytes) throws IOException {
		return board.write(bytes);
	}

//	public int write(int... bytes) throws IOException {
//		return board.write(bytes);
//	}

	public byte[] read(int numberOfBytes) throws IOException {
		return board.read(numberOfBytes);
  }

	public byte[] read(int deviceAddr, int numberOfBytes) throws IOException {
		return board.read(deviceAddr, numberOfBytes);
	}

	public byte[] read(int deviceAddr, int regAddr, int numberOfBytes) throws IOException {
		return board.read(deviceAddr, regAddr, numberOfBytes);
	}

	public void sleep(long i) {
		board.sleep(i);
	}

	public byte[] convertToBytes(int... buff) {
		return board.convertToBytes(buff);
	}

}
