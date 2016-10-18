/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tigase.rpi.sensors;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import tigase.rpi.sensors.base.I2cDevice;
import tigase.rpi.sensors.base.SensorValue;

/**
 *
 * @author Artur Hefczyc <artur.hefczyc at tigase.net>
 */
public class BME280Sensor extends I2cDevice implements Sensor {

	public static final String TEMP_DESCR = "BME280 Temperature";
	public static final String TEMP_NAME = TEMP_SHORT_NAME;
	public static final String HUMD_DESCR = "BME280 Humidity";
	public static final String HUMD_NAME = HUMD_SHORT_NAME;
	public static final String PRES_DESCR = "BME280 Pressure";
	public static final String PRES_NAME = PRES_SHORT_NAME;

	private static final int BME280_ADDR = 0x76;
	private static final int CHIP_ID_REG  = 0xD0;
	// It seems to be that due to internal heating of the sensor board
	// it shows about 3C temperature is higher than realtemperature
	private static final float CTEMP_CORRECTION = -3f;

	private Map<String, SensorValue> results = new HashMap();
	private SensorValue temp;
	private SensorValue hum;
	private SensorValue pres;
	private static BME280Sensor sensor = null;

	public static BME280Sensor getInstance() throws Exception {
		if (sensor == null) {
			sensor = new BME280Sensor();
		}
		return sensor;
	}

	private BME280Sensor() throws IOException, InterruptedException, Exception {
		super(BME280_ADDR);
		init();
		temp = new SensorValue(TEMP_DESCR, TEMP_NAME, TEMP_UNIT_C, 0f);
		hum = new SensorValue(HUMD_DESCR, HUMD_NAME, HUMD_UNIT, 0f);
		pres = new SensorValue(PRES_DESCR, PRES_NAME, PRES_UNIT, 0f);
		results.put(TEMP_NAME, temp);
		results.put(HUMD_NAME, hum);
		results.put(PRES_NAME, pres);
	}

  protected final void init() throws IOException {
  }

	public byte[] readBME280ID() throws IOException {
		return read(BME280_ADDR, 0xD0, 2);
	}

	public synchronized double[] readBME280All() throws IOException {

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
		double cTemp = (var1 + var2) / 5120.0 + CTEMP_CORRECTION;
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

		double[] res = new double[3];
		res[0] = cTemp;
		res[1] = humidity;
		res[2] = pressure;
		return res;
	}

	@Override
	public Map<String, SensorValue> getValues() throws IOException {
		double[] res = readBME280All();
		temp.updateValue((float)res[0]);
		hum.updateValue((float)res[1]);
		pres.updateValue((float)res[2]);
		return results;
	}

}
