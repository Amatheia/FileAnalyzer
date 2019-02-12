package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The KeywordAnalyzer class implements the Analyzer interface and
 * determines where keywords are in the input file.
 * Outputs file with list of keywords and locations.
 *
 *@author   April Albaugh
 */

public class KeywordAnalyzer implements Analyzer {

    private Map<String, List<Integer>> keywordMap;
    private Properties properties;
    private int tokenOccurence;

    /**
     * Constructor for the KeywordAnalyzer object. Instantiates a TreeMap.
     */
    public KeywordAnalyzer()  {
        keywordMap = new TreeMap<String, List<Integer>>();
        tokenOccurence = 0;
    }

    /**
     * Constructor for KeywordAnalyzer with Properties parameter
     *
     *@param properties Receives the properties from AnalyzeFile
     */
    public KeywordAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        readKeywordFile();
    }

    /**
     * Gets the keywordMap attribute
     *
     *@return   The keywordMap value
     */ 
    public Map<String, List<Integer>> getKeywordMap() {
        return keywordMap;
    }

    /**
     * The formatContents method receives the line from the input file, and
     * parses the comma delimited string. 
     *
     *@param line   The line from the input file.
     */
    private void formatContents(String line) {
        String[] lineArray = line.split(", ");
        for(int i = 0; i<lineArray.length; i++) {
            List<Integer> locationList = new ArrayList<Integer>();
            keywordMap.put(lineArray[i], locationList);
        }
    }


    /**
     *  The readKeywordFile method reads the input file from the properties file.
     *  It calls the formatContents method to parse the input.
     */
    private void readKeywordFile() {
        BufferedReader input = null;

        try {
            input = new BufferedReader(new FileReader(properties.getProperty("file.path.keywords")));
            while (input.ready()) {
                String line = null;
                line = input.readLine();
                formatContents(line);
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
     *  The processToken method checks each token in input file.
     *
     *@param token  The token value from the input file
     */
    public void processToken(String token) {
        tokenOccurence++;
        if (keywordMap.containsKey(token)) {
          List<Integer> l = keywordMap.get(token);
          l.add(tokenOccurence);
        }
    }

    /**
     *  The writeOutputFile method creates the KeywordAnalyzer report
     *
     *@param inputFilePath  inputFilePath file name
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir")
                + properties.getProperty("output.file.keyword");
        try (PrintWriter writer = 
                new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            writeKeywords(writer);
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

    /**
     *  The writeKeywords method writes keywords and locations to the file.
     *
     *@param writer Printwriter write to file.
     */
    private void writeKeywords(PrintWriter writer) {       
        for (Map.Entry<String, List<Integer>> entry : keywordMap.entrySet()) {
            writer.print(entry.getKey());
            writer.println(" =");            
            generatePositions(writer, entry.getValue());
            writer.println();
        }
    }

    /**
     *  The generatePositions method writes formatted keyword positions to the file.
     *
     *@param writer Printwriter write to file.
     *@param positionList   The List of keyword positions.
     */
    private void generatePositions(PrintWriter writer, List<Integer> positionList) {
        final int MAX_LINES = 8;
        String commaString = ", ";

        writer.print("[");

        for (int position : positionList) {
            int i = positionList.indexOf(position);
            if (i == positionList.size() - 1) {
                writer.print(position);
            // % (Modulus) Divides left hand operand by right hand operand
            } else if ((i + 1) % MAX_LINES != 0 && i != (positionList.size() - 1)) {
                writer.print(position);
                writer.print(commaString);
            } else {
                writer.print(position);
                writer.println(commaString);
            }
        }

        writer.println("]");
    }
 
}


