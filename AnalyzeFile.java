package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The AnalyzeFile class is the main controlling class. This class has 
 * an instance variable that holds the path to the input file. This class runs 
 * the analysis on the input file and checks for a command line argument. This 
 * class calls each Analyzer class and processes tokens, and calls a method to 
 * write the output files.
 *
 *@author   April Albaugh
 */

public class AnalyzeFile {

    public static final int VALID_ARG = 2;

    private Properties properties;
    private String inputFilePath;
    private List<Analyzer> analyzers;

    /**
     *  Constructor for the AnalyzeFile object
     */
    public AnalyzeFile() { 

    }

    /**
     * The runAnalysis method tests if one argument has been entered by the
     * user, if not it will output a message. It will receive the input file  
     * name and call beginAnalyzers method, openAndReadFile method, and 
     * writeAllOutputFiles method.
     *
     *@param args  from the command line
     */
    public void runAnalysis(String[] args) {
        if (args.length != VALID_ARG) {
            System.out.println("Please enter an argument on the command line, "
                                + "an input file name");
            return;
        } else { 
            inputFilePath = args[0];
            String propertiesFilePath = args[1];
            loadProperties(propertiesFilePath);
            beginAnalyzers();
            openAndReadFile();
            writeAllOutputFiles();
        }
    }

    /**
     * The beginAnalyzers method starts each Analyzer
     */
    private void beginAnalyzers() {

        analyzers = new ArrayList<Analyzer>();

        analyzers.add(new SummaryReport(properties));
        analyzers.add(new UniqueTokenAnalyzer(properties));
        analyzers.add(new BigWordAnalyzer(properties));
        analyzers.add(new TokenCountAnalyzer(properties));
        analyzers.add(new TokenSizeAnalyzer(properties));
        analyzers.add(new KeywordAnalyzer(properties));
    }

    /**
     *  The loadProperties method loads the properties file from the command 
     *  line into the Properties object.
     */
    private void loadProperties(String propertiesFilePath)  {

        properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch(IOException ioEx) {
            System.out.println("Could not load the properties file");
            ioEx.printStackTrace();
        } catch(Exception exception) {
            System.out.println("Something bad happened");
            exception.printStackTrace();
        }

    }

    /**
     * The openAndReadFile method will read the input file
     *
     */
    private void openAndReadFile() {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(inputFilePath));

            while (input.ready()) {
                String line = null;
                line = input.readLine();
                createTokens(line);
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
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (Exception exception) {
                System.out.println("Failed to close input file");
                exception.printStackTrace();
            }
        }  
    }

    /**
     * The createTokens method loops through strings, creates tokens, and calls
     * the analyzeTokens method.
     *
     *@param line  line from input file
     */
    private void createTokens(String line) {
        String[] tokenList = line.split("\\W");
        for (int index = 0; index < tokenList.length; index++) {
                analyzeTokens(tokenList[index]);
        }
    }

    /**
     * The analyzeTokens method loops through the Analyzers, and calls the
     * processToken method, if token is not blank, for each analyzer.
     *
     *@param token  token processed words from input file
     */
    private void analyzeTokens(String token) {
        if (token.length() > 0) {
            for (Analyzer analyzer : analyzers) {
                analyzer.processToken(token);
            }
        }
    }

    /**
     * The writeAllOutputFiles method calls each Analyzer writeOutputFile method.
     */
    private void writeAllOutputFiles() { 
        for (Analyzer analyzer : analyzers) {
                analyzer.writeOutputFile(inputFilePath);
        }  
    }

}
