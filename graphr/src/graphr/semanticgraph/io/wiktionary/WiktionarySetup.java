package graphr.semanticgraph.io.wiktionary;

import de.tudarmstadt.ukp.jwktl.JWKTL;
import java.io.File;

/**
 * This class is used to setup the Wiktionary Database. Setup Wiktionary Access:
 * https://code.google.com/p/jwktl/wiki/GettingStarted Please, follow those
 * steps first.
 *
 * As Wiktionary Dump use: http://dumps.wikimedia.org/backup-index.html
 * ->"enwiktionary" ->Articles, templates, media/file descriptions, and primary
 * meta-pages: "enwiktionary-xxxx-pages-articles.xml.bz2"
 *
 * @author Florian
 */
public class WiktionarySetup {

    private static final String userHomeDir = System.getProperty("user.home");
    private static final String PATH_TO_DUMP_FILE = userHomeDir + "/enwiktionary-20150602-pages-articles.xml";
    private static final String TARGET_WIKTIONARY_DIRECTORY = userHomeDir + "/enwiktionary";
    private static final boolean OVERWRITE_EXISTING_FILES = true;

    /**
     * Runs the Database setup for the wiktionary dump xml file.
     *
     * @param args not needed
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        File dumpFile = new File(PATH_TO_DUMP_FILE);
        File outputDirectory = new File(TARGET_WIKTIONARY_DIRECTORY);
        boolean overwriteExisting = OVERWRITE_EXISTING_FILES;

        JWKTL.parseWiktionaryDump(dumpFile, outputDirectory, overwriteExisting);
    }
}
