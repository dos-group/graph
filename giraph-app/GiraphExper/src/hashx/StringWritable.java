package hashx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class StringWritable implements WritableComparable<StringWritable> {
	
	private String s;

	public StringWritable(String s) { 
		this.s = s;
	}
	
	public String getS() {
		return s;
	}
	
	public void setS(String s) {
		this.s = s;
	}

	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		s = in.readUTF();
		
	}

	@Override
	public int compareTo(StringWritable o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
