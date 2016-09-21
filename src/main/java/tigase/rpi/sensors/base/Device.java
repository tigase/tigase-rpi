/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.rpi.sensors.base;

import tigase.rpi.Board;
import tigase.rpi.Grovepi;
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
