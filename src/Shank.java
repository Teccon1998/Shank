import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

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
            ArrayList<FunctionDefinitionNode> FunctionNodes = new ArrayList<>();
            FunctionNodes = parser.parseTokens();
            HashMap<String, CallableNode> functionHashMap = new HashMap<>();
            //puts all the functionNodes parsed into a hashmap
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                functionHashMap.put(functionDefinitionNode.getFunctionName(), functionDefinitionNode);
            }
            //prints out all the function nodes 
            //(for debug)
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                System.out.println("\n\n" + functionDefinitionNode + "\n");
            }
            //Loops through all the functionDefintionNodes
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                //loops through each DefinitionNode to get its statements
                for (int i = 0; i < functionDefinitionNode.getStatementList().size(); i++) 
                {
                    //checks that each of the statements are FunctionCallNodes
                    if(functionDefinitionNode.getStatementList().get(i).getStatement() instanceof FunctionCallNode)
                    {
                        //Creates a CallableNode to store and work with that node
                        CallableNode functionCallNode;
                        if((functionCallNode = functionHashMap.get(functionDefinitionNode.getFunctionName()))!=null)
                        {
                            //Checks the parametersize of that functionCallNode and checks to see if its equal to the functiondefinition parameter size
                            int functionCallNodeParameterSize = functionCallNode.getParameterVariableNodes().size();
                            if(functionDefinitionNode.getParameterVariableNodes().size() == functionCallNodeParameterSize)
                            {
                                //if its a built in functionNode cast it to a builtinfunctionnode and check if its variadic
                                if(functionCallNode instanceof BuiltInFunctionNode)
                                {
                                    BuiltInFunctionNode builtInFunctionCallNode = (BuiltInFunctionNode) functionCallNode;
                                    if (builtInFunctionCallNode.isVariadic()) {
                                        //if its built in and variadic then get the data types from its variables passed and call the function
                                        ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                                        dataTypes = getDataType(functionCallNode);
                                        //TODO: Interpret? Or Execute?
                                    }

                                }
                                //if its a regular function call then build the data types and pass it to interpreter
                                else if(functionCallNode instanceof FunctionDefinitionNode)
                                {
                                    ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                                    dataTypes = getDataType(functionCallNode);
                                    //Interpret? How do I get the first function? What is the variable hashmap?
                                    System.out.println(dataTypes);
                                }  
                                else
                                {
                                    throw new Exception("Not a recognized function");
                                    
                                }
                                
                            }
                        }
                    }
                }
            }
            
            
        }    
        catch(IOException e)
        {
            System.out.println("Exception occured opening file");
            e.printStackTrace();
        }
    }

    private static ArrayList<InterpreterDataType> getDataType(CallableNode callableNode)
    {
        ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
        for(int i = 0; i <callableNode.getParameterVariableNodes().size(); i++)
        {
            VariableNode.Type Type = callableNode.getParameterVariableNodes().get(i)
                    .getType();
            if (Type.equals(VariableNode.Type.INTEGER)) {
                IntegerNode intNode = (IntegerNode) callableNode
                        .getParameterVariableNodes().get(i).getNode();
                dataTypes.add(new IntDataType(intNode.getNumber()));
            }
            else if (Type.equals(VariableNode.Type.REAL)) {
                FloatNode floatNode = (FloatNode) callableNode
                        .getParameterVariableNodes().get(i).getNode();
                dataTypes.add(new FloatDataType(floatNode.getNumber()));
            }
            else
            {
                return null;
            }
        }
        return dataTypes;
    }
    
}