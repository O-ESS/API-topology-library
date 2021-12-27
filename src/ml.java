
public class ml {
	private double Default;
	private long min;
	private long max;

	public ml(double Default, long min, long max) {
		this.Default = Default;
		this.min = min;
		this.max = max;
	}

	public void setDefault(int val) {
		if (val >= min && val <= max)
			Default = val;
	}

	public double getDefault() {
		return Default;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}

}
