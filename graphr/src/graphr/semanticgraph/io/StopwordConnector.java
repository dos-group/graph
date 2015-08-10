package graphr.semanticgraph.io;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class loads all Stopwords in the English language.
 *
 * @author Florian
 */
public class StopwordConnector {

    private static StopwordConnector instance;
    private Set<String> stopwords;

    private StopwordConnector() {
        try {
            stopwords = new HashSet<String>(Files.readLines(new File(getClass().getClassLoader().getResource("stop-words/stop-words-english1.txt").toURI()), Charset.defaultCharset()));
            stopwords.addAll(Files.readLines(new File(getClass().getClassLoader().getResource("stop-words/stop-words-english2.txt").toURI()), Charset.defaultCharset()));
            stopwords.addAll(Files.readLines(new File(getClass().getClassLoader().getResource("stop-words/stop-words-english3-google.txt").toURI()), Charset.defaultCharset()));
            stopwords.addAll(Files.readLines(new File(getClass().getClassLoader().getResource("stop-words/stop-words-english4.txt").toURI()), Charset.defaultCharset()));
            stopwords.addAll(Files.readLines(new File(getClass().getClassLoader().getResource("stop-words/stop-words-english5.txt").toURI()), Charset.defaultCharset()));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(StopwordConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return current instance
     */
    public static StopwordConnector getInstance() {
        if (instance == null) {
            instance = new StopwordConnector();
        }
        return instance;
    }

    /**
     *
     * @param word single word
     * @return yes when word is a stopword. Otherwise false.
     */
    public boolean isStopword(String word) {
        return (stopwords.contains(word));
    }

    /**
     *
     * @param text untokenized text
     * @return text without stopwords
     */
    public String removeStopwords(String text) {
        String outputString = "";
        //TokenizeText
        String[] tokenizedString = tokenizeString(text);
        //remove stopwords
        for (String token : tokenizedString) {
            if (!isStopword(token)) {
                outputString += " " + token;
            }
        }
        return outputString;
    }

    private String[] tokenizeString(String text) {
        return text.replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
    }

}
