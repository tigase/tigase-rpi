/*
 * Commands.java
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

package tigase.pi.utils;

public class Commands {
  public static final int DREAD                           = 1;
  public static final int DWRITE                          = 2;
  public static final int AREAD                           = 3;
  public static final int AWRITE                          = 4;
  public static final int PMODE                           = 5;
  public static final int UREAD                           = 7;
  public static final int VERSION                         = 8;
  public static final int ACC_XYZ                         = 20;
  public static final int RTC_GET_TIME                    = 30;
  public static final int DHT_TEMP                        = 40;
  public static final int LEDBAR_INIT                     = 50;
  public static final int LEDBAR_ORIENT                   = 51;
  public static final int LEDBAR_LEVEL                    = 52;
  public static final int LEDBAR_SET_ONE                  = 53;
  public static final int LEDBAR_TOGGLE_ONE               = 54;
  public static final int LEDBAR_SET                      = 55;
  public static final int LEDBAR_GET                      = 56;
  public static final int FOUR_DIGIT_INIT                 = 70;
  public static final int FOUR_DIGIT_BRIGHTNESS           = 71;
  public static final int FOUR_DIGIT_VALUE                = 72;
  public static final int FOUR_DIGIT_VALUE_ZEROS          = 73;
  public static final int FOUR_DIGIT_INDIVIDUAL_DIGIT     = 74;
  public static final int FOUR_DIGIT_INDIVIDUAL_LEDS      = 75;
  public static final int FOUR_DIGIT_SCORE                = 76;
  public static final int FOUR_DIGIT_AREAD                = 77;
  public static final int FOUR_DIGIT_ALL_ON               = 78;
  public static final int FOUR_DIGIT_ALL_OFF              = 79;
  public static final int STORE_COLOR                     = 90;
  public static final int CHAINABLE_RGB_LED_INIT          = 91;
  public static final int CHAINABLE_RGB_LED_TEST          = 92;
  public static final int CHAINABLE_RGB_LED_SET_PATTERN   = 93;
  public static final int CHAINABLE_RGB_LED_SET_MODULO    = 94;
  public static final int CHAINABLE_RGB_LED_SET_LEVEL     = 95;
  public static final int IR_READ                         = 21;
  public static final int IR_RECV_PIN                     = 22;
  public static final int DUST_SENSOR_READ                = 10;
  public static final int DUST_SENSOR_EN                  = 14;
  public static final int DUST_SENSOR_DIS                 = 15;
  public static final int ENCODER_READ                    = 11;
  public static final int ENCODER_EN                      = 16;
  public static final int ENCODER_DIS                     = 17;
  public static final int FLOW_READ                       = 12;
  public static final int FLOW_EN                         = 18;
  public static final int FLOW_DIS                        = 13;

  public static final int UNUSED                          = 0;

  public Commands(){}
}