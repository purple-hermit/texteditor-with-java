import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
    public void saveFile(File file, String text) throws FileNotFoundException{
        PrintWriter fileOut = null;
        try{
            fileOut = new PrintWriter(file);
            fileOut.println(text);
        }finally{
            if(fileOut != null){
                fileOut.close();
            }
        }
    }
    public String openFile(File file) throws FileNotFoundException{
        StringBuilder content = new StringBuilder();
        Scanner fileIn = null;
        try{
            fileIn = new Scanner(file);
            while(fileIn.hasNextLine()){
                content.append(fileIn.nextLine()).append("\n");
            }
        }finally{
            fileIn.close();
        }
        return content.toString();
    }
}
