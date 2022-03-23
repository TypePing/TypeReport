package typestore.typereport.minecraft.events;

import java.text.SimpleDateFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import typestore.typereport.Main;
import typestore.typereport.models.cache.ChatCache;
import typestore.typereport.models.submodels.ChatVariveis;
import typestore.typereport.models.submodels.ReportVariaveis;
import typestore.typereport.utils.TimeFormatUtils;

public class PlayerChatEvent implements Listener {

	private Main m;
	private ChatCache chat;

	public PlayerChatEvent(Main m) {
		this.m = m;
		chat = m.getChatCache();

	}

	@EventHandler
	public void aoChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(!chat.has(p.getName())) return;
		if (chat.hasForStatus(p.getName(), "motivo")) {
		e.setCancelled(true);
		String msgChat = e.getMessage();
		if (!msgChat.equalsIgnoreCase("cancelar")) {
			if (!m.getDelayCache().get().containsKey(p.getName())) {
				if (m.getReportManager().verificar(chat.get(p.getName()).getPlayer_que_foi_reportado())) {
				List<ReportVariaveis> reports = m.getReportManager().getReport(chat.get(p.getName()).getPlayer_que_foi_reportado()).getReports();
				int i = 0;
				for (ReportVariaveis report : reports) {
					if (report.getPlayer_que_reportou().equalsIgnoreCase(p.getName())) {
						i++;
					}
				}
				if (i == 0) {
					chat.get(p.getName()).setStatus("prova");
					chat.get(p.getName()).setMotivo(msgChat);
					for (String msg : m.getConfigLoader().digite_prova) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
					return;
				}else {
					chat.remove(p.getName());
					for(String msg : m.getConfigLoader().Voce_ja_reportou_este_jogador) {
						p.sendMessage(msg);
					}
					if(m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
					}
				}
				}else {
					chat.get(p.getName()).setStatus("prova");
					chat.get(p.getName()).setMotivo(msgChat);
					for (String msg : m.getConfigLoader().digite_prova) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.CLICK, 3f, 3f);
					}
					return;
				}
			} else {
				chat.remove(p.getName());
				for (String msg : m.getConfigLoader().esta_em_delay) {
					TimeFormatUtils time = new TimeFormatUtils();
					p.sendMessage(msg.replace("{tempo}", time.getTimeString(m.getDelayCache().get().get(p.getName()))));
				}
				if (m.getConfigLoader().exibir_som) {
					p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
				}
			}
		
		}else {
			chat.remove(p.getName());
			for(String msg : m.getConfigLoader().operacao_cancelada) {
				p.sendMessage(msg);
			}
			if(m.getConfigLoader().exibir_som) {
				p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
			}
		}
		}else {
			if (!chat.hasForStatus(p.getName(), "prova"))
				return;
			e.setCancelled(true);
			String msgChat = e.getMessage();
			if (!msgChat.equalsIgnoreCase("cancelar")) {
				ChatVariveis va = chat.get(p.getName());
	if (!m.getDelayCache().get().containsKey(p.getName())) {
					
					if (m.getReportManager().verificar(va.getPlayer_que_foi_reportado())) {
					List<ReportVariaveis> reports = m.getReportManager().getReport(va.getPlayer_que_foi_reportado()).getReports();
					int i = 0;
					for (ReportVariaveis report : reports) {
						if (report.getPlayer_que_reportou().equalsIgnoreCase(p.getName())) {
							i++;
						}
					}
					if (i == 0) {
					String target = va.getPlayer_que_foi_reportado();
					String motivo = va.getMotivo();
					String prova = msgChat;
					SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy - hh | mm | ss");
					String dataValue = data.format(Long.valueOf(System.currentTimeMillis()));

					chat.remove(p.getName());
					m.getReportManager().addReport(p.getName(), target, motivo, prova, dataValue);
					m.getDelayCache().get().put(p.getName(), m.getConfigLoader().delay_para_reportar);
					m.getReportCache().setItensMenuReports(target);
					m.getReportCache().setItensTarget(target, p.getName(), motivo, dataValue, prova);
					for (String msg : m.getConfigLoader().report_enviado) {
						p.sendMessage(msg);
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 3f, 3f);
					}
					for(Player pls : Bukkit.getOnlinePlayers()) {
						if(pls.hasPermission("typereport.staff.mensagem")) {
							for (String msg : m.getConfigLoader().report_recebido_staff) {
								pls.sendMessage(msg);
							}
							if (m.getConfigLoader().exibir_som) {
								pls.playSound(pls.getLocation(), Sound.CLICK, 3f, 3f);
							}
						}
					}
					
					}else {
						chat.remove(p.getName());
						for(String msg : m.getConfigLoader().Voce_ja_reportou_este_jogador) {
							p.sendMessage(msg);
						}
						if(m.getConfigLoader().exibir_som) {
							p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
						}
					}
					}else {
						String target = va.getPlayer_que_foi_reportado();
						String motivo = va.getMotivo();
						String prova = msgChat;
						SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy - hh | mm | ss");
						String dataValue = data.format(Long.valueOf(System.currentTimeMillis()));
						chat.remove(p.getName());
						m.getReportManager().addReport(p.getName(), target, motivo, prova, dataValue);
						m.getDelayCache().get().put(p.getName(), m.getConfigLoader().delay_para_reportar);
						m.getReportCache().setItensMenuReports(target);
						m.getReportCache().setItensTarget(target, p.getName(), motivo, dataValue, prova);
						for (String msg : m.getConfigLoader().report_enviado) {
							p.sendMessage(msg);
						}
						if (m.getConfigLoader().exibir_som) {
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 3f, 3f);
						}
						for(Player pls : Bukkit.getOnlinePlayers()) {
							if(pls.hasPermission("typereport.staff.mensagem")) {
								for (String msg : m.getConfigLoader().report_recebido_staff) {
									pls.sendMessage(msg);
								}
								if (m.getConfigLoader().exibir_som) {
									pls.playSound(pls.getLocation(), Sound.CLICK, 3f, 3f);
								}
							}
						}
						
					}
				} else {
					chat.remove(p.getName());
					for (String msg : m.getConfigLoader().esta_em_delay) {
						TimeFormatUtils time = new TimeFormatUtils();
						p.sendMessage(msg.replace("{tempo}", time.getTimeString(m.getDelayCache().get().get(p.getName()))));
					}
					if (m.getConfigLoader().exibir_som) {
						p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
					}
				}
			
		}else {
			chat.remove(p.getName());
			for(String msg : m.getConfigLoader().operacao_cancelada) {
				p.sendMessage(msg);
			}
			if(m.getConfigLoader().exibir_som) {
				p.playSound(p.getLocation(), Sound.VILLAGER_NO, 3f, 3f);
			}
		}
		

			
		}
	}
}
