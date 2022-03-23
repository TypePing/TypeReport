package typestore.typereport.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import typestore.typereport.Main;
import typestore.typereport.database.StorageDataBase;
import typestore.typereport.models.Report;
import typestore.typereport.models.submodels.ReportVariaveis;

public class ReportManager {
	
	private Main m;
	private Map<String, Report> u;

	public ReportManager(Main m) {
		this.m = m;
		u = m.getReportCache().get();
		loadAllReports();
	}

	public Report getReport(String player) {
		return u.get(player);
	}

	public boolean verificar(String player) {
		if (u.containsKey(player)) {
			return true;
		} else {
			return false;
		}
	}
	//;player_que_reportou:player_que_foi_reportado:motivo,:prova,:data;
	public void loadAllReports() {
		int i = 0;
		StorageDataBase db = m.getStorageDataBase();
		for(String playerQueFoiReportado : db.getReports()) {
			String reportDoPlayer = db.getreports(playerQueFoiReportado);
			List<ReportVariaveis> reports = new ArrayList<>();
			List<String> list = Arrays.asList(reportDoPlayer.split(";"));
			for (String l : list) {
				if (!l.equalsIgnoreCase("")) {
					
					
					String player_que_reportou = l.split(":")[0];
					String player_que_foi_reportado = l.split(":")[1];
					String motivo = l.split(":")[2];
					String prova = l.split(":")[3];
					String data = l.split(":")[4];
	
					
					ReportVariaveis va = new ReportVariaveis(player_que_reportou, player_que_foi_reportado, motivo, prova, data);
					reports.add(va);
				}
			}
			Report report = new Report(playerQueFoiReportado, reports);
			u.put(playerQueFoiReportado, report);
			i++;
		}
		if(m.getConfigLoader().console_log) {
			if(i >= 1) {
			Bukkit.getConsoleSender().sendMessage(m.getPrefix()+" §aForam carregados §e"+i+" §areports §ePENDENTES§a!");
		}
		}
	}
	//;player_que_reportou:player_que_foi_reportado:motivo,:prova,:data;
	@SuppressWarnings("static-access")
	public void saveAllReports() {
		int i = 0;
		StorageDataBase db = m.getStorageDataBase();
		for(String playerQueFoiReportado : u.keySet()) {
			List<String> list = new ArrayList<>();	
			if (db.verificarPlayer(playerQueFoiReportado)) {
				
					String variaveis = "";
					for (ReportVariaveis re : u.get(playerQueFoiReportado).getReports()) {
						variaveis = ";"+re.getPlayer_que_reportou()+":"+re.getPlayer_que_foi_reportado()
						+":"+re.getMotivo()+":"+re.getProva()+":"+re.getData()+";";
						list.add(variaveis);
					}
					
String juntas = variaveis.join(";", list);
				
					db.setreports(playerQueFoiReportado, juntas);
					u.remove(playerQueFoiReportado);
					i++;
					
			}else {
				String variaveis = "";
				for (ReportVariaveis re : u.get(playerQueFoiReportado).getReports()) {
					variaveis = ";"+re.getPlayer_que_reportou()+":"+re.getPlayer_que_foi_reportado()
					+":"+re.getMotivo()+":"+re.getProva()+":"+re.getData()+";";
					list.add(variaveis);
				}
				String juntas = variaveis.join(";", list);
				db.setPlayer(playerQueFoiReportado, juntas);
				u.remove(playerQueFoiReportado);
				i++;
			}
		
		}
		if(m.getConfigLoader().console_log) {
			if(i >= 1) {
			Bukkit.getConsoleSender().sendMessage(m.getPrefix()+" §aForam salvos §e"+i+" §areports §ePENDENTES§a!");
		}
		}
	}
	public void addReport(String player, String target, String motivo, String prova, String data) {
		if(!verificar(target)) {
			List<ReportVariaveis> reports = new ArrayList<>();
			ReportVariaveis va = new ReportVariaveis(player, 
					target, motivo, prova, data);
			reports.add(va);
			Report report = new Report(target, reports);
			u.put(target, report);
		}else {
			List<ReportVariaveis> reports = getReport(target).getReports();
			ReportVariaveis va = new ReportVariaveis(player, 
					target, motivo, prova, data);
			reports.add(va);
		}
	}
	public void removeReport(String target) {
		FileConfiguration file = m.getMenuReportYaml().getConfig();
		List<String> list = m.getReportCache().getRemoveReport();
		list.add(target);
		
		String nome = file.getString("Item.nome").replace("&", "§").replace("{player}", target);
		List<String> lore11 = (List<String>) file.getStringList("Item.lore").stream().map(s -> s.replace("&", "§"))
				.collect(Collectors.toList());
		List<String> lore111 = (List<String>) lore11.stream()
				.map(s -> s.replace("{quantia}", "" + u.get(target).getReports().size())).collect(Collectors.toList());
		ItemStack it11 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta mt1 = (SkullMeta) it11.getItemMeta();
		mt1.setOwner(target);
		mt1.setDisplayName(nome);
		mt1.setLore(lore111);
		it11.setItemMeta(mt1);
		
		m.getReportCache().getListItemStack().get("pendentes").remove(it11);
	
		u.remove(target);
	}
	
	public void deleteReports() {
		StorageDataBase db = m.getStorageDataBase();
		for(String player : m.getReportCache().getRemoveReport()) {
			db.deletePlayer(player);
		}
	}

}
