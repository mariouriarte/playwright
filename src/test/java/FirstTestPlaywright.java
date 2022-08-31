import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.Assert;
import org.junit.Test;

public class FirstTestPlaywright {

    private Page login(Playwright playwright) {

        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100)).newPage();

        page.setViewportSize(1200, 600);
        page.navigate("https://www.saucedemo.com/");

        page.type("id=user-name", "standard_user");
//        page.type("id=password", "secret_sauce");
        page.locator("id=password").fill("secret_sauce");
        page.click("id=login-button");

        return page;
    }

    @Test
    public void verificarQueItemsPuedanSerAgregadosAlCarrito() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            String textElement = page.innerText("a#item_4_title_link div");

            page.click("id=add-to-cart-sauce-labs-backpack");
            page.click("a.shopping_cart_link");

            String carTextElement = page.innerText("div.cart_item_label a#item_4_title_link  div");

            Assert.assertTrue(textElement.equals(carTextElement));
        }
    }

    @Test
    public void verificarCheckoutDeItemsSeleccionados() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            String textElement = page.innerText("a#item_4_title_link div");

            page.click("id=add-to-cart-sauce-labs-backpack");
            page.click("a.shopping_cart_link");

            String carTextElement = page.innerText("div.cart_item_label a#item_4_title_link  div");

            Assert.assertTrue(textElement.equals(carTextElement));

            page.click("id=checkout");

            page.type("id=first-name", "Mario Arturo");
            page.type("id=last-name", "Uriarte");
            page.type("id=postal-code", "00001");

            page.click("id=continue");
            page.click("id=finish");
        }
    }

    @Test
    public void verificarListadoDeItemsEnElHomePage() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            // Order A to Z
            String textElement = page.innerText("div.inventory_item:first-child div.inventory_item_label a");
            String firstElementAtoZ = "Sauce Labs Backpack";
            Assert.assertTrue(textElement.equals(firstElementAtoZ));

            textElement = page.innerText("div.inventory_item:last-child div.inventory_item_label a");
            String lastElementAtoZ = "Test.allTheThings() T-Shirt (Red)";
            Assert.assertTrue(textElement.equals(lastElementAtoZ));

            // Order Z to A
            page.selectOption("select.product_sort_container", "za");

            textElement = page.innerText("div.inventory_item:first-child div.inventory_item_label a");
            String firstElementZtoA = "Test.allTheThings() T-Shirt (Red)";
            Assert.assertTrue(textElement.equals(firstElementZtoA));

            textElement = page.innerText("div.inventory_item:last-child div.inventory_item_label a");
            String lastElementZtoA = "Sauce Labs Backpack";
            Assert.assertTrue(textElement.equals(lastElementZtoA));

            // Order low to hi
            page.selectOption("select.product_sort_container", "lohi");

            textElement = page.innerText("div.inventory_item:first-child div.inventory_item_label a");
            String firstElementLowToHi = "Sauce Labs Onesie";
            Assert.assertTrue(textElement.equals(firstElementLowToHi));

            textElement = page.innerText("div.inventory_item:last-child div.inventory_item_label a");
            String lastElementLowToHi = "Sauce Labs Fleece Jacket";
            Assert.assertTrue(textElement.equals(lastElementLowToHi));

            // Order hi to low
            page.selectOption("select.product_sort_container", "hilo");

            textElement = page.innerText("div.inventory_item:first-child div.inventory_item_label a");
            String firstElementHiToLow = "Sauce Labs Fleece Jacket";
            Assert.assertTrue(textElement.equals(firstElementHiToLow));

            textElement = page.innerText("div.inventory_item:last-child div.inventory_item_label a");
            String lastElementHiToLow = "Sauce Labs Onesie";
            Assert.assertTrue(textElement.equals(lastElementHiToLow));
        }
    }

    @Test
    public void removerItemsSeleccionadosParaElCarritoCompras() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            page.click("id=add-to-cart-sauce-labs-backpack");

            String removeButton = page.innerText("id=remove-sauce-labs-backpack");
            Assert.assertTrue("REMOVE".equals(removeButton));

            page.click("id=remove-sauce-labs-backpack");

            String addCartButton = page.innerText("id=add-to-cart-sauce-labs-backpack");
            Assert.assertTrue("ADD TO CART".equals(addCartButton));
        }
    }

    @Test
    public void verificarRedSocialTwitter() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            String href = page.getAttribute("li.social_twitter a", "href");
            page.navigate(href);

            Assert.assertTrue("https://twitter.com/saucelabs".equals(page.url()));
        }
    }

    @Test
    public void verificarRedSocialFacebook() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            String href = page.getAttribute("li.social_facebook a", "href");
            page.navigate(href);

            Assert.assertTrue("https://www.facebook.com/saucelabs".equals(page.url()));
        }
    }

    @Test
    public void verificarRedSocialLinkedin() {
        try (Playwright playwright = Playwright.create()) {

            Page page = login(playwright);

            String href = page.getAttribute("li.social_linkedin a", "href");
            page.navigate(href);

            Assert.assertTrue("https://www.linkedin.com/company/sauce-labs/".equals(page.url()));
        }
    }

    @Test
    public void verificarBotonesDeRedesSociales() {
        verificarRedSocialTwitter();
        verificarRedSocialFacebook();
        verificarRedSocialLinkedin();
    }
}
