package com.monster.checks;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.monster.commonMethods.Base;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Cookie;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;

public class Purchase {

    @Before
    public void startNewSession() {
        clearBrowserCache();
    }

    @Test
    public void purchaseSuccessful() {

        Base.openPage("purchaseURL");

        $(byAttribute("data-variant", "virtual2")).shouldNotHave(Condition.cssClass("collapsed"));

        String[] prices = $$(".purchase-options .js-price").getTexts();
        String[] names = $$(".panel-title>h4").getTexts();

        $$(".js-button-add-to-cart").find(Condition.visible).click();

        $$(".cart-summary-item .js-popup-cart-item").find(Condition.visible).should(Condition.appear);
        $$(".cart-summary-item .js-popup-cart-item").find(Condition.visible).shouldHave(Condition.text(names[1]));

        $(".cart-summary .subtotal-price .price").should(Condition.appear);
        $(".cart-summary .subtotal-price .price").shouldHave(Condition.text(prices[1]));
        $(".cart-summary .template-price").shouldHave(Condition.text(prices[1]));

        $(".cart-summary-item .row").hover();
        $$(".js-edit-license").find(Condition.visible).should(Condition.appear).click();

        $(".modal-body .selected").should(Condition.appear);
        $$(".item .js-txt-price").get(0).shouldHave(Condition.text(prices[0]));
        $$(".item .js-txt-price").get(1).shouldHave(Condition.text(prices[1]));
        $$(".item .js-txt-price").get(2).shouldHave(Condition.text(prices[2]));
        $$(".item .js-txt-price").get(3).shouldHave(Condition.text(prices[3]));

        $$(".js-license .block-heading").get(0).shouldHave(Condition.text(names[0]));
        $$(".js-license .block-heading").get(1).shouldHave(Condition.text(names[1]));
        $$(".js-license .block-heading").get(2).shouldHave(Condition.text(names[2]));
        $$(".js-license .block-heading").get(3).shouldHave(Condition.text(names[3]));

        $$(".js-license .block-heading").get(0).click();
        $(".js-license-popup-button").click();

        $$(".cart-summary-item .js-popup-cart-item").find(Condition.visible).should(Condition.appear);
        $$(".cart-summary-item .js-popup-cart-item").find(Condition.visible).shouldHave(Condition.text(names[0]));

        $(".cart-summary .subtotal-price .price").should(Condition.appear);
        $(".cart-summary .subtotal-price .price").shouldHave(Condition.text(prices[0]));
        $(".cart-summary .template-price").shouldHave(Condition.text(prices[0]));

        $$(".js-add-offer").get(7).click();
        $$(".js-cart-summary .js-popup-cart-item .offer-title").find(Condition.visible).shouldHave(Condition.text("SEO Boost"));
        $(".js-cart-summary .subtotal-price .price").shouldHave(Condition.text("$134"));
        $(".js-cart-summary .template-price").shouldHave(Condition.text("$134"));

        Cookie ck1 = WebDriverRunner.getWebDriver().manage().getCookieNamed("sci_count");
        assertEquals("2", ck1.getValue());
        Cookie ck2 = WebDriverRunner.getWebDriver().manage().getCookieNamed("sca_value");
        assertEquals("134", ck2.getValue());
        Cookie ck3 = WebDriverRunner.getWebDriver().manage().getCookieNamed("sc_summary");

        byte[] valueDecoded = Base64.decodeBase64(ck3.getValue());

        $(".js-checkout-button").click();
        String title1 = WebDriverRunner.getWebDriver().getTitle();
        assertEquals("TemplateMonster.com: Signin", title1);
        $(".js-new-customer-link").click();
        String title2 = WebDriverRunner.getWebDriver().getTitle();
        assertEquals("TemplateMonster.com: Billing Address", title2);

        DateFormat dateFormat = new SimpleDateFormat("MMddHHmmSS");
        Date date = new Date();
        String FinalDate = dateFormat.format(date);

        $("#billingInfo-form-name").val("Test User");
        $("#billingInfo-form-email").val(FinalDate + "@tester.devoffice.com");
        $("#billingInfo-form-address").val("12 street");
        $("#billingInfo-form-city").val("New York");
        //$("#billingInfo_form_country_chosen").val("United States");
        $("#billingInfo_form_state_chosen").val("New York");
        $("#billingInfo-form-postalcode").val("7777");
        $("#billingInfo-form-phone").val("7777777");

        $("#billingInfo-form-submit").click();
    }
}