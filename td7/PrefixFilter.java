package td7;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;

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