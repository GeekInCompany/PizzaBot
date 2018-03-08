package de.geekincompany.pizzakurier.browser;

import de.geekincompany.pizzakurier.Main;
import de.geekincompany.pizzakurier.model.Ingredients;
import de.geekincompany.pizzakurier.model.Order;
import de.geekincompany.pizzakurier.model.OrderObject;
import de.geekincompany.pizzakurier.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Browser {
    public static String BASE_URL = "https://www.bringbutler.de/cgi-bin/Shop.pl?SHOP=KurierKarlshuld&FILIALENNUMMER=0&PLZPOID=&RefererID=Engine-google&Frame=1%22";
    private WebDriver browser;

    public Browser() {
        /*
        this.browser = new FirefoxDriver();
        /*/
        this.browser = new HtmlUnitDriver(true);
        //*/
    }

    public boolean order(Order order) {
        browser.get(BASE_URL);
        for (OrderObject obj : order.getObjects()) {
            add(obj);
            if (obj.getIngredients().size() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                modify(order, obj);
            }
        }

        browser.findElement(By.id(order.getAdress().getType().getId())).click();
        browser.findElement(By.name("Vorname")).sendKeys(order.getAdress().getName());
        browser.findElement(By.name("Nachname_m3")).sendKeys(order.getAdress().getSname());
        browser.findElement(By.name("Strasse_m5")).sendKeys(order.getAdress().getStreet());
        browser.findElement(By.name("TelefonVorwahl_pgm")).sendKeys(order.getAdress().getPhone().substring(0,5));
        browser.findElement(By.name("TelefonNummer_pgm")).sendKeys(order.getAdress().getPhone().substring(5));
        browser.findElement(By.name("EMailAdresse_E")).sendKeys(order.getAdress().getEmail());
        browser.findElement(By.name("Bemerkungen")).sendKeys(Main.USER_AGENT);
        browser.findElement(By.xpath("//option[@value='Ich bin Stammkunde']")).click();
        browser.findElement(By.id("SpoidField")).sendKeys(String.valueOf(order.getAdress().getZip()));
        while(browser.findElements(By.cssSelector("a.searchajax")).size() == 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        browser.findElements(By.cssSelector("a.searchajax")).get(0).click();
        System.out.println("NEW PLz = "+browser.findElement(By.id("PLZOrtTxT")).getText());

        browser.findElement(By.id("WeiterButtonInfo")).click();
        if (order.isTakeaway()) {
            browser.findElement(By.xpath("//label[@for='SelbstabholerCheck']")).click();
        }

        //TODO Basket Validate

        boolean emailCheck = browser.findElement(By.id("EMailKopieCheck")).isSelected();
        boolean takeawayCheck = browser.findElement(By.id("SelbstabholerCheck")).isSelected();
        if (!emailCheck) {
            browser.findElement(By.id("EMailKopieCheck")).click();
            emailCheck = browser.findElement(By.id("EMailKopieCheck")).isSelected();
            if (!emailCheck) {
                return false;
            }
        }

        if (takeawayCheck != order.isTakeaway()) {
            browser.findElement(By.xpath("//label[@for='SelbstabholerCheck']")).click();
            takeawayCheck = browser.findElement(By.id("SelbstabholerCheck")).isSelected();
            if (takeawayCheck != order.isTakeaway()) {
                return false;
            }
        }
        //browser.findElement(By.id("ZahlungspflichtigBestellen")).click();
        order.setSubmitted(true);
        return true;
    }

    private void add(OrderObject o) {
        add(o.getProduct(), o.getVariation());
    }

    private void add(Product p, int v) {
        ((JavascriptExecutor) browser).executeScript(p.getVariationJS(v));
    }

    private void modify(Order order, OrderObject obj) {
        int i = 0;
        int internalVariationNo = obj.getProduct().getVariationNumber(obj.getVariation()) + 1;
        for (OrderObject orderObject : order.getObjects()) {
            if (orderObject.getProduct().getId().equals(obj.getProduct().getId()) && orderObject.getVariation() == obj.getVariation()) {
                i++;
                if (orderObject == obj)
                    break;
            }
        }
        //MOver225_1_3
        //MOver${VariationNo}_${variation}_${index}
        //MOver229_3_6
        String mOverElem = "MOver" + internalVariationNo + "_" + obj.getVariation() + "_" + i;
        while (browser.findElements(By.id(mOverElem)).size() == 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        browser.findElement(By.id(mOverElem)).click();

        for (HashMap.Entry<Ingredients, Integer> entry : obj.getIngredients().entrySet()) {
            //A_225_1_3_1_31
            //A_229_3_6_1_19
            //A_${VariationNo}_${variation}_${index}_ZUTATENGRUPENNUMMER(1)_${ZUTATENID}
            int zutat = entry.getKey().ordinal();
            int zValue = entry.getValue();
            if (zValue > 2) zValue = 2;
            if (zValue < 0) zValue = 0;
            String zutatTableID = "A_" + internalVariationNo + "_" + obj.getVariation() + "_" + i + "_1_" + zutat;
            while (browser.findElements(By.id(zutatTableID)).size() == 0) {
                try {
                    Thread.sleep(100);
                    browser.findElement(By.id(mOverElem)).click();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            boolean cZValue = browser.findElement(By.cssSelector("#" + zutatTableID + " .FuncBtn.FuncBtnPopup")).getText().equals((zValue > 0 ? zValue + "x" : ""));
            while (!cZValue) {
                browser.findElement(By.id(zutatTableID)).click();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cZValue = browser.findElement(By.cssSelector("#" + zutatTableID + " .FuncBtn.FuncBtnPopup")).getText().equals((zValue > 0 ? zValue + "x" : ""));
            }
        }
    }

    public ArrayList<Product> getProducts() {
        String productPattern = "^(\\w*)\\)";
        ArrayList<Product> productArrayList = new ArrayList<>();
        browser.get(BASE_URL);
        List<WebElement> elements = browser.findElements(By.cssSelector("td.PN"));
        Pattern p = Pattern.compile(productPattern);
        for (WebElement e : elements) {
            Matcher m = p.matcher(e.getText());
            if (!m.find())
                continue;
            String i = m.group(1);
            switch (i) {
                case "Mo":
                case "Di":
                case "Mi":
                case "Do":
                case "Fr":
                    break;
                default:
                    WebElement element = browser.findElement(By.xpath("//td[text()='" + i + ")']/parent::tr"));
                    if (element == null)
                        break;

                    String name = element.findElement(By.cssSelector("td.PT")).getText().split("\n")[0];
                    if (name.equals(""))
                        break;
                    ArrayList<String[]> variations = new ArrayList<>();
                    List<WebElement> elementPMs = element.findElements(By.cssSelector("td.PM"));
                    List<WebElement> elementPPs = element.findElements(By.cssSelector("td.PP"));
                    for (int j = 0; j < elementPMs.size(); j++) {
                        WebElement elementPM = elementPMs.get(j);
                        WebElement elementPP = elementPPs.get(j);

                        if (elementPM.findElements(By.cssSelector("table")).size() != 0) {
                            String price = elementPP.getText().split("\n")[0];
                            String onClick = elementPM.findElement(By.cssSelector("div.FuncBtn:first-child")).getAttribute("onclick");
                            variations.add(new String[]{price, onClick});
                        }
                    }
                    Product prod = new Product(i, name, variations.toArray(new String[0][0]));
                    productArrayList.add(prod);
            }
        }
        return productArrayList;
    }

    public void close() {
        browser.close();
        browser.quit();
    }
}
