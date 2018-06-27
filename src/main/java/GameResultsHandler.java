import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameResultsHandler {

    private String fileName;

    public GameResultsHandler(String name) {
        fileName = name;
        createFileIfNotPresent(name);
    }

    private void createFileIfNotPresent(String name) {
        File f = new File(name);
        if (f.exists() && !f.isDirectory()) {
            return;
        }
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("File creating error");
                e.printStackTrace();
            }
        }
    }

    public void serializeResultList(List<Score> list) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            stream.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Score> deserializeResultList() {
        List<Score> list;
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileName))) {
                list = (List<Score>) stream.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
