package xieliangji.automation.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.awt.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BeforeAfterActions {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private BrowserType.LaunchOptions getLaunchOptions(String browserType) {
        switch (browserType) {
            default -> {
                return new BrowserType.LaunchOptions().setHeadless(false);
            }
        }
    }

    @BeforeAll
    public void setup() {
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        playwright = Playwright.create();
        String browserType = System.getenv("BROWSER_TYPE");
        browserType = browserType == null ? "" : browserType;
        switch (browserType) {
            case "webkit" -> browser = playwright.webkit().launch(getLaunchOptions(browserType));
            case "firefox" -> browser = playwright.firefox().launch(getLaunchOptions(browserType));
            default -> browser = playwright.chromium().launch(getLaunchOptions(browserType));
        }
        context = browser.newContext(
                new Browser.NewContextOptions().setViewportSize(screenDimension.width, screenDimension.height));
        page = context.newPage();
    }

    @AfterAll
    public void teardown() {
        page.close();
        context.close();
        browser.close();
        playwright.close();
    }
}
