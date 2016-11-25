package tomasulo.util.filesystem;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {

    public String[] readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.FileReader(path));
        StringBuilder sb = new StringBuilder();
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

        } finally {
            br.close();
        }
        return sb.toString().split("\n");

    }

}
