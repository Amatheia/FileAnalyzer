package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The UniqueTokenAnalyzer class implements the Analyzer interface and
 * creates a report of all unique tokens.
 *
 *@author   April Albaugh
 */

public class UniqueTokenAnalyzer implements Analyzer {

    private Properties properties;

    private Set<String> uniqueTokensList;

    /**
     * Constructor for the UniqueTokenAnalyzer object. Instantiates a TreeSet.
     */
    public UniqueTokenAnalyzer() { 
        uniqueTokensList = new TreeSet<String>();
    }

    /**
     *  Constructor for UniqueTokenAnalyzer with one Properties parameter
     *
     *@param properties Receives the properties from AnalyzeFile
     */
    public UniqueTokenAnalyzer(Properties properties) {
        this();
        this.properties = properties;
    }

    /**
     * Gets the uniqueTokensList attribute
     *
     *@return   The uniqueTokensList value
     */  
    public Set getUniqueTokensList() {
        return uniqueTokensList;
    }

    /**
     * The processToken method receives String token and adds it
     * to the TreeSet.
     *
     *@param token  The token value from the input file
     */ 
    public void processToken(String token) {
        uniqueTokensList.add(token);
    } 

    /**
     * The writeOutputFile method creates the summary report
     *
     *@param inputFilePath  inputFilePath file name
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir")
                 + properties.getProperty("output.file.unique");
        try (PrintWriter out = 
                new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            for (String tokenWriter : uniqueTokensList) {
                out.println(tokenWriter);
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
