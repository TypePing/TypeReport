package typestore.typereport.minecraft.delay;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import typestore.typereport.Main;

public class DelayReport implements Runnable{
	
	private Main m;
	private Map<String, Integer> delay;
	
	public DelayReport(Main m) {
		this.m = m;
		delay = m.getDelayCache().get();
	}
	
	
	@Override
	public void run() {
		for(String player : delay.keySet()) {
			int segundos = delay.get(player);
			int conta = segundos - 1;
			if(conta <= 0) {
				delay.remove(player);
				Player p = Bukkit.getPlayerExact(player);
				if(p != null) {
				for(String msg : m.getConfigLoader().ja_pode_reportar_novamente) {
					p.sendMessage(msg);
				}
				}
			}else {
				delay.put(player, conta);
			}
		}
	}
}
