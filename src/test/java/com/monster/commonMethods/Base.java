package com.monster.commonMethods;

import com.codeborne.selenide.Condition;

import java.io.InputStream;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class Base {

    public static final Properties locators = loadProperties("/common.properties");

    public static Properties loadProperties(String path) {

        Properties locators = new Properties();
        InputStream is = Base.class.getResourceAsStream(path);
        try {
            locators.load(is);
            return locators;
        } catch (Exception e) {
            throw new RuntimeException("Error load properties " + path, e);
        }
    }

    public static void openPage(String url) {

        open(locators.getProperty(url));
    }


    public static void signIn(String email, String password) {

        $("#signin-link").click();
        $("#signin-form-email").val(locators.getProperty(email));
        $("#signin-form-password").val(locators.getProperty(password));
        $("#signin-form-submit").click();
    }

    public static void signOut() {

        $("#signout-link").shouldBe(Condition.present).click();
    }
}
