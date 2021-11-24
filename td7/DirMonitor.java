package td7;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class DirMonitor {

    private Path path;
    
    public DirMonitor(Path path) throws IOException {
        if(!Files.isReadable(path) && !Files.isDirectory(path))
            throw new IOException("Path inutilisable");
        this.path = path;
    }

    public void printPath() throws IOException {
        new MyAction(){

            @Override
            public void perform(Path p) throws IOException {
                long minSize = 1500;
                Iterator<Path> it = Files.newDirectoryStream(path, new DirectoryStream.Filter<Path>(){

                    @Override
                    public boolean accept(Path entry) throws IOException {
                        return entry.toFile().length() >= minSize && entry.endsWith("txt");
                    }
                    
                }).iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
            }
        };

        
    }

    public long sizeOfFiles() throws IOException{
        /*long size = 0;
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for(Path p : stream)
            size += p.toFile().length();
        return size;*/
        return new CountSize().sizeOfFiles(path);
    }

    public File mostRecent() throws IOException {
        /*long modified = 0;
        File f = null;
        DirectoryStream<Path> stream = Files.newDirectoryStream(path);
        for(Path p : stream) {
            if(modified == 0) {
                f = p.toFile();
                modified = f.lastModified();
            }
            if(f.lastModified() < modified) {
                f = p.toFile();
                modified = f.lastModified();
            }
        }
        return f;*/

        return new MostRecent().mostRecent(path);
    }

    public class PrefixFilter implements DirectoryStream.Filter<Path> {

        private long size;

        public PrefixFilter(long n) {
            size = n;
        }
        @Override
        public boolean accept(Path entry) throws IOException {
            return entry.toFile().length() >= size;
        }
        
    }
    
    public class CountSize implements MyAction {

        private long size;

        public long sizeOfFiles(Path p) throws IOException {
            this.perform(p);
            return size;
        }

        @Override
        public void perform(Path p) throws IOException {
            long size = 0;

            DirectoryStream<Path> stream = Files.newDirectoryStream(p);
            for(Path p1 : stream)
                size += p1.toFile().length();

            this.size = size;
        }
        
    }

    public class MostRecent implements MyAction {

        File f;

        public File mostRecent(Path p) throws IOException {
            this.perform(p);
            return f;
        }

        @Override
        public void perform(Path p) throws IOException {
            long modified = 0;
            File f = null;
            DirectoryStream<Path> stream = Files.newDirectoryStream(p);
            for(Path p1 : stream) {
                if(modified == 0) {
                    f = p1.toFile();
                    modified = f.lastModified();
                }
                if(f.lastModified() < modified) {
                    f = p1.toFile();
                    modified = f.lastModified();
                }
            }
            this.f = f;
        }
        
    }
}