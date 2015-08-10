package graphr.util;

/**
 *
 * @author Florian
 * @param <A> any object type for the first entry
 * @param <B> any object type for the second entry
 */
public class Tupel<A, B> {

    private A entry1;
    private B entry2;

    /**
     *
     * @param entry1
     * @param entry2
     */
    public Tupel(A entry1, B entry2) {
        this.entry1 = entry1;
        this.entry2 = entry2;
    }

    /**
     *
     * @return first entry object
     */
    public A getEntry1() {
        return entry1;
    }

    /**
     *
     * @return second entry object
     */
    public B getEntry2() {
        return entry2;
    }
}
