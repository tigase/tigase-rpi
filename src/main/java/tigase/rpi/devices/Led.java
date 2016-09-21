package tigase.rpi.devices;

/*
 * **********************************************************************
 * PROJECT       :  GrovePi Java Library
 *
 * This file is part of the GrovePi Java Library project. More information about
 * this project can be found here:  https://github.com/DexterInd/GrovePi
 * **********************************************************************
 *
 * ## License
 *
 * The MIT License (MIT)
 * GrovePi for the Raspberry Pi: an open source platform for connecting Grove Sensors to the Raspberry Pi.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.io.IOException;


/**
 * Handles control of LED sensor for GrovePi.
 *
 * Note the LED will only be dimmable if the sensor is connected
 * to a port that supports Pulse Width Modulation
 *
 * Digital ports that support Pulse Width Modulation (PWM)
 * D3, D5, D6
 *
 * Digital ports that do not support PWM
 * D2, D4, D7, D8
 *
 * @author Chad Williams
 *
 */
public class Led extends StateDevice {
  public final static int MAX_BRIGHTNESS = 255;

  /**
   * Create a new LED connected to the specified pin.
   *
   * @param pin GrovePi pin number
   * @throws IOException
   * @throws InterruptedException
   */
  public Led(int pin) throws IOException, InterruptedException, Exception{
    super(pin, MAX_BRIGHTNESS + 1);
  }

	public int getOnValue() {
		return MAX_BRIGHTNESS;
	}

	public int getMaxValue() {
		return MAX_BRIGHTNESS;
	}

  /**
   * Sets the LED brightness to the specified percentage of the maximum brightness,
   * note dimming only works if connected to a pin that supports PWM otherwise this
   * will just appear to turn the LED on/off.
   * @param percent Percentage of maximum brightness, expects a number from 0 to 100
   * @throws IOException
   */
  public void setBrightness(float percent) throws IOException{
    if (percent <= 0){
      turnOff();
    }else if(percent >= 100){
      turnOn();
    }else{
      write((int)((MAX_BRIGHTNESS * percent)/100));
    }
  }

}
