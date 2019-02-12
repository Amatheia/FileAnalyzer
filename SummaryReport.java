package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The SummaryReport class implements the Analyzer interface and
 * creates a summary report
 *
 *@author   April Albaugh
 */

public class SummaryReport implements Analyzer {

    private Properties properties;    
    private int totalTokensCount;

    /**
     * Constructor for the SummaryReport object
     */
    public SummaryReport() {

    }

    /**
     *  Constructor for SummaryReport with one Properties parameter
     *@param properties Receives the properties from AnalyzeFile
     */
    public SummaryReport(Properties properties) {
        this.properties = properties;
    }


    /**
     * Gets the totalTokensCount attribute
     *
     *@return   The totalTokensCount value
     */
    public int getTotalTokensCount() {
        return totalTokensCount;
    }

    /**
     * The processToken method adds all the tokens
     *
     *@param token  token processed word from input file
     */
    public void processToken(String token) {
        totalTokensCount++;
    }

    /**
     * The writeOutputFile method creates the summary report
     *
     *@param inputFilePath  inputFilePath file input name from command line
     */
    public void writeOutputFile(String inputFilePath) {
        PrintWriter out = null;
        String outputFilePath = properties.getProperty("output.dir")
                 + properties.getProperty("output.file.summary");
        File file = new File(inputFilePath);
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)));
            out.println("Application: " + properties.getProperty("application.name"));
            out.println("Author: " + properties.getProperty("author"));
            out.println("Email: " + properties.getProperty("author.email.address"));
            out.println("Input File: " + file.getAbsolutePath());
            out.println("Analyzed On: " + new Date());
            out.println("Total Token Count: " + getTotalTokensCount());
        } catch (FileNotFoundException fileNotFoundEx) {
            System.out.println("Could not find file");
            fileNotFoundEx.printStackTrace();
        } catch (IOException ioEx) {
            System.out.println("Could not write the file");
            ioEx.printStackTrace();
        } catch (Exception exception) {
            System.out.println("Something bad happened");
            exception.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception exception) {
                System.out.println("Failed to close output file");
                exception.printStackTrace();
            }
        }
    }

}
