package graphr.semanticgraph.io.enums;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryEntry;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Generic representation of the parts of speech used in Wiktionary. The part of
 * speech is defined for a certain {@link IWiktionaryEntry}.
 *
 * @see IWiktionaryEntry#getPartOfSpeech()
 * @author Christian M. Meyer
 * @author Christof MÃ¼ller
 * @author Lizhen Qu
 */
public enum POSTagWiktionary {

    /**
     * Noun.
     */
    NOUN(POSTag.NOUN),
    /**
     * Proper noun (names, locations, organizations)
     */
    PROPER_NOUN(POSTag.PROPER_NOUN),
    /**
     * First/given name (e.g. Nadine).
     */
    FIRST_NAME(POSTag.NOUN),
    /**
     * Last/family name (e.g. Miller).
     */
    LAST_NAME(POSTag.NOUN),
    /**
     * Toponym (i.e., a place name).
     */
    TOPONYM(POSTag.non),
    /**
     * Only takes the singular form.
     */
    SINGULARE_TANTUM(POSTag.non),
    /**
     * Only takes the plural form.
     */
    PLURALE_TANTUM(POSTag.non),
    /**
     * Measure words (e.g., litre).
     */
    MEASURE_WORD(POSTag.non),
    /**
     * Verb.
     */
    VERB(POSTag.VERB),
    /**
     * Auxiliary verb (can, might, must, etc.).
     */
    AUXILIARY_VERB(POSTag.VERB),
    /**
     * Adjective.
     */
    ADJECTIVE(POSTag.ADJECTIVE),
    /**
     * Adverb.
     */
    ADVERB(POSTag.ADVERB),
    /**
     * Interjection.
     */
    INTERJECTION(POSTag.INTERJECTION),
    /**
     * Salutation (e.g., good afternoon).
     */
    SALUTATION(POSTag.non),
    /**
     * Onomatopoeia (e.g., peng, tic-tac).
     */
    ONOMATOPOEIA(POSTag.non),
    /**
     * Phrase.
     */
    PHRASE(POSTag.non),
    /**
     * Idiom (e.g., rock 'n' roll).
     */
    IDIOM(POSTag.non),
    /**
     * Collocation (e.g., strong tea).
     */
    COLLOCATION(POSTag.non),
    /**
     * Proverb (e.g., that's the way life is).
     */
    PROVERB(POSTag.VERB),
    /**
     * Mnemonic (e.g., "My Very Educated Mother Just Served Us Nachos" for
     * planet names).
     */
    MNEMONIC(POSTag.non),
    /**
     * Pronoun.
     */
    PRONOUN(POSTag.PRONOUN),
    /**
     * (Irreflexive) personal pronoun (I, you, he, she, we, etc.).
     */
    PERSONAL_PRONOUN(POSTag.PRONOUN),
    /**
     * Reflexive personal pronoun (myself, herself, ourselves, etc.).
     */
    REFLEXIVE_PRONOUN(POSTag.PRONOUN),
    /**
     * Possessive pronoun (mine, your, our, etc.).
     */
    POSSESSIVE_PRONOUN(POSTag.PRONOUN),
    /**
     * Demonstrative pronoun (_This_ is fast).
     */
    DEMONSTRATIVE_PRONOUN(POSTag.PRONOUN),
    /**
     * Relative pronoun (She sold the car, _which_ was very old ).
     */
    RELATIVE_PRONOUN(POSTag.PRONOUN),
    /**
     * Indefinite pronoun (_Nobody_ bought the car ).
     */
    INDEFINITE_PRONOUN(POSTag.PRONOUN),
    /**
     * Interrogative pronoun (who, what, etc.).
     */
    INTERROGATIVE_PRONOUN(POSTag.PRONOUN),
    /**
     * Interrogative adverb (how, when, etc.).
     */
    INTERROGATIVE_ADVERB(POSTag.ADVERB),
    /**
     * Particle.
     */
    PARTICLE(POSTag.PARTICLE),
    /**
     * Answer particle (yes, no, etc.).
     */
    ANSWERING_PARTICLE(POSTag.PARTICLE),
    /**
     * Negative particle (neither...nor, etc.).
     */
    NEGATIVE_PARTICLE(POSTag.PARTICLE),
    /**
     * Comparative particle (She is taller _than_ me).
     */
    COMPARATIVE_PARTICLE(POSTag.PARTICLE),
    /**
     * Focus particle (also, only, even, etc.).
     */
    FOCUS_PARTICLE(POSTag.PARTICLE),
    /**
     * Intensifying particle (very, low, etc.).
     */
    INTENSIFYING_PARTICLE(POSTag.PARTICLE),
    /**
     * Modal particle (express attitude, e.g., German: Sprich _doch mal_ mit ihr
     * ).
     */
    MODAL_PARTICLE(POSTag.non),
    /**
     * Article (a, the, etc.).
     */
    ARTICLE(POSTag.non),
    /**
     * Determiner (few, most, etc.).
     */
    DETERMINER(POSTag.DETERMINER),
    /**
     * Abbreviation.
     */
    ABBREVIATION(POSTag.non),
    /**
     * Acronym (pronounced as a word, e.g., "ROM", "NATO", "sonar")
     */
    ACRONYM(POSTag.NOUN),
    /**
     * Initialism (pronounced as letter by letter, e.g., "CD", "URL")
     */
    INITIALISM(POSTag.NOUN),
    /**
     * Contraction (e.g., it's).
     */
    CONTRACTION(POSTag.non),
    /**
     * Conjunction (and, or, etc.).
     */
    CONJUNCTION(POSTag.CONJUNCTION),
    /**
     * Subordinating conjunction (as soon as, after, etc.).
     */
    SUBORDINATOR(POSTag.non),
    /**
     * Preposition (e.g., underneath).
     */
    PREPOSITION(POSTag.PREPOSITION),
    /**
     * Postposition (e.g., ago).
     */
    POSTPOSITION(POSTag.non),
    /**
     * Affix.
     */
    AFFIX(POSTag.non),
    /**
     * Prefix.
     */
    PREFIX(POSTag.non),
    /**
     * Suffix.
     */
    SUFFIX(POSTag.non),
    /**
     * Place name suffix (e.g., -burg).
     */
    PLACE_NAME_ENDING(POSTag.non),
    /**
     * Bound lexeme.
     */
    LEXEME(POSTag.non),
    /**
     * Character.
     */
    CHARACTER(POSTag.non),
    /**
     * Letter of the alphabet (A, B, C, etc.).
     */
    LETTER(POSTag.non),
    /**
     * Number and numeral (e.g., two, fifteen, etc.).
     */
    NUMBER(POSTag.NUMBER),
    /**
     * Number and numeral (e.g., two, fifteen, etc.).
     */
    NUMERAL(POSTag.NUMBER),
    /**
     * Punctuation mark (., ?, ;, etc.).
     */
    PUNCTUATION_MARK(POSTag.SYMBOL),
    /**
     * Symbol (+, Â§, $, etc.).
     */
    SYMBOL(POSTag.SYMBOL),
    /**
     * Chinese Hanzi character.
     */
    HANZI(POSTag.non),
    /**
     * Japanese Kanji character.
     */
    KANJI(POSTag.non),
    /**
     * Japanese Katakana character.
     */
    KATAKANA(POSTag.non),
    /**
     * Japanese Hiragana character.
     */
    HIRAGANA(POSTag.non),
    /**
     * Gismu (a root word in Lojban).
     */
    GISMU(POSTag.non),
    /**
     * Inflected word form.
     */
    WORD_FORM(POSTag.non),
    /**
     * Participle.
     */
    PARTICIPLE(POSTag.non),
    /**
     * Transliterated word form.
     */
    TRANSLITERATION(POSTag.non),
    /**
     * @deprecated No longer used.
     */
    @Deprecated
    COMBINING_FORM(POSTag.non),
    /**
     * @deprecated No longer used.
     */
    @Deprecated
    EXPRESSION(POSTag.non),
    /**
     * @deprecated No longer used.
     */
    @Deprecated
    NOUN_PHRASE(POSTag.non);

    private static final Logger logger = Logger.getLogger(POSTagWiktionary.class.getName());

    protected static Set<String> unknownPos;

    /**
     * Find the part of speech with the given name. The method only for its
     * canonical English name. Use {@link #findByName(String, Map)} for
     * language-specific lookup. If no part of speech could be found,
     * <code>null</code> is returned.
     */
    public static POSTagWiktionary findByName(final String name) {
        return findByName(name, null);
    }

    /**
     * Find the part of speech with the given name. The method checks both for
     * the canonical English name as well as alternative names in other
     * languages, which can be specified by passing a custom additional map. If
     * no part of speech could be found, <code>null</code> is returned.
     */
    public static POSTagWiktionary findByName(final String name,
            final Map<String, POSTagWiktionary> additionalMap) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        StringBuilder label = new StringBuilder();
        for (char p : name.trim().toCharArray()) {
            if (p == ' ' || p == '\n' || p == '\r' || p == '\t') {
                label.append('_');
            } else {
                label.append(Character.toUpperCase(p));
            }
        }

        POSTagWiktionary result = null;
        if (additionalMap != null) {
            result = additionalMap.get(label.toString());
        }

        if (result == null) {
            try {
                result = POSTagWiktionary.valueOf(label.toString());
            } catch (IllegalArgumentException e) {
                logger.finer("Unknown part of speech: " + label.toString());
            }
        }

        /*if (result == null) {
         if (unknownPos == null)
         unknownPos = new TreeSet<String>();
         if (!unknownPos.contains(label.toString())) {
         unknownPos.add(label.toString());
         System.err.println("Unknown part of speech: " + label.toString());
         }
         }*/
        return result;
    }

    /**
     * Tests if the specified parts of speech are equal. The method returns
     * <code>true</code> if both parts of speech are <code>null</code>, but
     * <code>false</code> if only one of them is <code>null</code>.
     */
    public static boolean equals(final POSTagWiktionary partOfSpeech1,
            final POSTagWiktionary partOfSpeech2) {
        if (partOfSpeech1 == partOfSpeech2) {
            return true;
        } else if (partOfSpeech1 == null || partOfSpeech2 == null) {
            return false;
        } else {
            return partOfSpeech1.equals(partOfSpeech2);
        }
    }

    private final POSTag posTag;

    private POSTagWiktionary(POSTag posTag) {
        this.posTag = posTag;
    }

    public POSTag toPOSTag() {
        return this.posTag;
    }
}
