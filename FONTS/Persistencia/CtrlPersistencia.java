package Persistencia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CtrlPersistencia {
    public String llegirArxiu(String nomArxiu) {
        StringBuilder builder = new StringBuilder();
        try {
            Scanner reader = new Scanner(new File(new java.io.File( "." ).getCanonicalPath() + "\\"+ nomArxiu));
            while(reader.hasNextLine()) {
                builder.append(reader.nextLine()).append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString().strip();
    }

    public void escriureArxiu(String nomArxiu, String contingut) {
        try {
            FileWriter writer = new FileWriter(nomArxiu);
            writer.write(contingut);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
