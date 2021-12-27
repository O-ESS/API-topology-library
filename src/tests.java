import static org.junit.Assert.*;

import org.junit.Test;

public class tests {

	@Test
	public void testRightFile() throws Exception {
		API api = new API();
		boolean flag = api.readJSON("topology.json");
		assertTrue(flag);
	}

	@Test
	public void testWrongFile() throws Exception {
		API api = new API();
		boolean flag = api.readJSON("top.json");
		assertFalse(flag);
	}

	@Test
	public void testWriteRightTopology() throws Exception {
		API api = new API();
		api.readJSON("topology.json");
		boolean flag = api.writeJSON("top1");
		assertTrue(flag);
	}

	@Test
	public void testWriteWrongTopology() throws Exception {
		API api = new API();
		api.readJSON("topology.json");
		boolean flag = api.writeJSON("top2");
		assertFalse(flag);
	}

}
