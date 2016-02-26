import java.util.List;
import org.sql2o.*;

public class Client {
  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Client(String name) {
    this.name = name;
  }

  public static List<Client> all() {
    String sql = "Select id, name FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }
}
