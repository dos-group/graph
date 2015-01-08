package socialnetwork;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;
import org.apache.hadoop.io.WritableComparable;

/**
 * Writable for Double values.
 */
@InterfaceAudience.Public
@InterfaceStability.Stable
public class StringWritable implements WritableComparable<StringWritable> {

  private String value = "";
  
  public StringWritable() {
    
  }
  
  public StringWritable(String value) {
    set(value);
  }
  
  @Override
  public void readFields(DataInput in) throws IOException {
	  
	String s = "";
	char c = in.readChar();
	while (c != '}') {
		if (c != '{') {
			s += c;
		}
		c = in.readChar();
	}
    value = s;
    
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.write(value.getBytes());
  }
  
  public void set(String value) { this.value = value; }
  
  public String get() { return value; }

  /**
   * Returns true iff <code>o</code> is a DoubleWritable with the same value.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof StringWritable)) {
      return false;
    }
    StringWritable other = (StringWritable)o;
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

