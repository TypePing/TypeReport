package typestore.typereport.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import typestore.typereport.Main;

public class CommandReports extends BukkitCommand {
	private Main m;

	public CommandReports(String name, Main main) {
		super(name);
		setAliases(new ArrayList<String>());
		m = main;
	}

	public boolean execute(CommandSender sender, String alias, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(m.getPrefix() + " §cComando apenas para jogadores!");
			return true;
		}
		Player p = (Player) sender;
		if (args.length == 0) {
			if(p.hasPermission("typereport.abrir")) {
				m.getMenuReportsInventario().open(p);
				if(m.getConfigLoader().exibir_som) {
					p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
				}
				return true;
			}else {
				for (String msg : m.getConfigLoader().sem_permissao) {
					p.sendMessage(msg);
				}
				if (m.getConfigLoader().exibir_som) {
					p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
				}
			}
		}
		return false;
	}
}