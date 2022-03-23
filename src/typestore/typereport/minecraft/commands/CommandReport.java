package typestore.typereport.minecraft.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import typestore.typereport.Main;
import typestore.typereport.models.submodels.ChatVariveis;
import typestore.typereport.models.submodels.ReportVariaveis;
import typestore.typereport.utils.TimeFormatUtils;

public class CommandReport extends BukkitCommand {
	private Main m;

	public CommandReport(String name, Main main) {
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
			for (String msg : m.getConfigLoader().comando_incompleto) {
				p.sendMessage(msg);
			}
			if (m.getConfigLoader().exibir_som) {
				p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
			}
		}
		if (args.length >= 1) {
			String target = args[0];
			if (!m.getDelayCache().get().containsKey(p.getName())) {
				if (!m.getReportManager().verificar(target)) {
					if(!m.getChatCache().has(p.getName())) {
						SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy - hh | mm | ss");
						String dataValue = data.format(Long.valueOf(System.currentTimeMillis()));
						ChatVariveis va = new ChatVariveis(p.getName(), target, "", "", dataValue, "motivo");
						m.getChatCache().put(p.getName(), va);
					for (String msg : m.getConfigLoader().digite_motivo) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
					}else {
						for (String msg : m.getConfigLoader().digite_motivo) {
							p.sendMessage(msg);
						}
						if (m.getConfigLoader().exibir_som) {
							p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
						}
					}
				} else {
					List<ReportVariaveis> reports = m.getReportManager().getReport(target).getReports();
					int i = 0;
					for (ReportVariaveis report : reports) {
						if (report.getPlayer_que_reportou().equalsIgnoreCase(p.getName())) {
							i++;
						}
					}
					if (i == 0) {
						SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy - hh | mm | ss");
						String dataValue = data.format(Long.valueOf(System.currentTimeMillis()));
						ChatVariveis va = new ChatVariveis(p.getName(), target, "", "", dataValue, "motivo");
						m.getChatCache().put(p.getName(), va);
					for (String msg : m.getConfigLoader().digite_motivo) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
					} else {
						for (String msg : m.getConfigLoader().Voce_ja_reportou_este_jogador) {
							p.sendMessage(msg);
						}
						if (m.getConfigLoader().exibir_som) {
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
						}
					}
				}
			} else {
				for (String msg : m.getConfigLoader().esta_em_delay) {
					TimeFormatUtils time = new TimeFormatUtils();
					p.sendMessage(msg.replace("{tempo}", time.getTimeString(m.getDelayCache().get().get(p.getName()))));
				}
				if (m.getConfigLoader().exibir_som) {
					p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
				}
			}

		}
		return false;
	}
}