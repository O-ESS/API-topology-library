import java.io.Serializable;
import java.util.ArrayList;

public abstract class Component implements Serializable {
	private String id;
	private ArrayList<String[]> netlist;

	public Component(String id) {
		netlist = new ArrayList<>();
		this.id = id;
	}

	public void addToNetllist(String type, String device) {
		netlist.add(new String[] { type, device });
	}

	public ArrayList<String[]> getDevices() {
		return netlist;
	}

	public String getID() {
		return id;
	}

	public String toString() {
		String netlists = "";
		for (String[] h : netlist)
			netlists += h[0] + " " + h[1];
		return id + " " + netlists;
	}

}
