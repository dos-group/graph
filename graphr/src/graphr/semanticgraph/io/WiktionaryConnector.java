package graphr.semanticgraph.io;

import graphr.semanticgraph.io.wiktionary.WiktionaryLemma;
import graphr.semanticgraph.io.wiktionary.WiktionarySense;
import graphr.semanticgraph.io.enums.POSTag;
import graphr.semanticgraph.io.enums.POSTagWiktionary;
import de.tudarmstadt.ukp.jwktl.JWKTL;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEdition;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryPage;
import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.IWiktionarySense;
import de.tudarmstadt.ukp.jwktl.api.PartOfSpeech;
import de.tudarmstadt.ukp.jwktl.api.RelationType;
import de.tudarmstadt.ukp.jwktl.api.util.ILanguage;
import de.tudarmstadt.ukp.jwktl.api.util.Language;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * This class connects to the Wiktionary Database, which needs to be setuped
 * before using this class.
 *
 * @author Florian
 */
public class WiktionaryConnector {

    private static WiktionaryConnector instance;
    private final ILanguage language;
    private final String TARGET_WIKTIONARY_DIRECTORY = System.getProperty("user.home") + "/enwiktionary";

    private WiktionaryConnector() {
        language = Language.ENGLISH;
    }

    /**
     *
     * @return current instance
     */
    public static WiktionaryConnector getInstance() {
        if (instance == null) {
            instance = new WiktionaryConnector();
        }
        return instance;
    }

    /**
     * Looks for a WiktionaryPage (Lemma) for a given word (as lemma) and
     * POSTag.
     *
     * @param lemma word as lemma
     * @param posTagLemma POSTag of Lemma
     * @return all WiktionaryLemmas with given word and POSTag. If non are found
     * returns null.
     */
    public List<WiktionaryLemma> getWiktionaryLemma(String lemma, String posTagLemma) {
        List<WiktionaryLemma> allLemmas = new ArrayList<WiktionaryLemma>();
        List<WiktionaryLemma> lemmasToCheck = new ArrayList<WiktionaryLemma>();

        IWiktionaryEdition wkt = openWiktionary();

        IWiktionaryPage page = wkt.getPageForWord(lemma);

        if (page != null) {
            boolean isSamePOS = false;
            for (IWiktionaryEntry entry : page.getEntries()) {
                List<PartOfSpeech> entryPOSs = entry.getPartsOfSpeech();
                for (PartOfSpeech entryPOS : entryPOSs) {
                    if (entryPOS != null) {
                        POSTagWiktionary posWiktionary = POSTagWiktionary.valueOf(entryPOS.name());
                        POSTag posTag = POSTag.valueOf(posWiktionary.toPOSTag().name());
                        if (posTag.name().equals(posTagLemma)) {
                            isSamePOS = true;
                        }
                    }
                }
            }

            if (isSamePOS) {
                WiktionaryLemma wktLemma = new WiktionaryLemma(lemma, posTagLemma);
                allLemmas.add(wktLemma);
                lemmasToCheck.add(wktLemma);
            }
        }

        int counter = 0;
        while (lemmasToCheck.size() > 0 && counter < 10) {
            WiktionaryLemma currentLemma = lemmasToCheck.get(0);
            lemmasToCheck.remove(0);

            page = wkt.getPageForWord(currentLemma.getLemma());
            if (page != null) {
                for (IWiktionaryEntry entry : page.getEntries()) {
                    if (entry.getWordLanguage() != null) {
                        if (language.getName().equals(entry.getWordLanguage().getName())) {
                            //Add Relations
                            if (entry.getRelations() != null) {
                                for (IWiktionaryRelation relation : entry.getRelations()) {
                                    IWiktionaryPage relationPage = wkt.getPageForWord(relation.getTarget());
                                    if (relationPage != null) {
                                        if (!relationPage.getTitle().contains(" ")) {
                                            boolean isSamePOS = false;
                                            for (IWiktionaryEntry relationPageEntry : relationPage.getEntries()) {
                                                List<PartOfSpeech> entryPOSs = relationPageEntry.getPartsOfSpeech();
                                                for (PartOfSpeech entryPOS : entryPOSs) {
                                                    if (entryPOS != null) {
                                                        POSTagWiktionary posWiktionary = POSTagWiktionary.valueOf(entryPOS.name());
                                                        POSTag posTag = POSTag.valueOf(posWiktionary.toPOSTag().name());
                                                        if (posTag.name().equals(currentLemma.getPOS())) {
                                                            isSamePOS = true;
                                                        }
                                                    }
                                                }
                                            }
                                            if (isSamePOS) {
                                                String relationLemma = SemanticGraphIO.getLemma(relationPage.getTitle());
                                                if (relationLemma != null) {
                                                    if (!relationLemma.contains("\\?")) {//&& relationLemma.matches("\\w")) {
                                                        WiktionaryLemma wktRelationLemma = getWktLemma(allLemmas, relationLemma);
                                                        if (wktRelationLemma == null) {
                                                            wktRelationLemma = new WiktionaryLemma(relationLemma, currentLemma.getPOS());
                                                            allLemmas.add(wktRelationLemma);
                                                            currentLemma.addRelation(wktRelationLemma);
                                                            lemmasToCheck.add(wktRelationLemma);
                                                        } else {
                                                            currentLemma.addRelation(wktRelationLemma);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            counter++;
        }

        closeWiktionary(wkt);
        return allLemmas;
    }

    private WiktionaryLemma getWktLemma(List<WiktionaryLemma> allLemmas, String relationLemma) {
        for (WiktionaryLemma lemma : allLemmas) {
            if (lemma.getLemma().equals(relationLemma)) {
                return lemma;
            }
        }
        return null;
    }

    /**
     * Get all senses for a given word (lemma) and POSTag.
     *
     * @param lemma word (lemma)
     * @param posTag POSTag
     * @return Senses for word+POSTag. returns empty list when non are found.
     */
    public List<WiktionarySense> getWiktionarySenses(String lemma, String posTag) {

        List<WiktionarySense> allSenses = new ArrayList<WiktionarySense>();

        IWiktionaryEdition wkt = openWiktionary();
        List<IWiktionarySense> senses = wkt.getSensesForWord(lemma);

        for (IWiktionarySense sense : senses) {
            if (!sense.getGloss().getPlainText().equals("") && language.getName().equals(sense.getEntry().getWordLanguage().getName())) {
                //Check for POSTag
                boolean hasSamePOSTag = false;
                List<PartOfSpeech> posTags = sense.getEntry().getPartsOfSpeech();
                for (PartOfSpeech singlePosTag : posTags) {
                    if (singlePosTag != null) {
                        POSTagWiktionary posWiktionary = POSTagWiktionary.valueOf(singlePosTag.name());
                        POSTag posTagSense = POSTag.valueOf(posWiktionary.toPOSTag().name());
                        if (posTagSense.name().equals(posTag)) {
                            hasSamePOSTag = true;
                        }
                    }
                }
                if (hasSamePOSTag) {
                    //Remove all unwanted symbols from sense text
                    String senseText = sense.getGloss().getPlainText().replace("\"", "");
                    senseText = senseText.replace("\\", "");
                    senseText = JSONObject.quote(senseText);
                    senseText = senseText.replace("\"", "").replace("\\u", "").replace("null", "nulll");
                    senseText = senseText.replace("\n", " ").replace("\r", " ").replace("\\", "");
                    WiktionarySense wktSense = new WiktionarySense(senseText, posTag);

                    if (sense.getRelations(RelationType.SYNONYM) != null) { //sense.getRelations(RelationType.SYNONYM)
                        for (IWiktionaryRelation relation : sense.getRelations(RelationType.SYNONYM)) { //sense.getRelations(RelationType.SYNONYM)
                            wktSense.addRelation(SemanticGraphIO.getLemma(relation.getTarget()));
                        }
                    }
                    allSenses.add(wktSense);
                }
            }
        }

        closeWiktionary(wkt);

        return allSenses;
    }

    /**
     * Retrieves all wiktionarylemmas
     *
     * @return
     */
    public List<WiktionaryLemma> getAllWiktionaryLemmas() {

        List<WiktionaryLemma> allLemmas = new ArrayList<WiktionaryLemma>();
        List<String> allLemmasAsStrings = new ArrayList<String>();

        IWiktionaryEdition wkt = openWiktionary();

        for (IWiktionaryPage page : wkt.getAllPages()) {
            if (!page.getTitle().contains(" ")) {
                String lemma = SemanticGraphIO.getLemma(page.getTitle());
                if (lemma != null) {
                    if (!lemma.contains("\\?")) {// && lemma.matches("\\w")) {
                        WiktionaryLemma wktLemma;
                        if (allLemmasAsStrings.contains(lemma)) {//Check if exists
                            wktLemma = getLemmaFromList(allLemmas, lemma);
                            if (wktLemma == null) {
                                wktLemma = new WiktionaryLemma(lemma, "");//FIXME: insert POSTag
                                allLemmasAsStrings.add(lemma);
                                allLemmas.add(wktLemma);
                            }
                        } else {
                            wktLemma = new WiktionaryLemma(lemma, "");//FIXME: insert POSTag
                            allLemmasAsStrings.add(lemma);
                            allLemmas.add(wktLemma);
                            System.out.println(lemma);
                        }
                        for (IWiktionaryEntry entry : page.getEntries()) {
                            if (entry.getWordLanguage() != null) {
                                if (language.getName().equals(entry.getWordLanguage().getName())) {
                                    //Add Relations
                                    if (entry.getRelations() != null) {
                                        for (IWiktionaryRelation relation : entry.getRelations()) {
                                            IWiktionaryPage relationPage = wkt.getPageForWord(relation.getTarget());
                                            if (relationPage != null) {
                                                if (!relationPage.getTitle().contains(" ")) {
                                                    String relationLemma = SemanticGraphIO.getLemma(relationPage.getTitle());
                                                    if (relationLemma != null) {
                                                        if (!relationLemma.contains("\\?")) {//&& relationLemma.matches("\\w")) {
                                                            WiktionaryLemma wktRelationLemma;
                                                            if (allLemmasAsStrings.contains(relationLemma)) {//Check if exists
                                                                wktRelationLemma = getLemmaFromList(allLemmas, relationLemma);
                                                                if (wktRelationLemma == null) {
                                                                    wktRelationLemma = new WiktionaryLemma(relationLemma, entry.getPartOfSpeech().name());
                                                                    allLemmasAsStrings.add(relationLemma);
                                                                    allLemmas.add(wktRelationLemma);
                                                                }
                                                            } else {
                                                                wktRelationLemma = new WiktionaryLemma(relationLemma, entry.getPartOfSpeech().name());
                                                                allLemmasAsStrings.add(relationLemma);
                                                                allLemmas.add(wktRelationLemma);
                                                            }
                                                            wktLemma.addRelation(wktRelationLemma);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        closeWiktionary(wkt);

        return allLemmas;
    }

    private WiktionaryLemma getLemmaFromList(List<WiktionaryLemma> allLemmas, String lemma) {
        for (WiktionaryLemma wktLemma : allLemmas) {
            if (wktLemma.getLemma().equals(lemma)) {
                return wktLemma;
            }
        }
        return null;
    }

    private IWiktionaryEdition openWiktionary() {
        return JWKTL.openEdition(new File(TARGET_WIKTIONARY_DIRECTORY));
    }

    private void closeWiktionary(IWiktionaryEdition wkt) {
        wkt.close();
    }
}
