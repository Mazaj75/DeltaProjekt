package person;

public class AccountOwner {

    private String uuid;
    private String name;
    private String lastName;

    public AccountOwner(String name, String lastName){
        this.uuid = "...";
        this.name = name;
        this.lastName = lastName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
