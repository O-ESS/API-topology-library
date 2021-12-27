public class Resistor extends Component {

	private resistance r;

	public Resistor(String id, long Default, long max, long min) {
		super(id);
		r = new resistance(Default, min, max);
	}

	public resistance getResistance() {
		return r;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
