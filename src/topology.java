import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.lang.reflect.Array;
import java.util.ArrayList;

class topology {

	private ArrayList<Component> components;
	private String id;

	public topology(String id) throws IOException {
		components = new ArrayList<>();
		this.id = id;
//		writeTopolgy();
	}

//	public void writeTopolgy() throws IOException {
//		File f = new File("topolgy");
//		f.delete();
//		f.createNewFile();
//		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
//		oos.writeObject(this);
//		oos.close();
//	}
	public String toString() {
		return id + components.toString();
	}

	public void addComponent(Component cur) throws IOException {
		components.add(cur);
		// writeTopolgy();
	}

	public ArrayList<Component> getComponents() throws IOException {
		return components;
	}

	public String getId() {
		return id;
	}

	public static void main(String[] args) throws Exception {

	}
}
