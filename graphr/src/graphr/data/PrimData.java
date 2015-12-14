package graphr.data;

import graphr.graph.GraphDataVisitor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Hashtable;

public class PrimData implements JsonReadableWritable {
	private static final long serialVersionUID = -2410903391512626771L;

	Object o;

	// -- Constructors

	public PrimData(Object o) {
		if ((o instanceof Integer) || (o instanceof Long) || (o instanceof Double) || (o instanceof Boolean)
				|| (o instanceof String) || (o == null) || (o instanceof Hashtable<?, ?>) || (o instanceof HashSet<?>)) {
			this.o = o;
		} else {
			throw new UnsupportedOperationException("Object type unsupoorted by PrimData. " + o.toString());
		}
	}

	public PrimData(String abbrev, String someString) {
		setFromSomeString(abbrev, someString);
	}

	public PrimData(int i) {
		set(i);
	}

	public PrimData(long l) {
		set(l);
	}

	public PrimData(double d) {
		set(d);
	}

	public PrimData(boolean b) {
		set(b);
	}

	public PrimData(String s) {
		set(s);
	}

	// -- Setters

	public void set(long l) {
		o = new Long(l);
	}

	public void set(int i) {
		o = new Integer(i);
	}

	public void set(double d) {
		o = new Double(d);
	}

	public void set(boolean b) {
		o = new Boolean(b);
	}

	public void set(String s) {
		o = s;
	}

	public void set(Object o) {
		this.o = o;
	}

	public void setFromSomeString(String abbrev, String s) {
		switch (abbrev) {
		case "i":
			set(new Integer(s));
			break;
		case "l":
			set(new Long(s));
			break;
		case "d":
			set(new Double(s));
			break;
		case "b":
			set(new Boolean(s));
			break;
		default:
			set(s);
		}

	}

	// -- Getters

	public int i() {
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		}
		if (o instanceof Long) {
			return ((Long) o).intValue();
		}
		if (o instanceof Double) {
			return ((Double) o).intValue();
		}
		if (o instanceof String) {
			return Integer.parseInt((String) o);
		}
		if (o instanceof Boolean) {
			return (((Boolean) o) == true) ? 1 : 0;
		}
		throw new IllegalStateException("Wrapped object type is invalid.");
	}

	public long l() {
		if (o instanceof Integer) {
			return ((Integer) o).longValue();
		}
		if (o instanceof Long) {
			return ((Long) o).longValue();
		}
		if (o instanceof Double) {
			return ((Double) o).longValue();
		}
		if (o instanceof String) {
			return Long.parseLong((String) o);
		}
		if (o instanceof Boolean) {
			return (((Boolean) o) == true) ? 1L : 0L;
		}
		throw new IllegalStateException("Wrapped object type is invalid.");
	}

	public double d() {
		if (o instanceof Integer) {
			return ((Integer) o).doubleValue();
		}
		if (o instanceof Long) {
			return ((Long) o).doubleValue();
		}
		if (o instanceof Double) {
			return ((Double) o).doubleValue();
		}
		if (o instanceof String) {
			return Double.parseDouble((String) o);
		}
		if (o instanceof Boolean) {
			return (((Boolean) o) == true) ? 1L : 0L;
		}
		throw new IllegalStateException("Wrapped object type is invalid.");
	}

	public boolean b() {
		return ((Boolean) o).booleanValue();
	}

	public String s() {
		return o.toString();
	}

	public Object o() {
		return o;
	}

	public String toString() {

		if (o instanceof Integer) {
			return ((Integer) o).toString();
		}

		if (o instanceof Long) {
			return ((Long) o).toString();
		}

		if (o instanceof Double) {
			return ((Double) o).toString();
		}

		if (o instanceof Boolean) {
			return ((Boolean) o).toString();
		}

		if (o instanceof String) {
			return (String) o;
		}

		throw new IllegalStateException("Wrapped object type is invalid.");

	}

	public String getDataAbbrev() {

		return PrimData.getDataAbbrev(o);

	}

	// -- Convenience Methods

	public static String getDataAbbrev(Object o) {

		if (o instanceof Integer) {
			return "i";
		}

		if (o instanceof Long) {
			return "l";
		}

		if (o instanceof Double) {
			return "d";
		}

		if (o instanceof Boolean) {
			return "b";
		}

		if (o instanceof String) {
			return "S";
		}
		if (o instanceof Hashtable<?, ?>){
			return "Ht";
		}
		if(o instanceof HashSet<?>){
			return "Hs";
		}

		throw new IllegalArgumentException("Examined object type invalid.");

	}

	@Override
	public String getAsJson() {

		return o.toString();

	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub

	}

	@Override
	public Serializable accept(GraphDataVisitor visitor) {
		return visitor.visit(this);
	}

}
