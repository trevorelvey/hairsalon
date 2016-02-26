import java.time.LocalDateTime;
import org.junit.Rule;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class StylistTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Stylist.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Stylist firstStylist = new Stylist("Stylist1");
    Stylist secondStylist = new Stylist("Stylist1");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
  public void save_assignsIdToObject() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(myStylist.getId(), savedStylist.getId());
  }

  @Test
  public void find_findsStylistInDatabase_true() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    Stylist savedStylist = Stylist.find(myStylist.getId());
    assertTrue(myStylist.equals(savedStylist));
  }

  @Test
  public void update_updatesChangeNameOfStylistsInDatabase() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();
    myStylist.update("Stylist2");
    assertEquals("Stylist2", Stylist.find(myStylist.getId()).getName());
  }

  public void delete_deletesStylistFromDatabase() {
    Stylist firstStylist = new Stylist("Stylist1");
    Stylist secondStylist = new Stylist("Stylist2");
    firstStylist.save();
    secondStylist.save();
    firstStylist.delete();
    assertEquals(1, Stylist.all().size());
  }

  @Test
  public void getClients_returnsAllClientsWithSameStylist() {
    Stylist myStylist = new Stylist("Stylist1");
    myStylist.save();

    Client firstClient = new Client("Client1");
    Client secondClient = new Client("Client2");
    firstClient.save();
    secondClient.save();

    firstClient.assignStylist(myStylist.getId());
    secondClient.assignStylist(myStylist.getId());

    Client[] clients = new Client[] {firstClient, secondClient};
    assertTrue(myStylist.getClients()
                        .containsAll(Arrays.asList(clients)));
  }
}
