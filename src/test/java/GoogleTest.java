import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

class GoogleTest {

    @BeforeEach
    void setupAllure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @Test
    void searchInGoogle() {
        open("https://google.com");
        $x(".//div[@Class='121']").should(Condition.visible);
    }
}
