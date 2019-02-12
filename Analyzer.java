package java112.analyzer;

import java.util.*;

/**
 * The Analyzer interface with two abstract methods
 *
 *@author   April Albaugh
 */

public interface Analyzer {

    /**
     * The processToken method processes tokens
     *
     *@param token  token each word from the input file
     */
    void processToken(String token);

    /**
     * The writeOutputFile method writes a report for all tokens
     *@param inputFilePath  inputFilePath file name
     */
    void writeOutputFile(String inputFilePath);

}
