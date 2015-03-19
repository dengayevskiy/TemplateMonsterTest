package com.monster.checks;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.monster.commonMethods.Base;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

public class PositiveLogin {

    @Before
     public void startNewSession() {
        clearBrowserCache();
    }

    @Test
    public void signInButtonIsPresent() {

        Base.openPage("monsterURL");
        $("#signin-link").shouldBe(Condition.present);
    }

    @Test
    public void signInPopupIsShown() {

        Base.openPage("monsterURL");

        $("#signin-link").click();
        $(".sign-in-form").should(Condition.appear);
    }

    @Test
    public void signInPopupIsHidden() {

        Base.openPage("monsterURL");

        $("#signin-link").click();
        $(".sign-in-form").should(Condition.appear);
        $("#signin-link").click();
        $(".sign-in-form").should(Condition.disappear);
    }

    @Test
    public void signInWasSuccessful() {

        Base.openPage("monsterURL");

        Base.signIn("login", "pass");

        $("#your-account-link").shouldHave(Condition.text("Hello, " + Base.locators.getProperty("login")));

        Cookie ck = WebDriverRunner.getWebDriver().manage().getCookieNamed("wac");
        assertEquals("1", ck.getValue());

        $("#signout-link").should(Condition.appear);
    }

    @Test
    public void signOutWasSuccessful() {

        Base.openPage("monsterURL");

        Base.signIn("login", "pass");

        Base.signOut();

        $("#signin-link").should(Condition.appear);
    }

}
