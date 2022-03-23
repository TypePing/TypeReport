package typestore.typereport.models.cache;

import java.util.HashMap;
import java.util.Map;

public class DelayCache {

	private Map<String, Integer> delay;

	public DelayCache() {
		delay = new HashMap<>();
	}

	public Map<String, Integer> get() {
		return delay;
	}

}
