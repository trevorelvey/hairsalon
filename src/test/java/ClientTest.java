import java.time.LocalDateTime;
import org.junit.Rule;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class ClientTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst(){
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Client firstClient = new Client("Client1");
    Client secondClient = new Client("Client1");
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    Client myClient = new Client("Client1");
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }
}
