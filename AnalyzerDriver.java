package java112.analyzer;

/**
 * The AnalyzerDriver class with main method that instantiates an instance of
 * main processing class AnalyzeFile.
 *
 *@author   April Albaugh
 */

public class AnalyzerDriver {

    /**
     * The main program for the AnalyzerDriver class that instantiates an
     * AnalyzeFile object. Passes the command line arguments to the runAnalysis
     * method.
     *
     *@param args   The command line arguments
     */
    public static void main(String[] args) {
        AnalyzeFile analyzer = new AnalyzeFile();
        analyzer.runAnalysis(args);
    }

}
