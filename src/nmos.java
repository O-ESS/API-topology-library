
public class nmos extends Component {

	private ml ml1;

	public nmos(String id, double Default, long max, long min) {
		super(id);
		ml1 = new ml(Default, min, max);
	}

	public ml getMl() {
		return ml1;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
