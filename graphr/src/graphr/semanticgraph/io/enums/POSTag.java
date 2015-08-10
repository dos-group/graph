package graphr.semanticgraph.io.enums;

/**
 * All external enums which tag POS should be refered to this enum.
 *
 * @author Florian
 */
public enum POSTag {

    /**
     * Noun
     */
    NOUN,
    /**
     * Proper noun (names, locations, organizations, ...)
     */
    PROPER_NOUN,
    /**
     * Verb
     */
    VERB,
    /**
     * Adjective
     */
    ADJECTIVE,
    /**
     * Adverb
     */
    ADVERB,
    /**
     * Pronoun
     */
    PRONOUN,
    /**
     * Determiner (few, most, ...)
     */
    DETERMINER,
    /**
     * Conjunction (and, or, ...)
     */
    CONJUNCTION,
    /**
     * Number and numeral (1, 2, ..., one, two,...).
     */
    NUMBER,
    /**
     * Modal particle (express attitude)
     */
    MODAL,
    /**
     * Particle
     */
    PARTICLE,
    /**
     * Symbol (+, §, $,...)
     */
    SYMBOL,
    /**
     * Interjection
     */
    INTERJECTION,
    /**
     * Preposition (underneath, ...)
     */
    PREPOSITION,
    /**
     * non: not relevant POSTag
     */
    non;

}
