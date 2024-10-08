import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeEach;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class BaseTest {

    @BeforeEach
    void setupAllure() {
        Configuration.baseUrl = "https://google.com";
        Configuration.browser = "firefox";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        setAllureEnvironment();
    }

    void setAllureEnvironment() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", Configuration.browser)
                        .put("URL", Configuration.baseUrl)
                        .build(), System.getProperty("user.dir")
                        + "/allure-results/");

    }

    public static void allureEnvironmentWriter(ImmutableMap<String, String> environmentValuesSet,
                                               String customResultsPath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element environment = doc.createElement("environment");
            doc.appendChild(environment);
            environmentValuesSet.forEach((k, v) -> {
                Element parameter = doc.createElement("parameter");
                Element key = doc.createElement("key");
                Element value = doc.createElement("value");
                key.appendChild(doc.createTextNode(k));
                value.appendChild(doc.createTextNode(v));
                parameter.appendChild(key);
                parameter.appendChild(value);
                environment.appendChild(parameter);
            });

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File allureResultsDir = new File(customResultsPath);
            if (!allureResultsDir.exists()) allureResultsDir.mkdirs();
            StreamResult result = new StreamResult(
                    new File(customResultsPath + "environment.xml"));
            transformer.transform(source, result);
            System.out.println("Allure environment data saved.");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}
