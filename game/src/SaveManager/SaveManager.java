package SaveManager;

import java.io.*;

/** This Class is Responsable for writing and reading the save file */
public class SaveManager implements Serializable {

    /**
     * Methode to wirte a GameSave into a file
     *
     * @param save The GameSave that will be saved
     * @param path the Path to the File
     */
    public static void writeObject(GameSave save, String path)
            throws IOException, ClassNotFoundException {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(save);
        oos.close();
    }

    /**
     * Methode to read a save File into a GameSave object
     *
     * @param path the Path to the File
     * @return returns A GameSave object white the old Game variables
     */
    public static GameSave readObject(String path) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameSave save = (GameSave) ois.readObject();
        ois.close();
        return save;
    }
}
