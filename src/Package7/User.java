package Package7;

public class User {
    private String name;
    private String addres;

    public User(String name, String addres) {
        this.name = name;
        this.addres = addres;
    }

    public void displayUser() {
        System.out.println(this.name + " " + this.addres);
    }

    public String getName() {
        return this.name;
    }

    public String getAddres() {
        return this.addres;
    }
}
