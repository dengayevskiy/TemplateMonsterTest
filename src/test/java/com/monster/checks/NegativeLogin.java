package com.monster.checks;


import com.codeborne.selenide.Condition;
import com.monster.commonMethods.Base;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;

public class NegativeLogin {

    @Before
    public void startNewSession() {
        clearBrowserCache();
    }

    @Test
    public void noPassword() {

        Base.openPage("monsterURL");

        Base.signIn("login", "empty");

        $(".popover-content").shouldHave(Condition.text("Please enter Password")).should(Condition.appear);
    }

    @Test
    public void notValidPassword() {

        Base.openPage("monsterURL");

        Base.signIn("login", "notValidPass");

        $(".alert").shouldHave(Condition.text("Password doesn't match")).should(Condition.appear);
    }

    @Test
    public void noEmail() {

        Base.openPage("monsterURL");

        Base.signIn("empty", "empty");

        $(".popover-content").shouldHave(Condition.text("Please enter Email")).should(Condition.appear);
    }

    @Test
    public void notValidEmail() {

        Base.openPage("monsterURL");

        Base.signIn("notValidLogin", "empty");

        $(".popover-content").shouldHave(Condition.text("Doesn't look like a valid email")).should(Condition.appear);
    }

    @Test
    public void notRegisteredAccount() {

        Base.openPage("monsterURL");

        Base.signIn("notRegisteredAcc", "notRegisteredPass");

        $(".alert").shouldHave(Condition.text("No account under such email")).should(Condition.appear);
    }

}
