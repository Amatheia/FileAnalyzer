package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The TokenCountAnalyzer class implements the Analyzer interface and
 * receives tokens from the input file. It counts the number of unique tokens
 * and the number of times it occurred, and outputs a file.
 *
 *@author   April Albaugh
 */

public class TokenCountAnalyzer implements Analyzer {

    private Properties properties;
    private Map<String, Integer> tokenCounts;

    /**
     * Constructor for the TokenCountAnalyzer object. Instantiates a TreeMap.
     */
    public TokenCountAnalyzer()  {
        tokenCounts = new TreeMap<String, Integer>();
    }

    /**
     * Constructor for TokenCountAnalyzer with Properties parameter
     *
     *@param properties Receives the properties from AnalyzeFile
     */
    public TokenCountAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Gets the tokenCounts attribute
     *
     *@return   The tokenCounts value
     */ 
    public Map getTokenCounts() {
        return tokenCounts;
    }

    /**
     *  The processToken method receives String token and adds it
     *  to the TreeSet. Then it counts number of times token occurs.
     *
     *@param token  The token value from the input file
     */
    public void processToken(String token) {

        int count = 1;

        if(tokenCounts.containsKey(token))  {
            count = tokenCounts.get(token);
            tokenCounts.put(token, ++count);
        } else {
            tokenCounts.put(token, count);
        }
    }

    /**
     *  The writeOutputFile method creates the TokenCountAnalyzer report
     *
     *@param inputFilePath  inputFilePath file name
     *
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir")
                + properties.getProperty("output.file.token.count");
        try (PrintWriter writer = 
                new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            for(Map.Entry<String, Integer> entry : tokenCounts.entrySet())  {
                writer.print(entry.getKey());
                writer.print("\t");
                writer.println(entry.getValue());
            }
        } catch (FileNotFoundException fileNotFoundEx) {
            System.out.println("Could not find file");
            fileNotFoundEx.printStackTrace();
        } catch (IOException ioEx) {
            System.out.println("Could not read the file");
            ioEx.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Something bad happened");
            exception.printStackTrace();
        }
    }
 }
