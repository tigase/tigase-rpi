/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.rpi.sensors;

import tigase.rpi.sensors.base.I2cDevice;
import tigase.rpi.sensors.Sensor;
import java.io.IOException;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class BME280Sensor extends I2cDevice implements Sensor {

	private static final int BME280_ADDR = 0x76;
	private static final int CHIP_ID_REG  = 0xD0;

	public BME280Sensor() throws IOException, InterruptedException, Exception {
		super(BME280_ADDR);
		init();
	}

  protected final void init() throws IOException {
  }

//def getShort(data, index):
//  # return two bytes from data as a signed 16-bit value
//  return c_short((data[index+1] << 8) + data[index]).value
//
//def getUShort(data, index):
//  # return two bytes from data as an unsigned 16-bit value
//  return (data[index+1] << 8) + data[index]
//
//def getChar(data,index):
//  # return one byte from data as a signed char
//  result = data[index]
//  if result > 127:
//    result -= 256
//  return result
//
//def getUChar(data,index):
//  # return one byte from data as an unsigned char
//  result =  data[index] & 0xFF
//  return result
//


//def readBME280ID(addr=DEVICE):
//  # Chip ID Register Address
//  REG_ID     = 0xD0
//  (chip_id, chip_version) = bus.read_i2c_block_data(addr, REG_ID, 2)
//  return (chip_id, chip_version)
	public byte[] readBME280ID() throws IOException {
		return read(BME280_ADDR, 0xD0, 2);
	}


//def readBME280All(addr=DEVICE):
//  # Register Addresses
//  REG_DATA = 0xF7
//  REG_CONTROL = 0xF4
//  REG_CONFIG  = 0xF5
//
//  REG_HUM_MSB = 0xFD
//  REG_HUM_LSB = 0xFE
//
//  # Oversample setting - page 27
//  OVERSAMPLE_TEMP = 2
//  OVERSAMPLE_PRES = 2
//  MODE = 1
//
//  control = OVERSAMPLE_TEMP<<5 | OVERSAMPLE_PRES<<2 | MODE
//  bus.write_byte_data(addr, REG_CONTROL, control)
//
//  # Read blocks of calibration data from EEPROM
//  # See Page 22 data sheet
//  cal1 = bus.read_i2c_block_data(addr, 0x88, 24)
//  cal2 = bus.read_i2c_block_data(addr, 0xA1, 1)
//  cal3 = bus.read_i2c_block_data(addr, 0xE1, 7)
//
//  # Convert byte data to word values
//  dig_T1 = getUShort(cal1, 0)
//  dig_T2 = getShort(cal1, 2)
//  dig_T3 = getShort(cal1, 4)
//
//  dig_P1 = getUShort(cal1, 6)
//  dig_P2 = getShort(cal1, 8)
//  dig_P3 = getShort(cal1, 10)
//  dig_P4 = getShort(cal1, 12)
//  dig_P5 = getShort(cal1, 14)
//  dig_P6 = getShort(cal1, 16)
//  dig_P7 = getShort(cal1, 18)
//  dig_P8 = getShort(cal1, 20)
//  dig_P9 = getShort(cal1, 22)
//
//  cal2 = getUChar(cal2, 0)
//  dig_H2 = getShort(cal3, 0)
//  dig_H3 = getUChar(cal3, 2)
//
//  dig_H4 = getChar(cal3, 3)
//  dig_H4 = (dig_H4 << 24) >> 20
//  dig_H4 = dig_H4 | (getChar(cal3, 4) & 0x0F)
//
//  dig_H5 = getChar(cal3, 5)
//  dig_H5 = (dig_H5 << 24) >> 20
//  dig_H5 = dig_H5 | (getUChar(cal3, 4) >> 4 & 0x0F)
//
//  dig_H6 = getChar(cal3, 6)
//
//  # Read temperature/pressure/humidity
//  data = bus.read_i2c_block_data(addr, REG_DATA, 8)
//  pres_raw = (data[0] << 12) | (data[1] << 4) | (data[2] >> 4)
//  temp_raw = (data[3] << 12) | (data[4] << 4) | (data[5] >> 4)
//  hum_raw = (data[6] << 8) | data[7]
//
//  #Refine temperature
//  var1 = ((((temp_raw>>3)-(dig_T1<<1)))*(dig_T2)) >> 11
//  var2 = (((((temp_raw>>4) - (dig_T1)) * ((temp_raw>>4) - (dig_T1))) >> 12) * (dig_T3)) >> 14
//  t_fine = var1+var2
//  temperature = float(((t_fine * 5) + 128) >> 8);
//
//  # Refine pressure and adjust for temperature
//  var1 = t_fine / 2.0 - 64000.0
//  var2 = var1 * var1 * dig_P6 / 32768.0
//  var2 = var2 + var1 * dig_P5 * 2.0
//  var2 = var2 / 4.0 + dig_P4 * 65536.0
//  var1 = (dig_P3 * var1 * var1 / 524288.0 + dig_P2 * var1) / 524288.0
//  var1 = (1.0 + var1 / 32768.0) * dig_P1
//  if var1 == 0:
//    pressure=0
//  else:
//    pressure = 1048576.0 - pres_raw
//    pressure = ((pressure - var2 / 4096.0) * 6250.0) / var1
//    var1 = dig_P9 * pressure * pressure / 2147483648.0
//    var2 = pressure * dig_P8 / 32768.0
//    pressure = pressure + (var1 + var2 + dig_P7) / 16.0
//
//  # Refine humidity
//  humidity = t_fine - 76800.0
//  humidity = (hum_raw - (dig_H4 * 64.0 + dig_H5 / 16384.8 * humidity)) * (dig_H2 / 65536.0 * (1.0 + dig_H6 / 67108864.0 * humidity * (1.0 + dig_H3 / 67108864.0 * humidity)))
//  humidity = humidity * (1.0 - cal2 * humidity / 524288.0)
//  if humidity > 100:
//    humidity = 100
//  elif humidity < 0:
//    humidity = 0
//
//  return temperature/100.0,pressure/100.0,humidity
	public double[] readBME280All() throws IOException {

		// Read blocks of calibration data from EEPROM

		// Read 24 bytes of data from address 0x88(136)
		byte[] cal1 = read(BME280_ADDR, 0x88, 24);
		// Read 1 byte of data from address 0xA1(161)
		int cal2 = ((byte)read(BME280_ADDR, 0xA1, 1)[0] & 0xFF);
		// Read 7 bytes of data from address 0xE1(225)
		byte[] cal3 = read(BME280_ADDR, 0xE1, 7);

		// temp coefficients
		int dig_T1 = (cal1[0] & 0xFF) + ((cal1[1] & 0xFF) * 256);
		int dig_T2 = (cal1[2] & 0xFF) + ((cal1[3] & 0xFF) * 256);
		if(dig_T2 > 32767) {
			dig_T2 -= 65536;
		}
		int dig_T3 = (cal1[4] & 0xFF) + ((cal1[5] & 0xFF) * 256);
		if(dig_T3 > 32767) {
			dig_T3 -= 65536;
		}

		// pressure coefficients
		int dig_P1 = (cal1[6] & 0xFF) + ((cal1[7] & 0xFF) * 256);
		int dig_P2 = (cal1[8] & 0xFF) + ((cal1[9] & 0xFF) * 256);
		if(dig_P2 > 32767) {
			dig_P2 -= 65536;
		}
		int dig_P3 = (cal1[10] & 0xFF) + ((cal1[11] & 0xFF) * 256);
		if(dig_P3 > 32767) {
			dig_P3 -= 65536;
		}
		int dig_P4 = (cal1[12] & 0xFF) + ((cal1[13] & 0xFF) * 256);
		if(dig_P4 > 32767) {
			dig_P4 -= 65536;
		}
		int dig_P5 = (cal1[14] & 0xFF) + ((cal1[15] & 0xFF) * 256);
		if(dig_P5 > 32767) {
			dig_P5 -= 65536;
		}
		int dig_P6 = (cal1[16] & 0xFF) + ((cal1[17] & 0xFF) * 256);
		if(dig_P6 > 32767) {
			dig_P6 -= 65536;
		}
		int dig_P7 = (cal1[18] & 0xFF) + ((cal1[19] & 0xFF) * 256);
		if(dig_P7 > 32767) {
			dig_P7 -= 65536;
		}
		int dig_P8 = (cal1[20] & 0xFF) + ((cal1[21] & 0xFF) * 256);
		if(dig_P8 > 32767) {
			dig_P8 -= 65536;
		}
		int dig_P9 = (cal1[22] & 0xFF) + ((cal1[23] & 0xFF) * 256);
		if(dig_P9 > 32767) {
			dig_P9 -= 65536;
		}

		// Convert the data
		// humidity coefficients
		int dig_H2 = (cal3[0] & 0xFF) + (cal3[1] * 256);
		if(dig_H2 > 32767) {
			dig_H2 -= 65536;
		}
		int dig_H3 = cal3[2] & 0xFF ;
		int dig_H4 = ((cal3[3] & 0xFF) * 16) + (cal3[4] & 0xF);
		if(dig_H4 > 32767) {
			dig_H4 -= 65536;
		}
		int dig_H5 = ((cal3[4] & 0xFF) / 16) + ((cal3[5] & 0xFF) * 16);
		if(dig_H5 > 32767) {
			dig_H5 -= 65536;
		}
		int dig_H6 = cal3[6] & 0xFF;
		if(dig_H6 > 127) {
			dig_H6 -= 256;
		}

		// Select control humidity register
		// Humidity over sampling rate = 1
		write(BME280_ADDR, 0xF2 , (byte)0x01);
		// Select control measurement register
		// Normal mode, temp and pressure over sampling rate = 1
		write(BME280_ADDR, 0xF4 , (byte)0x27);
		// Select config register
		// Stand_by time = 1000 ms
		write(BME280_ADDR, 0xF5 , (byte)0xA0);

		// Read 8 bytes of data from address 0xF7(247)
		// pressure msb1, pressure msb, pressure lsb, temp msb1, temp msb, temp lsb, humidity lsb, humidity msb
		byte[] data = read(BME280_ADDR, 0xF7, 8);

		// Convert pressure and temperature data to 19-bits
		long adc_p = (((long)(data[0] & 0xFF) * 65536) + ((long)(data[1] & 0xFF) * 256) + (long)(data[2] & 0xF0)) / 16;
		long adc_t = (((long)(data[3] & 0xFF) * 65536) + ((long)(data[4] & 0xFF) * 256) + (long)(data[5] & 0xF0)) / 16;
		// Convert the humidity data
		long adc_h = ((long)(data[6] & 0xFF) * 256 + (long)(data[7] & 0xFF));

		// Temperature offset calculations
		double var1 = (((double)adc_t) / 16384.0 - ((double)dig_T1) / 1024.0) * ((double)dig_T2);
		double var2 = ((((double)adc_t) / 131072.0 - ((double)dig_T1) / 8192.0) *
						(((double)adc_t)/131072.0 - ((double)dig_T1)/8192.0)) * ((double)dig_T3);
		double t_fine = (long)(var1 + var2);
		double cTemp = (var1 + var2) / 5120.0;
		double fTemp = cTemp * 1.8 + 32;

		// Pressure offset calculations
		var1 = ((double)t_fine / 2.0) - 64000.0;
		var2 = var1 * var1 * ((double)dig_P6) / 32768.0;
		var2 = var2 + var1 * ((double)dig_P5) * 2.0;
		var2 = (var2 / 4.0) + (((double)dig_P4) * 65536.0);
		var1 = (((double) dig_P3) * var1 * var1 / 524288.0 + ((double) dig_P2) * var1) / 524288.0;
		var1 = (1.0 + var1 / 32768.0) * ((double)dig_P1);
		double p = 1048576.0 - (double)adc_p;
		p = (p - (var2 / 4096.0)) * 6250.0 / var1;
		var1 = ((double) dig_P9) * p * p / 2147483648.0;
		var2 = p * ((double) dig_P8) / 32768.0;
		double pressure = (p + (var1 + var2 + ((double)dig_P7)) / 16.0) / 100;

		// Humidity offset calculations
		double var_H = (((double)t_fine) - 76800.0);
		var_H = (adc_h - (dig_H4 * 64.0 + dig_H5 / 16384.0 * var_H)) * (dig_H2 / 65536.0 * (1.0 + dig_H6 / 67108864.0 * var_H * (1.0 + dig_H3 / 67108864.0 * var_H)));
		double humidity = var_H * (1.0 -  cal2 * var_H / 524288.0);
		if(humidity > 100.0) {
			humidity = 100.0;
		} else {
			if(humidity < 0.0) {
				humidity = 0.0;
			}
		}

		//String msg = String.format("T:%1$.0fC H:%2$.0f%% P:%3$.0fhPa", cTemp, humidity, pressure);
		//System.out.println(msg);
		// Output data to screen
		//		System.out.printf("Temperature in Celsius : %.2f C %n", cTemp);
		//		System.out.printf("Temperature in Fahrenheit : %.2f F %n", fTemp);
		//		System.out.printf("Pressure : %.2f hPa %n", pressure);
		//		System.out.printf("Relative Humidity : %.2f %% RH %n", humidity);
		double[] res = new double[3];
		res[0] = cTemp;
		res[1] = humidity;
		res[2] = pressure;
		return res;
	}

	@Override
	public float[] getValues() throws IOException {
		double[] vals = readBME280All();
		float[] res = new float[vals.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = (float)vals[i];
		}
		return res;
	}

}
