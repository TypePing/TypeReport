package typestore.typereport.models.cache;

import java.util.HashMap;
import java.util.Map;

import typestore.typereport.models.submodels.ChatVariveis;

public class ChatCache {
	
	private Map<String, ChatVariveis> chat;
	
	public ChatCache() {
		chat = new HashMap<>();
	}
	public Map<String, ChatVariveis> get() {
		return chat;
	}
	
	public boolean hasForStatus(String player, String status) {
		if(chat.get(player).getStatus().equalsIgnoreCase(status)) {
			return true;
		}else {
			return false;
		}
	}
	public boolean has(String player) {
		if(chat.containsKey(player)) {
			return true;
		}else {
			return false;
		}
	}
	public ChatVariveis get(String player) {
		return chat.get(player);
	}
	
	public void put(String player, ChatVariveis va) {
		chat.put(player, va);
	}
	public void remove(String player) {
		chat.remove(player);
	}

}
