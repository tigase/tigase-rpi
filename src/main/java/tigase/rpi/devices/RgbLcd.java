/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.rpi.devices;

import tigase.rpi.sensors.base.I2cDevice;
import java.io.IOException;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class RgbLcd extends I2cDevice {

  public static final int DISPLAY_RGB_ADDR = 0x62;
  public static final int DISPLAY_TEXT_ADDR = 0x3e;

  private static final int LCD_COMMAND = 0x80;
  private static final int LCD_WRITECHAR = 0x40;

  private static final int LCD_CLEARDISPLAY = 0x01;
  private static final int LCD_NEWLINE = 0xc0;

  private static final int RGB_RED = 0x04;
  private static final int RGB_GREEN = 0x03;
  private static final int RGB_BLUE = 0x02;

//  private final I2CBus i2CBus;
//  private final I2CDevice rgbDevice;
//  private final I2CDevice textDevice;

	public RgbLcd() throws IOException, InterruptedException, Exception {
		//    i2CBus = I2CFactory.getInstance(I2CBus. BUS_1);
		//    rgbDevice = i2CBus.getDevice(DISPLAY_RGB_ADDR);
		//    textDevice = i2CBus.getDevice(DISPLAY_TEXT_ADDR);
		super(0x62);
		init();
	}

  protected final void init() throws IOException {
      write(DISPLAY_RGB_ADDR, 0, 0);
      write(DISPLAY_RGB_ADDR, 1, 0);
      write(DISPLAY_RGB_ADDR, 0x08, 0xaa);
      write(DISPLAY_TEXT_ADDR, LCD_COMMAND, 0x08 | 0x04); // display on, no cursor
      write(DISPLAY_TEXT_ADDR, LCD_COMMAND, 0x28); // 2 Lines
      write(DISPLAY_TEXT_ADDR, LCD_COMMAND, LCD_CLEARDISPLAY); // clear Display
			sleep(50);
  }

//  public void write(I2CDevice device, int... command) throws IOException {
//    byte[] buffer = new byte[command.length];
//    for (int i = 0; i < command.length; i++) {
//      buffer[i] = (byte) command[i];
//    }
//    device.write(buffer, 0, command.length);
//  }

  public void setRGB(int r, int g, int b) throws IOException {
      write(DISPLAY_RGB_ADDR, RGB_RED, r);
      write(DISPLAY_RGB_ADDR, RGB_GREEN, g);
      write(DISPLAY_RGB_ADDR, RGB_BLUE, b);
      sleep(50);
  }

  public void setText(String text) throws IOException {
		write(DISPLAY_TEXT_ADDR, LCD_COMMAND, LCD_CLEARDISPLAY); // clear Display
		sleep(50);
		int count = 0;
		int row = 0;
		for (char c : text.toCharArray()) {
			if (c == '\n' || count == 16) {
				count = 0;
				row += 1;
				if (row == 2) {
					break;
				}
				write(DISPLAY_TEXT_ADDR, LCD_COMMAND, LCD_NEWLINE); // new line
				if (c == '\n') {
					continue;
				}
			}
			count++;
			write(DISPLAY_TEXT_ADDR, LCD_WRITECHAR, c); // Write character
		}
		sleep(100);
	}

 }
