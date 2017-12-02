/*
 * I2CScanner.java
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
package tigase.pi.utils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.wiringpi.I2C;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class I2CScanner {

	private static final int MODE_WRITE = 1;
	private static final int MODE_READ = 2;
	private static final int FIRST = 0x03;
	private static final int LAST = 0x77;

	private static int[] i2cDevices = new int[128];

	static {
		GpioController gpio = GpioFactory.getInstance();
		I2CBus bus = null;
		try {
			bus = I2CFactory.getInstance(I2CBus.BUS_1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		int cmd = MODE_WRITE;
		for (int r = 0; r < 128; r += 16) {
			for (int i = 0; i < 16; i++) {
				if ((r + i >= 0x30 && r + i <= 0x37) || (r + i >= 0x50 && r + i <= 0x5F)) {
					cmd = MODE_READ;
				} else {
					cmd = MODE_WRITE;
				}
				/* Skip unwanted addresses */
				if (r + i < FIRST || r + i > LAST) {
					i2cDevices[r+i] = -3;
					continue;
				}
				int dev = I2C.wiringPiI2CSetup(r + i);
				if (dev < 0) {
					i2cDevices[r+i] = -1;
					continue;
				}
				int res = 1;
				if (cmd == MODE_WRITE) {
					res = I2C.wiringPiI2CWrite(dev, 0);
				}
				if (cmd == MODE_READ) {
					res = I2C.wiringPiI2CRead(dev);
				}
				if (res >= 0) {
					i2cDevices[r+i] = res;
				} else {
					i2cDevices[r+i] = -2;
				}
			}
		}
	}

	public static void printI2CInfo() {
		System.out.println("     0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f");
		System.out.print("0");
		for (int r = 0; r < 128; r += 16) {
			System.out.print(Integer.toHexString(r) + ": ");
			System.out.flush();
			for (int i = 0; i < 16; i++) {
				System.out.flush();
				/* Skip unwanted addresses */
				if (r + i < FIRST || r + i > LAST) {
					System.out.print("   ");
					continue;
				}
				if (i2cDevices[r+i] == -1) {
					System.out.print("UU ");
					continue;
				}
				if (i2cDevices[r+i] >= 0) {
					System.out.print(Integer.toHexString(r + i) + " ");
				} else {
					System.out.print("-- ");
				}
			}
			System.out.println();
		}
	}

	public static boolean isConnected(int deviceAddr) {
		return i2cDevices[deviceAddr] >= 0;
	}

}
