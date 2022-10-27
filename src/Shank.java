import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Shank {
    
    public static void main(String[] args) throws Exception
    {
        String arg = "C:\\Users\\alexa\\OneDrive\\Desktop\\311\\Shank\\src\\InputFile.txt";
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
            ArrayList<String> notPermittedNames = new ArrayList<>();
            String[] notPermittedList = new String[] {"read", "write", "getRandom", "integerToReal", "realToInteger","squareRoot"};
            notPermittedNames.addAll(Arrays.asList(notPermittedList));
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                if (notPermittedNames.contains(functionDefinitionNode.getFunctionName()))
                {
                    throw new Exception("Cannot overwrite built in functions");
                }
                functionHashMap.put(functionDefinitionNode.getFunctionName(), functionDefinitionNode);
            }
            functionHashMap.put("read", new Read("read", new ArrayList<VariableNode>(), true));
            functionHashMap.put("write", new Write("write", new ArrayList<VariableNode>(), true));
            functionHashMap.put("getRandom", new getRandom());
            functionHashMap.put("integerToReal", new integerToReal());
            functionHashMap.put("realToInteger", new realToInteger());
            functionHashMap.put("squareRoot", new squareRoot());
            
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                System.out.println("\n\n " + functionDefinitionNode + "\n");
            }
            Interpreter.functionsHashmap = functionHashMap;
            //Will check every function parsed to find start functionNode
            for (FunctionDefinitionNode functionDefinitionNode : FunctionNodes) {
                if(functionDefinitionNode.getFunctionName().equals("start"))
                {
                    ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                    ArrayList<ParameterNode> parameterNodes = new ArrayList<>();
                    Interpreter.InterpretFunction(new FunctionCallNode("start", parameterNodes), dataTypes);
                    // System.out.println(Interpreter.VariableHashMap);
                }
            }


            // for(FunctionDefinitionNode functionDefinitionNode : FunctionNodes)
            // {
            //     for(int i = 0; i <functionDefinitionNode.getStatementList().size(); i++)
            //     {
            //         if(functionDefinitionNode.getStatementList().get(i).getStatement() instanceof FunctionCallNode)
            //         {
            //             FunctionCallNode functionCallNode = (FunctionCallNode) functionDefinitionNode.getStatementList().get(i).getStatement();
                        
            //             if(functionCallNode == null)
            //             {
            //                 throw new Exception("Undefined function");
            //             }
            //             CallableNode variaticNode = functionHashMap.get(functionCallNode.getFunctionName());
            //             if(variaticNode.getFunctionName().equals("read") || variaticNode.getFunctionName().equals("write"))
            //             {
                            
            //             }
            //             else if(functionCallNode.getParameterNodes().size() == functionHashMap.get(functionCallNode.getFunctionName()).getParameterVariableNodes().size())
            //             {
            //                 ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
            //                 for (int j = 0; j < functionCallNode.getParameterNodes().size();j++)
            //                 {
            //                     for (int k = 0; k < functionDefinitionNode.getLocalVariablesList().size(); k++) {
            //                         if (functionCallNode.getParameterNodes() == null) {
            //                             break;
            //                         }
            //                         if (functionCallNode.getParameterNodes().get(j).getVarRefNode().getVariableName()
            //                                 .equals(functionDefinitionNode.getLocalVariablesList().get(k)
            //                                         .getVariableName())) {
            //                             if (functionDefinitionNode.getLocalVariablesList().get(k).getType()
            //                                     .equals(VariableNode.Type.INTEGER)) {
            //                                 int value = ((IntegerNode) functionDefinitionNode.getLocalVariablesList()
            //                                         .get(k).getNode()).getNumber();
            //                                 dataTypes.add(new IntDataType(value));
            //                                 break;
            //                             } else if (functionDefinitionNode.getLocalVariablesList().get(k).getType()
            //                                     .equals(VariableNode.Type.REAL)) {
            //                                 float value = ((FloatNode) functionDefinitionNode.getLocalVariablesList()
            //                                         .get(k).getNode()).getNumber();
            //                                 dataTypes.add(new FloatDataType(value));
            //                                 break;
            //                             }
            //                         }
            //                     }
            //                 }
                            
                        
                        
                        
            //         }
            //     }
            // }
            
        }    
        catch(IOException e)
        {
            System.out.println("Exception occured opening file");
            e.printStackTrace();
        }
    }
    
}