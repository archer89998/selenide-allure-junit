import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

class Google1Test extends BaseTest {

    @Test
    void searchInGoogle() {
        open("/");
        System.out.println("GT1" + Selenide.sessionId());
        $x(".//div[@Class='121']").should(Condition.visible);
    }
}
