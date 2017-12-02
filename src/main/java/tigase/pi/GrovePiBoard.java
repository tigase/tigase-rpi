/*
 * GrovePiBoard.java
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
package tigase.pi;

import tigase.pi.utils.Commands;
import tigase.pi.utils.Status;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class GrovePiBoard extends Board {

  private static Board instance = null;
  //private final I2CDevice device;

  public static final byte PIN_MODE_OUTPUT = 1;
  public static final byte PIN_MODE_INPUT = 0;
  private static final byte GROVEPI_PLUS_BOARD_ADDR = 0x04;
	private static final byte PI2GROVER_BOARD_ADDR = 0x03;
	private int address = -1;

  //private Debug debug;

  private GrovePiBoard() throws IOException, InterruptedException, Exception {
		super();
		address = GROVEPI_PLUS_BOARD_ADDR;
		//init();
  }

  public static Board getInstance() throws IOException, InterruptedException, Exception {
    if(instance == null) {
      instance = new GrovePiBoard();
    }
    return instance;
  }

	public int write(byte... bytes) throws IOException {
    return writeI2c(address, bytes);
	}

	public int getDefDeviceAddress() {
		return address;
	}

  public int setPinMode(int pin, int pinMode) throws IOException {
    return write(convertToBytes(Commands.PMODE, pin, pinMode, Commands.UNUSED));
  }

  public void init() {
    try {
			write(convertToBytes(0xfe, 0x04));
			sleep(100);
    } catch (IOException e) {
      StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      String exceptionDetails = sw.toString();
    }
  }

  public String version() throws IOException {
    write(convertToBytes(Commands.VERSION, Commands.UNUSED, Commands.UNUSED, Commands.UNUSED));
    sleep(100);

    byte[] b = read(4);
    read(1);

    return String.format("%s.%s.%s", (int)b[1], (int)b[2], (int)b[3]);
  }

	@Override
	public byte[] digitalReadFromPin(int port) throws IOException {
		write(convertToBytes(Commands.DREAD, port, Commands.UNUSED, Commands.UNUSED));
		read(1);
		return read(4);
	}

	@Override
	public byte[] analogReadFromPin(int port) throws IOException {
		write(convertToBytes(Commands.AREAD, port, Commands.UNUSED, Commands.UNUSED));
		read(1);
		return read(4);
	}

	@Override
	public int digitalWriteToPin(int port, int val) throws IOException {
		write(convertToBytes(Commands.AWRITE, port, val, Commands.UNUSED));
		return Status.OK;
	}

	@Override
	public int analogWriteToPin(int port, int val) throws IOException {
		write(convertToBytes(Commands.DWRITE, port, val, Commands.UNUSED));
		return Status.OK;
	}

}
