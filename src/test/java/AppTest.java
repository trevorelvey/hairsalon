import java.util.ArrayList;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Hair Salon");
  }

  @Test
  public void stylistAddedSuccessfully() {
    goTo("http://localhost:4567/");
    fill("#name-stylist").with("Stylist1");
    submit(".new-stylist");
    assertThat(pageSource()).contains("Stylist1");
  }

  @Test
  public void stylistRemoved() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    goTo("http://localhost:4567/");
    click("option", withText("Stylist1"));
    submit(".delete-stylist");
    assertThat(!(pageSource()).contains("Stylist1"));
  }

  @Test
  public void stylistUpdatedSuccessfully() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    goTo("http://localhost:4567/");
    click("option", withText("Stylist1"));
    fill("#newName-stylist").with("Stylist2");
    submit(".update-stylist");
    assertThat(pageSource()).contains("Stylist2");
  }

}
