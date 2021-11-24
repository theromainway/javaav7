package td7;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path p = Paths.get(".");
        try {
            DirMonitor dm = new DirMonitor(p);
            dm.printPath();
            System.out.println(dm.sizeOfFiles());
            System.out.println(dm.mostRecent());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}