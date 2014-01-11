package br.com.eduardo.loan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

@RunWith(JUnit4.class)
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity_> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity_.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Test
    public void openSettings() throws Exception {

        solo.clickOnActionBarItem(R.id.menu_settings);

        solo.clickOnActionBarHomeButton();

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}