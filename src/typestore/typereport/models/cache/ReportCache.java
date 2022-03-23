package typestore.typereport.models.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import typestore.typereport.Main;
import typestore.typereport.models.Report;
import typestore.typereport.models.submodels.ReportVariaveis;

public class ReportCache {

	private Main m;
	private Map<String, Report> u;
	private Map<String, List<ItemStack>> it;
	private List<String> delete;

	public ReportCache(Main m) {
		this.m = m;
		u = new HashMap<>();
		it = new HashMap<>();
		delete = new ArrayList<>();
		List<ItemStack> itList = new ArrayList<>();
		it.put("pendentes", itList);
	}

	public Map<String, Report> get() {
		return u;
	}

	public Map<String, List<ItemStack>> getListItemStack() {
		return it;
	}
	public List<String> getRemoveReport() {
		return delete;
	}

	public void setItensMenuReports(String target) {
		String player = target;
		List<ItemStack> itList = it.get("pendentes");
		FileConfiguration file = m.getMenuReportYaml().getConfig();

		String nome = file.getString("Item.nome").replace("&", "§").replace("{player}", player);
		int i = 0;
		for(ItemStack it : itList) {
			if(it.getItemMeta().getDisplayName().equalsIgnoreCase(nome)) {
				i++;
			}
		}
		if(i != 0) {
			int conta =  u.get(player).getReports().size() - 1;
			List<String> lore = (List<String>) file.getStringList("Item.lore").stream().map(s -> s.replace("&", "§"))
					.collect(Collectors.toList());
			List<String> lore1 = (List<String>) lore.stream()
					.map(s -> s.replace("{quantia}", "" + conta)).collect(Collectors.toList());
			ItemStack it1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta mt = (SkullMeta) it1.getItemMeta();
			mt.setOwner(player);
			mt.setDisplayName(nome);
			mt.setLore(lore1);
			it1.setItemMeta(mt);
			itList.remove(it1);
			
			List<String> lore11 = (List<String>) file.getStringList("Item.lore").stream().map(s -> s.replace("&", "§"))
					.collect(Collectors.toList());
			List<String> lore111 = (List<String>) lore11.stream()
					.map(s -> s.replace("{quantia}", "" + u.get(player).getReports().size())).collect(Collectors.toList());
			ItemStack it11 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta mt1 = (SkullMeta) it11.getItemMeta();
			mt1.setOwner(player);
			mt1.setDisplayName(nome);
			mt1.setLore(lore111);
			it11.setItemMeta(mt1);
			itList.add(it11);
		}else {
			List<String> lore11 = (List<String>) file.getStringList("Item.lore").stream().map(s -> s.replace("&", "§"))
					.collect(Collectors.toList());
			List<String> lore111 = (List<String>) lore11.stream()
					.map(s -> s.replace("{quantia}", "" + u.get(player).getReports().size())).collect(Collectors.toList());
			ItemStack it11 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta mt1 = (SkullMeta) it11.getItemMeta();
			mt1.setOwner(player);
			mt1.setDisplayName(nome);
			mt1.setLore(lore111);
			it11.setItemMeta(mt1);
			itList.add(it11);
		}
		

	}

	public void setItensTarget(String target, String player, String motivo, String data, String prova) {
		if (it.containsKey(target)) {
			ItemStack it1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta mt = (SkullMeta) it1.getItemMeta();
			mt.setOwner(player);
			mt.setDisplayName("§e" + player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add("§e- §7Data do report: §e" + data);
			lore.add("");
			lore.add("§e- §7Motivo: §e" + motivo);
			lore.add("§e- §7Prova: §e" + prova);
			lore.add("");
			mt.setLore(lore);
			it1.setItemMeta(mt);
			it.get(target).add(it1);

		} else {
			
			List<ItemStack> itList = new ArrayList<>();
			ItemStack it1 = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			SkullMeta mt = (SkullMeta) it1.getItemMeta();
			mt.setOwner(player);
			mt.setDisplayName("§e" + player);
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add("§e- §7Data do report: §e" + data);
			lore.add("");
			lore.add("§e- §7Motivo: §e" + motivo);
			lore.add("§e- §7Prova: §e" + prova);
			lore.add("");
			mt.setLore(lore);
			it1.setItemMeta(mt);
			itList.add(it1);
			it.put(target, itList);

		}

	}

	public void setAllItens() {
		for (String target : u.keySet()) {
			setItensMenuReports(target);
			for (ReportVariaveis va : u.get(target).getReports()) {
				setItensTarget(target, va.getPlayer_que_reportou(), va.getMotivo(), va.getData(), va.getProva());
			}
		}
	}

}
