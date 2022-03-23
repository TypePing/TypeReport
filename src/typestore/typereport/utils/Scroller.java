package typestore.typereport.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import typestore.typereport.Main;



public class Scroller {
	
	
  private List<ItemStack> items;
  
  private HashMap<Integer, Inventory> pages;
  
  private String name;
  
  private int inventorySize;
  
  private List<Integer> slots;
  
  private int backSlot;
  
  private int previousPage;
  
  private int nextPage;
  
  private PlayerRunnable backRunnable;
  
  private ChooseItemRunnable onClickRunnable;
  
  static {
    Bukkit.getPluginManager().registerEvents(new Listener() {
          @EventHandler
          public void onClick(InventoryClickEvent e) {
            if (e.getInventory().getHolder() instanceof Scroller.ScrollerHolder) {
              e.setCancelled(true);
              Player p = (Player)e.getWhoClicked();
              Scroller.ScrollerHolder holder = (Scroller.ScrollerHolder)e.getInventory().getHolder();
              if (e.getSlot() == 40 && 
                holder.getPage() == 1)
            	  p.closeInventory();
              if (e.getSlot() == (holder.getScroller()).previousPage) {
                if (holder.getScroller().hasPage(holder.getPage() - 1))
                  holder.getScroller().open((Player)e.getWhoClicked(), holder.getPage() - 1); 
              } else if (e.getSlot() == (holder.getScroller()).nextPage) {
                if (holder.getScroller().hasPage(holder.getPage() + 1))
                  holder.getScroller().open((Player)e.getWhoClicked(), holder.getPage() + 1); 
              } else if (e.getSlot() == (holder.getScroller()).backSlot) {
                e.getWhoClicked().closeInventory();
                (holder.getScroller()).backRunnable.run((Player)e.getWhoClicked());
              } else if ((holder.getScroller()).slots.contains(Integer.valueOf(e.getSlot())) && 
                (holder.getScroller()).onClickRunnable != null) {
                if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
                  return; 
                (holder.getScroller()).onClickRunnable.run((Player)e.getWhoClicked(), e.getCurrentItem());
              } 
            } 
          }
        },(Plugin)Main.getPlugin(Main.class));
  }
  
  public Scroller(ScrollerBuilder builder) {
    this.items = builder.items;
    this.pages = new HashMap<>();
    this.name = builder.name;
    this.inventorySize = builder.inventorySize;
    this.slots = builder.slots;
    this.backSlot = builder.backSlot;
    this.backRunnable = builder.backRunnable;
    this.previousPage = builder.previousPage;
    this.nextPage = builder.nextPage;
    this.onClickRunnable = builder.clickRunnable;
    createInventories();
  }
  
  private void createInventories() {
    List<List<ItemStack>> lists = getPages(this.items, Integer.valueOf(this.slots.size()));
    int page = 1;
    for (List<ItemStack> list : lists) {
      Inventory inventory = Bukkit.createInventory(new ScrollerHolder(this, page), this.inventorySize, this.name);
      int slot = 0;
      for (ItemStack it : list) {
        inventory.setItem(((Integer)this.slots.get(slot)).intValue(), it);
        slot++;
      } 
      if (page == 1)
        inventory.setItem(40, getBackFlecha()); 
      if (page != 1)
        inventory.setItem(this.previousPage, getPageFlecha(page - 1)); 
      inventory.setItem(this.nextPage, getPageFlecha(page + 1));
      if (this.backRunnable != null)
        inventory.setItem(this.backSlot, getBackFlecha()); 
      this.pages.put(Integer.valueOf(page), inventory);
      page++;
    } 
    ((Inventory)this.pages.get(Integer.valueOf(this.pages.size()))).setItem(this.nextPage, new ItemStack(Material.AIR));
  }
  
  private ItemStack getBackFlecha() {
    ItemStack item = new ItemStack(Material.ARROW);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.GREEN + "Voltar");
    item.setItemMeta(meta);
    return item;
  }
  
  private ItemStack getPageFlecha(int page) {
    ItemStack item = new ItemStack(Material.ARROW);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.GREEN + "P" + page);
    item.setItemMeta(meta);
    return item;
  }
  
  public int getPages() {
    return this.pages.size();
  }
  
  public boolean hasPage(int page) {
    return this.pages.containsKey(Integer.valueOf(page));
  }
  
  public void open(Player player) {
    open(player, 1);
  }
  
  public void open(Player player, int page) {
    player.openInventory(this.pages.get(Integer.valueOf(page)));
  }
  
  private <T> List<List<T>> getPages(Collection<T> c, Integer pageSize) {
    List<T> list = new ArrayList<>(c);
    if (pageSize == null || pageSize.intValue() <= 0 || pageSize.intValue() > list.size())
      pageSize = Integer.valueOf(list.size()); 
    int numPages = (int)Math.ceil(list.size() / pageSize.intValue());
    List<List<T>> pages = new ArrayList<>(numPages);
    for (int pageNum = 0; pageNum < numPages;)
      pages.add(list.subList(pageNum * pageSize.intValue(), Math.min(++pageNum * pageSize.intValue(), list.size()))); 
    return pages;
  }
  
  private class ScrollerHolder implements InventoryHolder {
    private Scroller scroller;
    
    private int page;
    
    public ScrollerHolder(Scroller scroller, int page) {
      this.scroller = scroller;
      this.page = page;
    }
    
    public Inventory getInventory() {
      return null;
    }
    
    public Scroller getScroller() {
      return this.scroller;
    }
    
    public int getPage() {
      return this.page;
    }
  }
  
  public static class ScrollerBuilder {
    private static final List<Integer> ALLOWED_SLOTS = Arrays.asList(new Integer[] { 
          Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(13), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), 
          Integer.valueOf(22), Integer.valueOf(23), 
          Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(31), Integer.valueOf(32), Integer.valueOf(33), 
          Integer.valueOf(34) });
    
    private List<ItemStack> items = new ArrayList<>();
    
    private String name = "";
    
    private int inventorySize = 45;
    
    private List<Integer> slots = ALLOWED_SLOTS;
    
    private int backSlot = -1;
    
    private int previousPage = 18;
    
    private int nextPage = 26;
    
    private Scroller.PlayerRunnable backRunnable;
    
    private Scroller.ChooseItemRunnable clickRunnable;
    
    public ScrollerBuilder withItems(List<ItemStack> items) {
      this.items = items;
      return this;
    }
    
    public ScrollerBuilder withOnClick(Scroller.ChooseItemRunnable clickRunnable) {
      this.clickRunnable = clickRunnable;
      return this;
    }
    
    public ScrollerBuilder withName(String name) {
      this.name = name;
      return this;
    }
    
    public ScrollerBuilder withSize(int size) {
      this.inventorySize = size;
      return this;
    }
    
    public ScrollerBuilder withArrowsSlots(int previousPage, int nextPage) {
      this.previousPage = previousPage;
      this.nextPage = nextPage;
      return this;
    }
    
    public ScrollerBuilder withBackItem(int slot, Scroller.PlayerRunnable runnable) {
      this.backSlot = slot;
      this.backRunnable = runnable;
      return this;
    }
    
    public ScrollerBuilder withItemsSlots(Integer... slots) {
      this.slots = Arrays.asList(slots);
      return this;
    }
    
    public Scroller build() {
      return new Scroller(this);
    }
  }
  
  public static interface ChooseItemRunnable {
    void run(Player param1Player, ItemStack param1ItemStack);
  }
  
  public static interface PlayerRunnable {
    void run(Player param1Player);
  }
}
