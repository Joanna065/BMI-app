package logic

interface Bmi {
    fun countBMI(mass: Int, height: Int): Double
    fun getMassRange(): IntRange
    fun getHeightRange(): IntRange
    fun isValidMass(mass: Int): Boolean
    fun isValisHeight(height: Int): Boolean
}