package typestore.typereport.minecraft.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import typestore.typereport.Main;
import typestore.typereport.utils.Scroller;

public class MenuDetalhes {
	
	private Main m;
	@SuppressWarnings("unused")
	private Inventory inv;

	public MenuDetalhes(Main m) {
		this.m = m;
	}

	public void open(Player p, String target) {
		inv = Bukkit.createInventory(null, 6 * 9, "§7Detalhes > "+target);
			Scroller sc = (new Scroller.ScrollerBuilder())
					.withItemsSlots(new Integer[] { Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12),
							Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16),
							Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), Integer.valueOf(22),
							Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(28),
							Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32),
							Integer.valueOf(33), Integer.valueOf(34) })
					.withItems(m.getReportCache().getListItemStack().get(target)).withSize(45).withArrowsSlots(18, 26)
					.withName("§7Detalhes > "+target).build();
			sc.open(p);
	
		
	}

}
