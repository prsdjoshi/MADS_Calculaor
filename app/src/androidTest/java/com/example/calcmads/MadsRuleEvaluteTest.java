package com.example.calcmads;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.calcmads.calc.MadsRuleEvalute;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MadsRuleEvaluteTest {

    @Test
    public void testmadsRuleEvalute() {
        MadsRuleEvalute madsRuleEvalute = new MadsRuleEvalute();
        float actual = madsRuleEvalute.evaluate("50 + 20 / 10");
        // expected value is 212
        float expected = 7;
        // use this method because float is not precise
        assertEquals("solve expression using MADS rule", expected, actual, 0.001);
    }
    @Test
    public void testmadsRuleEvalute0() {
        MadsRuleEvalute madsRuleEvalute = new MadsRuleEvalute();
        float actual = madsRuleEvalute.evaluate("50 / 20 + 5");
        // expected value is 212
        float expected = 2;
        // use this method because float is not precise
        assertEquals("solve expression using MADS rule", expected, actual, 0.001);
    }
    @Test
    public void testmadsRuleEvalute1() {
        MadsRuleEvalute madsRuleEvalute = new MadsRuleEvalute();
        float actual = madsRuleEvalute.evaluate("25 - 2 * 10");
        // expected value is 212
        float expected = 5;
        // use this method because float is not precise
        assertEquals("solve expression using MADS rule", expected, actual, 0.001);
    }
    @Test
    public void testmadsRuleEvalute2() {
        MadsRuleEvalute madsRuleEvalute = new MadsRuleEvalute();
        float actual = madsRuleEvalute.evaluate("10 / 2 - 20");
        // expected value is 212
        float expected = -15;
        // use this method because float is not precise
        assertEquals("solve expression using MADS rule", expected, actual, 0.001);
    }

}