import java.util.List;
import org.sql2o.*;

public class Client {
  private int id;
  private String name;
  private int stylist_id;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Client(String name) {
    this.name = name;
  }


  public int getStylistId() {
    return stylist_id;
  }

  @Override
  public boolean equals(Object otherClient) {
    if(!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getName().equals(newClient.getName()) &&
        this.getId() == newClient.getId();
    }
  }

  public static List<Client> all() {
    String sql = "SELECT id, name, stylist_id FROM clients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO clients (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM clients where id=:id";
      Client client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return client;
    }
  }

  public void update(String newName) {
    this.name = newName;
    String sql = "UPDATE clients SET name = :newName WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("newName", newName)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  public void delete() {
    String sql = "DELETE FROM clients WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  public void assignStylist(int stylist_id) {
    stylist_id = stylist_id;
    String sql = "UPDATE clients SET stylist_id = :stylist_id WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
         .addParameter("stylist_id", stylist_id)
         .addParameter("id", this.id)
         .executeUpdate();
    }
  }

  public String getStylistName() {
    return Stylist.find(stylist_id).getName();
  }
}
