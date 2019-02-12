package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The BigWordAnalyzer class implements the Analyzer interface and
 * finds unique tokens that are greater than or equal to a specified number. 
 * It outputs the largest tokens in a report.
 *
 *@author   April Albaugh
 */

public class BigWordAnalyzer implements Analyzer {

    private Properties properties;
    private Set<String> bigWords;
    private int minimumWordLength;

    /**
     * Constructor for the BigWordAnalyzer object. Instantiates a TreeSet.
     */
    public BigWordAnalyzer()  {
        bigWords = new TreeSet<String>();
    }

    /**
     *  Constructor for BigWordAnalyzer with Properties parameter
     *
     *@param properties Receives the properties from AnalyzeFile
     */
    public BigWordAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        minimumWordLength = Integer.parseInt(properties.getProperty("bigwords.minimum.length"));
    }

   /**
     * The getBigWords method returns the TreeSet which holds the set 
     * of biggest tokens
     *
     *@return   The bigWords set
     */
    public Set<String> getBigWords()  {
        return bigWords;
    }


    /**
     *  The processToken method processes each token in input file and adds 
     *  them to the TreeSet.
     *
     *@param token Token from the AnalyzeFile class
     */
    public void processToken(String token) {
        if(token.length() >= minimumWordLength)  {
             bigWords.add(token);
        }

    }

    /**
     *  The writeOutputFile method creates the BigWordAnalyzer report
     *
     *@param inputFilePath  inputFilePath file name
     *
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir")
                + properties.getProperty("output.file.bigwords");
        try (PrintWriter output = 
                new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            for (String word : bigWords) {
                output.println(word);
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
