package graphr.semanticgraph.io.enums;

/**
 * Used by Stanford Library as Part-of-Speech tags.
 *
 * Source: http://cs.nyu.edu/grishman/jet/guide/PennPOS.html
 *
 * Part-of-Speech tags:<br>
 * 1. CC Coordinating conjunction<br>
 * 2. CD Cardinal number<br>
 * 3. DT Determiner<br>
 * 4. EX Existential there<br>
 * 5. FW Foreign word<br>
 * 6. IN Preposition or subordinating conjunction<br>
 * 7. JJ Adjective<br>
 * 8. JJR Adjective, comparative<br>
 * 9. JJS Adjective, superlative<br>
 * 10. LS List item marker<br>
 * 11. MD Modal<br>
 * 12. NN Noun, singular or mass<br>
 * 13. NNS Noun, plural<br>
 * 14. NNP Proper noun, singular<br>
 * 15. NNPS Proper noun, plural<br>
 * 16. PDT Predeterminer<br>
 * 17. POS Possessive ending<br>
 * 18. PRP Personal pronoun<br>
 * 19. PRP$ Possessive pronoun<br>
 * 20. RB Adverb<br>
 * 21. RBR Adverb, comparative<br>
 * 22. RBS Adverb, superlative<br>
 * 23. RP Particle<br>
 * 24. SYM Symbol<br>
 * 25. TO to<br>
 * 26. UH Interjection<br>
 * 27. VB Verb, base form<br>
 * 28. VBD Verb, past tense<br>
 * 29. VBG Verb, gerund or present participle<br>
 * 30. VBN Verb, past participle<br>
 * 31. VBP Verb, non-3rd person singular present<br>
 * 32. VBZ Verb, 3rd person singular present<br>
 * 33. WDT Wh-determiner<br>
 * 34. WP Wh-pronoun<br>
 * 35. WP$ Possessive wh-pronoun<br>
 * 36. WRB Wh-adverb<br>
 *
 * @author Alexander
 */
public enum POSTagStanfordNLP {

    CC(POSTag.CONJUNCTION),
    CD(POSTag.NUMBER),
    DT(POSTag.DETERMINER),
    EX(POSTag.non),
    FW(POSTag.non),
    IN(POSTag.PREPOSITION),
    JJ(POSTag.ADJECTIVE),
    JJR(POSTag.ADJECTIVE),
    JJS(POSTag.ADJECTIVE),
    LS(POSTag.non),
    MD(POSTag.MODAL),
    NN(POSTag.NOUN),
    NNS(POSTag.NOUN),
    NNP(POSTag.PROPER_NOUN),
    NNPS(POSTag.PROPER_NOUN),
    PDT(POSTag.DETERMINER),
    POS(POSTag.non),
    PRP(POSTag.PRONOUN),
    PRP$(POSTag.PRONOUN),
    RB(POSTag.ADVERB),
    RBR(POSTag.ADVERB),
    RBS(POSTag.ADVERB),
    RP(POSTag.PARTICLE),
    SYM(POSTag.SYMBOL),
    TO(POSTag.non),
    UH(POSTag.INTERJECTION),
    VB(POSTag.VERB),
    VBD(POSTag.VERB),
    VBG(POSTag.VERB),
    VBN(POSTag.VERB),
    VBP(POSTag.VERB),
    VBZ(POSTag.VERB),
    WDT(POSTag.DETERMINER),
    WP(POSTag.PRONOUN),
    WP$(POSTag.PRONOUN),
    WRB(POSTag.ADVERB);

    private final POSTag posTag;

    private POSTagStanfordNLP(POSTag posTag) {
        this.posTag = posTag;
    }

    public POSTag toPOSTag() {
        return this.posTag;
    }
    
}
