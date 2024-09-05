import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class Google3Test extends BaseTest {

    @Test
    void searchInGoogle() {
        open("https://google.com");
        System.out.println("GT3" + Selenide.sessionId());
        $x(".//div[@Class='123']").should(Condition.visible);
    }
}
