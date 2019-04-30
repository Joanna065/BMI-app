package com.example.bmi

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat


enum class CategoriesBmi(
    private val nameCategoryId: Int,
    private val colorId: Int,
    private val descriptionId: Int,
    val imageId: Int
) {
    UNDERWEIGHT(
        R.string.underweight_cat_bmi,
        R.color.underweightLapisLazuli,
        R.string.overweight_bmi_description,
        R.drawable.iconfinder_android_robot_mobile_mood_emoji_1172122
    ),
    NORMAL(
        R.string.normal_cat_bmi,
        R.color.normalVerdigris,
        R.string.normal_bmi_description,
        R.drawable.iconfinder_android_robot_mobile_mood_emoji_happy_joke_tounge_1172119
    ),
    OVERWEIGHT(
        R.string.overweight_cat_bmi,
        R.color.overweightGoldenYellow,
        R.string.overweight_bmi_description,
        R.drawable.iconfinder_android_robot_mobile_mood_emoji_sad_1172128
    ),
    OBESE(
        R.string.obese_cat_bmi,
        R.color.obeseFlame,
        R.string.obese_bmi_description,
        R.drawable.iconfinder_android_robot_mobile_mood_emoji_sad_tear_1172125
    ),
    EXTREMELY_OBESE(
        R.string.extremely_obese_cat_bmi,
        R.color.extremelyObesePompeianRose,
        R.string.extremely_obese_bmi_description,
        R.drawable.iconfinder_android_robot_mobile_mood_emoji_crash_bug_dead_1172120
    );

    fun getDescription(resources: Resources): String = resources.getString(descriptionId)

    fun getColor(resources: Resources) = ResourcesCompat.getColor(resources, colorId, null)

    fun getNameCategory(resources: Resources): String = resources.getString(nameCategoryId)

    companion object {
        fun valueOfCategoryBmi(resources: Resources, value: String): CategoriesBmi {
            for (enum in values()) {
                if (resources.getString(enum.nameCategoryId) == value) {
                    return enum
                }
            }
            throw IllegalArgumentException(
                "No enum const " + CategoriesBmi::class.java + "@nameCategory." + value
            )
        }
    }

}