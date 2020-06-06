package Package7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ChatStorage {
    private ArrayList<User> users = new ArrayList(10);

    public ChatStorage() {
        this.readData();
    }

    private void readData() {
        try {
            File file = new File("Users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            while((line = reader.readLine()) != null) {
                String name = line;
                line = reader.readLine();
                this.users.add(new User(name, line));
            }

            reader.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public String CollectData() {
        String res=" ";
        try {
            File file = new File("Users.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null) {
                res = res + line + System.getProperty("line.separator");
            }

            reader.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }
        return res;
    }

    public void saveData(String Name, String ipNumber) {
        try {
            File file = new File("Users.txt");
            FileWriter writer=new FileWriter("Users.txt",true);
            writer.append(Name + System.getProperty("line.separator"));
            writer.append(ipNumber + System.getProperty("line.separator"));
            this.users.add(new User(Name, ipNumber));
            writer.close();
        }
        catch (IOException var2) {
            System.out.println("File not found");
            var2.printStackTrace();
        }

    }

    public User getUser(String findName) {
        Iterator var3 = this.users.iterator();

        while(var3.hasNext()) {
            User user = (User)var3.next();
            if (findName.equals(user.getName())) {
                return user;
            }
        }

        return null;
    }
}
