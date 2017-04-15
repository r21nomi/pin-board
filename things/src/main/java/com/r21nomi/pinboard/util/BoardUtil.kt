package com.r21nomi.pinboard.util

import android.os.Build

import com.google.android.things.pio.PeripheralManagerService

object BoardUtil {

    private val DEVICE_EDISON_ARDUINO = "edison_arduino"
    private val DEVICE_EDISON = "edison"
    private val DEVICE_JOULE = "joule"
    private val DEVICE_RPI3 = "rpi3"
    private val DEVICE_PICO = "imx6ul_pico"
    private val DEVICE_VVDN = "imx6ul_iopb"
    private var sBoardVariant = ""

    val RPI3_PIN_20 = "BCM20"
    val RPI3_PIN_21 = "BCM21"

    // For the edison check the pin prefix
    // to always return Edison Breakout pin name when applicable.
    private val boardVariant: String
        get() {
            if (!sBoardVariant.isEmpty()) {
                return sBoardVariant
            }
            sBoardVariant = Build.DEVICE
            if (sBoardVariant == DEVICE_EDISON) {
                val pioService = PeripheralManagerService()
                val gpioList = pioService.gpioList
                if (gpioList.size != 0) {
                    val pin = gpioList[0]
                    if (pin.startsWith("IO")) {
                        sBoardVariant = DEVICE_EDISON_ARDUINO
                    }
                }
            }
            return sBoardVariant
        }
}
