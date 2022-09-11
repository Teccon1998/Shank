import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Shank {
    
    public static void main(String[] args) throws IOException
    {
        String arg = "C:\\Users\\alexa\\OneDrive\\Desktop\\GitBlame\\311\\Lexer\\src\\InputFile.txt";
        // if(args.length != 1)
        // {
        //     System.out.println("Incorrect # of args");
        // }
        Path path = Paths.get(arg);
        try
        {
            List<String> InputList = Files.readAllLines(path);
            Lexer Lexer = new Lexer(InputList);
            
            for(int i = 0; i<InputList.size(); i++)
            {
                if (i == 4)
                {
                    String temp = "neigh";
                }
                try
                {

                    System.out.println(Lexer.lex(InputList.get(i)));
                }
                catch(Exception e)
                {
                    System.out.println("Failed to Lex on InputFile line: " + i); // Iteration location for debugging
                    e.printStackTrace();
                }   
            }
        }    
        catch(IOException e)
        {
            System.out.println("Exception occured opening file");
            e.printStackTrace();
        }
    }

    
}
