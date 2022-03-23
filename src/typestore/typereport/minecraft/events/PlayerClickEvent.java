package typestore.typereport.minecraft.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import typestore.typereport.Main;

public class PlayerClickEvent implements Listener {
	private Main m;

	public PlayerClickEvent(Main m) {
		this.m = m;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;
		Player p = (Player) e.getWhoClicked();
		String title = m.getMenuReportYaml().getConfig().getString("title").replace("&", "§");

		if (e.getView().getTitle().equals(title)) {

			e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType() == Material.AIR)
				return;
			String target = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0];

			if (e.getCurrentItem().getItemMeta().getDisplayName().equals(target)) {
				if (e.isLeftClick()) {
					m.getMenuDetalhes().open(p, target.replace("§e", ""));
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
				}else if (e.isRightClick() && e.isShiftClick()) {
					m.getReportManager().removeReport(target.replace("§e", ""));
					p.closeInventory();
					for (String msg : m.getConfigLoader().report_deltado) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 3f, 3f);
					}
				} else if (e.isRightClick()) {
					m.getMenuAcoesRapidas().open(p, target.replace("§e", ""));
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
				}
			}
		} else {
			if(e.getView().getTitle().split(" ")[0].equalsIgnoreCase("§7Acoes")) {
			if (e.getView().getTitle()
					.equals("§7Acoes Rapidas > " + e.getInventory().getTitle().split(" ")[3].replace("§7", ""))) {
				e.setCancelled(true);
				if (e.getCurrentItem() == null)
					return;
				if (e.getCurrentItem().getType() == Material.AIR)
					return;

				for (String key : m.getMenuAcoesRapidaYaml().getConfig().getConfigurationSection("Itens")
						.getKeys(false)) {
					String a = "Itens." + key + ".";

					String nome = m.getMenuAcoesRapidaYaml().getConfig().getString(a + "nome").replace("&", "§");
					if (e.getCurrentItem().getItemMeta().getDisplayName().equals(nome)) {
						String permissao = m.getMenuAcoesRapidaYaml().getConfig().getString(a + "permissao");
						if (p.hasPermission(permissao)) {
							p.closeInventory();
							List<String> comandos = m.getMenuAcoesRapidaYaml().getConfig()
									.getStringList(a + "comandos");
							for (String comando : comandos) {
								String comando1 = comando.replace("{player}",
										e.getInventory().getTitle().split(" ")[3].replace("§7", ""));
								Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comando1);
							}
							for (String msg : m.getConfigLoader().acao_executada) {
								String msg1 = msg.replace("{acao}", key);
								String msg2 = msg1.replace("{player}",
										e.getInventory().getTitle().split(" ")[3].replace("§7", ""));
								p.sendMessage(msg2);
							}
							if (m.getConfigLoader().exibir_som) {
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 3f, 3f);
							}
						} else {
							p.closeInventory();
							for (String msg : m.getConfigLoader().sem_permissao) {
								p.sendMessage(msg);
							}
							if (m.getConfigLoader().exibir_som) {
								p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
							}
						}
					}
				}
			}
		}
		}

	}
}