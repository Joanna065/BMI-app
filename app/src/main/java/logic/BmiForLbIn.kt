package logic

class BmiForLbIn : Bmi {
    private val validMassRange = 44..661
    private val validHeightRange = 20..100

    override fun countBMI(mass: Int, height: Int): Double {
        require(mass in validMassRange && height in validHeightRange) { "invalid data" }
        return mass * 703.0 / (height * height)
    }

    override fun getMassRange(): IntRange {
        return validMassRange
    }

    override fun getHeightRange(): IntRange {
        return validHeightRange
    }

    override fun isValidMass(mass: Int): Boolean = mass in validMassRange

    override fun isValisHeight(height: Int): Boolean = height in validHeightRange
}