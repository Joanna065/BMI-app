package com.example.bmi

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import logic.BmiForKgCm
import logic.BmiForLbIn
import org.junit.Assert

class BmiCalcTests : StringSpec() {
    init {
        "for valid mass [kg] and height [cm] return bmi"{
            val bmi = BmiForKgCm()
            bmi.countBMI(65, 170) shouldBeAround 22.491
        }

        "for mass [kg] and height [cm] lower than 0 should throw an exception"{
            val bmi = BmiForKgCm()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(-1, -2)
            }
        }

        "for invalid mass [kg] and height [cm] should throw an exception"{
            val bmi = BmiForKgCm()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(1000, 500)
            }
        }
        "for invalid mass [kg] and valid height [cm] should throw an exception"{
            val bmi = BmiForKgCm()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(10, 160)
            }
        }

        "for valid mass [kg] and invalid height [cm] should throw an exception"{
            val bmi = BmiForKgCm()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(100, 40)
            }
        }

        "for valid mass [lb] and height [in]return bmi"{
            val bmi = BmiForLbIn()
            bmi.countBMI(143, 67) shouldBeAround 22.394
        }

        "for mass [lb] and height [in] lower than 0 should throw an exception"{
            val bmi = BmiForLbIn()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(-1, -2)
            }
        }

        "for invalid mass [lb] and height [in] should throw an exception"{
            val bmi = BmiForLbIn()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(30, 120)
            }
        }

        "for invalid mass [lb] and valid height [in] should throw an exception"{
            val bmi = BmiForLbIn()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(30, 50)
            }
        }

        "for valid mass [lb] and invalid height [in] should throw an exception"{
            val bmi = BmiForLbIn()
            shouldThrow<IllegalArgumentException> {
                bmi.countBMI(100, 150)
            }
        }
    }

    infix fun Double.shouldBeAround(value: Double) {
        Assert.assertEquals(value, this, 0.001)
    }
}