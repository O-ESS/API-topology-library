# API-topology-library
Java was used to implement the API topology library.
We only implemented resistor and nmos as devices because they were present in the given topology example with thier respective properties.
We have six functions 
boolean readJSON(String FileName) -> which reads a topology from a a JSON file and returns if the reading was successful or not.
boolean writeJSON(String topologyid) -> which writes a topology from memory to a JSON file and returns false if there wasn't a topology with the given id.
ArrayList<String> queryTopologies() -> returns an arraylist of the ids of topologies present now in the memory.
boolean deleteTopology(String topologyid) -> deletes a topology gives it's id and returns false if this id is not present.
ArrayList<String> queryDevices(String topologyid) -> returns an arraylist of ids of the components present in the given topology.
ArrayList<String> queryDevicesWithNetlistNode(String topologyid, String netlistNodeid) -> returns an arraylist of the devices conncted to a given component in a given topology. 
