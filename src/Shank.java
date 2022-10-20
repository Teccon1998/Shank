import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Shank {
    
    public static void main(String[] args) throws Exception
    {
        String arg = "C:\\Users\\alexa\\OneDrive\\Desktop\\GitBlame\\311\\Shank\\src\\InputFile.txt";
        Path path = Paths.get(arg);
        // if(args.length != 1)
        // {
        //     System.out.println("Incorrect # of args");
        //     System.exit(0);
        // }
        // Path path = Paths.get(args[0]);

        try
        {
            
            List<String> InputList = Files.readAllLines(path);
            Lexer Lexer = new Lexer(InputList);
            ArrayList<List<Token>> listOfTokenlists = new ArrayList<List<Token>>();
            for(int i = 0; i<InputList.size(); i++)
            {
                try 
                {
                    listOfTokenlists.add(Lexer.lex(InputList.get(i)));
                    System.out.println(listOfTokenlists.get(i));
                } 
                catch (Exception e) 
                {
                    System.out.println("Failed to Lex on InputFile line: " + i); // Iteration location for debugging
                    e.printStackTrace();
                }
            }
            ArrayList<Token> MasterTokenList = new ArrayList<>();
            for(List<Token> list : listOfTokenlists)
            {
                MasterTokenList.addAll(list);
            }
            
            Parser parser = new Parser(MasterTokenList);
            System.out.println("\n" + parser.parseTokens());
        }    
        catch(IOException e)
        {
            System.out.println("Exception occured opening file");
            e.printStackTrace();
        }
    }

    
}