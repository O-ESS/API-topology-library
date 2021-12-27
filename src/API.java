import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class API {
	private ArrayList<topology> topologies;
	private int fileCounter;

	public API() {
		// file counter to put files for each time we write a topology
		fileCounter = 0;
		// the list of topologies
		topologies = new ArrayList<>();
	}

	public boolean readJSON(String FileName) throws Exception {
		// parsing file "JSONExample.json"
		try {
			Object obj = new JSONParser().parse(new FileReader(FileName));
			// typecasting obj to JSONObject
			JSONObject jo = (JSONObject) obj;
			// getting the id of the topology
			String id = (String) jo.get("id");
			JSONArray components = (JSONArray) jo.get("components");
			topology cur = new topology(id);
			// looping on the components of the topology
			for (Object O : components) {
				JSONObject JO = (JSONObject) O;
				// id of component
				String Id = (String) JO.get("id");
				// type of component
				String type = (String) JO.get("type");
				// we only have two types resistor and nmos
				if (type.equals("resistor")) {
					JSONObject res = (JSONObject) JO.get("resistance");
					Resistor r = new Resistor(Id, (long) res.get("default"), (long) res.get("max"),
							(long) res.get("min"));
					Map address = ((Map) JO.get("netlist"));
					// adding the devices on the netlist
					Iterator<Map.Entry> itr = address.entrySet().iterator();
					while (itr.hasNext()) {
						Map.Entry pair = itr.next();
						r.addToNetllist((String) pair.getKey(), (String) pair.getValue());
					}
					cur.addComponent(r);
				} else if (type.equals("nmos")) {
					JSONObject res = (JSONObject) JO.get("m(l)");
					nmos r = new nmos(Id, (double) res.get("default"), (long) res.get("max"), (long) res.get("min"));
					Map address = ((Map) JO.get("netlist"));
					// adding the devices on the netlist
					Iterator<Map.Entry> itr = address.entrySet().iterator();
					while (itr.hasNext()) {
						Map.Entry pair = itr.next();
						r.addToNetllist((String) pair.getKey(), (String) pair.getValue());
					}
					cur.addComponent(r);
				} else// device unknown
					throw new Exception("New device");
			}
			// add topology
			topologies.add(cur);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean writeJSON(String topologyid) throws Exception {
		topology cur = null;
		// search for topolgy
		for (topology to : topologies) {
			if (to.getId().equals(topologyid))
				cur = to;
		}
		// if the id is not present in topologies return false
		if (cur == null)
			return false;
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		jo.put("id", topologyid);
		for (Component c : cur.getComponents()) {
			JSONObject Jo = new JSONObject();
			String type = c.getClass().getName();
			if (type.equals("Resistor")) {
				Jo.put("type", "resistor");
				JSONObject resestance = new JSONObject();
				resestance.put("max", ((Resistor) c).getResistance().getMax());
				resestance.put("min", ((Resistor) c).getResistance().getMin());
				resestance.put("default", ((Resistor) c).getResistance().getDefault());
				Jo.put("resistance", resestance);
			} else if (type.equals("nmos")) {
				Jo.put("type", type);
				JSONObject ml = new JSONObject();
				ml.put("max", ((nmos) c).getMl().getMax());
				ml.put("min", ((nmos) c).getMl().getMin());
				ml.put("default", ((nmos) c).getMl().getDefault());
				Jo.put("m(l)", ml);
			}
			Jo.put("id", c.getID());
			JSONObject netlist = new JSONObject();
			for (String[] h : c.getDevices())
				netlist.put(h[0], h[1]);
			Jo.put("netlist", netlist);
			ja.add(Jo);
		}
		jo.put("components", ja);
		PrintWriter pw = new PrintWriter("file" + fileCounter + ".json");
		fileCounter++;
		pw.write(jo.toJSONString());

		pw.flush();
		pw.close();
		return true;
	}

	public ArrayList<String> queryTopologies() {
		ArrayList<String> qtopolgies = new ArrayList<>();
		for (topology to : topologies)
			qtopolgies.add(to.getId());
		return qtopolgies;
	}

	public boolean deleteTopology(String topologyid) {
		ArrayList<topology> newTopologies = new ArrayList<>();
		boolean found = false;
		for (topology c : topologies) {
			if (c.getId().equals(topologyid))
				found = true;
			else
				newTopologies.add(c);
		}
		topologies = newTopologies;
		return found;
	}

	public ArrayList<String> queryDevices(String topologyid) throws IOException {
		ArrayList<String> deviceList = new ArrayList<>();
		for (topology c : topologies) {
			if (c.getId().equals(topologyid)) {
				for (Component co : c.getComponents()) {
					deviceList.add(co.getID() + " " + co.getClass().getName());
				}
			}
		}
		return deviceList;
	}

	public ArrayList<String> queryDevicesWithNetlistNode(String topologyid, String netlistNodeid) throws IOException {
		ArrayList<String> deviceList = new ArrayList<>();
		for (topology c : topologies) {
			if (c.getId().equals(topologyid)) {
				for (Component co : c.getComponents()) {
					if (co.getID().equals(netlistNodeid)) {
						for (String[] h : co.getDevices())
							deviceList.add(h[0] + " : " + h[1]);
					}
				}
			}
		}
		return deviceList;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		API api = new API();
		System.out.println(api.readJSON("topology.json"));
		System.out.println(api.writeJSON("top1"));
		System.out.println(api.queryTopologies());
		System.out.println(api.queryDevices("top1"));
		System.out.println(api.queryDevicesWithNetlistNode("top1", "res1"));
	}

}
