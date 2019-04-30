package com.example.bmi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UITest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun resultAccuracyDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("65"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val resultTV = onView(withId(R.id.resultBmiTV))
        resultTV.check(matches(withText("22.49")))
    }

    @Test
    fun visibilityTextFieldTest() {
        val massTV = onView(withId(R.id.massTV))
        massTV.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        val heightTV = onView(withId(R.id.heightTV))
        heightTV.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        val countButton = onView(withId(R.id.countButton))
        countButton.check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    @Test
    fun normalCategoryDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("65"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val categoryTV = onView(withId(R.id.categoryTV))
        categoryTV.check(matches(withText(R.string.normal_cat_bmi)))
    }

    @Test
    fun underweightCategoryDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("40"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val categoryTV = onView(withId(R.id.categoryTV))
        categoryTV.check(matches(withText(R.string.underweight_cat_bmi)))
    }

    @Test
    fun overweightCategoryDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("85"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val categoryTV = onView(withId(R.id.categoryTV))
        categoryTV.check(matches(withText(R.string.overweight_cat_bmi)))
    }

    @Test
    fun obeseCategoryDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("100"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val categoryTV = onView(withId(R.id.categoryTV))
        categoryTV.check(matches(withText(R.string.obese_cat_bmi)))
    }

    @Test
    fun extremelyObeseCategoryDisplayTest() {
        val massET = onView(withId(R.id.massET))
        massET.perform(replaceText("120"), closeSoftKeyboard())

        val heightET = onView(withId(R.id.heightET))
        heightET.perform(replaceText("170"), closeSoftKeyboard())

        val countButton = onView(withId(R.id.countButton))
        countButton.perform(click())

        val categoryTV = onView(withId(R.id.categoryTV))
        categoryTV.check(matches(withText(R.string.extremely_obese_cat_bmi)))
    }
}