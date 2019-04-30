package logic

class BmiForKgCm : Bmi {
        private val validMassRange = 20..300
    private val validHeightRange = 50..250

    override fun countBMI(mass: Int, height: Int): Double {
        require(mass in validMassRange && height in validHeightRange) { "invalid data" }
        return mass * 10000.0 / (height * height)
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