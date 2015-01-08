package socialnetwork;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;
import org.apache.log4j.Logger;

/**
 * Writable for Double values.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StringWritable implements WritableComparable<StringWritable> {
	protected static Logger log = Logger.getLogger(SocialGraph.class);

	private String value = "";

	public StringWritable() {

	}

	public StringWritable(String value) {
		set(value);
	}

	@Override
	public void readFields(DataInput in) throws IOException {

		byte[] b = new byte[7];
		for (int i = 0; i < 7; i ++) {
			b[i] = (byte) in.readUnsignedByte();
			log.info("Read byte: " + b[i]);
		}
		
		value = new String(b);

//		char c = in.readChar();
//		log.info("Read char0: " + c);
//		while (c != '}') {
//			if (c != '{') {
//				s += c;
//				log.info("Read so far: " + s);
//			}
//			c = in.readChar();
//			log.info("Read char1+: " + c);
//		}

//		value = s;

	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.write(value.getBytes());
	}

	public void set(String value) {
		this.value = value;
	}

	public String get() {
		return value;
	}

	/**
	 * Returns true iff <code>o</code> is a DoubleWritable with the same value.
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof StringWritable)) {
			return false;
		}
		StringWritable other = (StringWritable) o;
		return this.value == other.value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(StringWritable o) {
		return value.compareTo(o.value);
	}

	@Override
	public String toString() {
		return value;
	}

}
