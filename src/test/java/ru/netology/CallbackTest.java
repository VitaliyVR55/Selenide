package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CallbackTest {
    private String makeDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Омск");
        String bookedDate = makeDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME));
        $("[data-test-id='date'] input").sendKeys(bookedDate);
        $("[data-test-id=name] input").setValue("Васильев Вася");
        $("[data-test-id=phone] input").setValue("+79271234567");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно забронирована на " + bookedDate));
    }
}
