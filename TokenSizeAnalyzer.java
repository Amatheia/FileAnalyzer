package java112.analyzer;

import java.io.*;
import java.util.*;

/**
 * The TokenSizeAnalyzer class implements the Analyzer interface and
 * determines the size distribution of the tokens in the input file.
 * Outputs file with list of token lengths and a histogram of the results.
 *
 *@author   April Albaugh
 */

public class TokenSizeAnalyzer implements Analyzer {

    private Map<Integer, Integer> tokenSizes;
    private Properties properties;
    private int maximumSize;

    /**
     * Constructor for the TokenSizeAnalyzer object. Instantiates a TreeMap.
     */
    public TokenSizeAnalyzer()  {
        tokenSizes = new TreeMap<Integer, Integer>();
    }

    /**
     * Constructor for TokenSizeAnalyzer with Properties parameter
     *
     *@param properties Receives the properties from AnalyzeFile
     */
    public TokenSizeAnalyzer(Properties properties) {
        this();
        this.properties = properties;
        maximumSize = 80;
    }

    /**
     * Gets the tokenSizes attribute
     *
     *@return   The tokenSizes value
     */ 
    public Map<Integer, Integer> getTokenSizes() {
        return tokenSizes;
    }

    /**
     * Gets the maximumSize attribute
     *
     *@return   The maximumSize value
     */ 
    public int getMaximumSize()  {
      return maximumSize;
    }

    /**
     *  The processToken method checks the TreeMap for each token length. 
     *  Then it adds the lengths. 
     *
     *@param token  The token value from the input file
     */
    public void processToken(String token) {
        if (tokenSizes.containsKey(token.length())) {
            int value = tokenSizes.get(token.length());
            tokenSizes.put(token.length(), ++value);
        } else {
            tokenSizes.put(token.length(), 1);
        }
    }

    /**
     *  The writeOutputFile method creates the TokenSizeAnalyzer report
     *
     *@param inputFilePath  inputFilePath file name
     *
     */
    public void writeOutputFile(String inputFilePath) {
        String outputFilePath = properties.getProperty("output.dir")
                + properties.getProperty("output.file.token.size");
        try (PrintWriter writer = 
                new PrintWriter(new BufferedWriter(new FileWriter(outputFilePath)))) {
            outputTokenSizeReport(writer);
            writer.println("");
            outputTokenHistogram(writer);
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
     * The outputTokenSizeReport method outputs the length of each token and the
     * the number of tokens with the key's length.
     *
     * @param writer    Printwriter write to file.
     */
    private void outputTokenSizeReport(PrintWriter writer) {
        for(Map.Entry<Integer, Integer> entry : tokenSizes.entrySet())  {
            writer.println(entry.getKey() + "	" + entry.getValue());
        }
    }

    /**
     * The outputTokenHistogram method outputs a histogram with asterisk
     * values for each key in the tokenSizes map. Collections.max returns the 
     * maximum element of the given collection (the tokenSizes largest value).
     * String keyLine calls the formatLine method and returns a String
     * representation of map Key (an int). Key becomes String with toString, tab 
     * minus length of the Key String and ' ' char adds spaces after Key.
     * Int number outputs the number of asterisks. Converts double to int.
     * The method Math.ceil(double a) returns the smallest double value that is  
     * greater than or equal to the argument and is equal to a mathematical 
     * integer. Math.ceil gives correct number of asterisks (shows at least
     * one). String stars calls the formatLine method and creates a String
     * representation of the number of asterisks and outputs the number of 
     * asterisks, the char value.
     *
     * @param writer   Printwriter write to file.
     */
    private void outputTokenHistogram(PrintWriter writer) {
        final int TAB = 4;
        double tokenLargestValue = Collections.max(tokenSizes.values());
        double totalAsterisks = tokenLargestValue / getMaximumSize();
        for(Map.Entry<Integer, Integer> entry : tokenSizes.entrySet())  {
            String keyLine = formatLine(entry.getKey().toString(),
                             TAB - entry.getKey().toString().length(), ' ');
            int number = (int) Math.ceil(entry.getValue() / totalAsterisks);
            String stars = formatLine(keyLine, number, '*');
            writer.println(stars);
        }
    }

    /**
     * The formatLine method assigns the number to the specified char value and
     * outputs the input String. The primitive char type is used for a single
     * character value.
     *
     * @param input String to add values to.
     * @param number  The number of values to add.
     * @param value Value to add.
     * @return  The input string.
     */
    private String formatLine(String input, int number, char value) {
          //char[] array with dimension
          char[] charArray = new char[number];
          //The Arrays.fill(char[] a, char val) method assigns the 
          //specified char value to each element of the specified array of chars
          Arrays.fill(charArray, value);         
          input += new String(charArray); 
          return input;
    }
 }
