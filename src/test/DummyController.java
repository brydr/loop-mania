package test;

import unsw.loopmania.Item;
import unsw.loopmania.LoopManiaWorldController;

public class DummyController extends LoopManiaWorldController {
	// A dummy controller for testing, required for many things since the backend
	// must interact with the front end when getting items

	@Override
	public void onLoad(Item item) {
		// Stub for frontend processing for tests only
		return;
	}
}
