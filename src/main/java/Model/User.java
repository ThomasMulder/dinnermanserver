package Model;

/**
 * Created by Thomas on 17-2-2016.
 */
public class User {
    private int id;
    private String name;
    private String email;

    public User(int id, String name, String email) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
