package typestore.typereport;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import typestore.typereport.config.ConfigLoader;
import typestore.typereport.database.CredentialsDataBase;
import typestore.typereport.database.MySqlSqlLiteDataBase;
import typestore.typereport.database.StorageDataBase;
import typestore.typereport.managers.ReportManager;
import typestore.typereport.minecraft.commands.CommandReport;
import typestore.typereport.minecraft.commands.CommandReports;
import typestore.typereport.minecraft.delay.DelayReport;
import typestore.typereport.minecraft.events.PlayerChatEvent;
import typestore.typereport.minecraft.events.PlayerClickEvent;
import typestore.typereport.minecraft.menus.MenuAcoesRapidas;
import typestore.typereport.minecraft.menus.MenuDetalhes;
import typestore.typereport.minecraft.menus.MenuReports;
import typestore.typereport.models.cache.ChatCache;
import typestore.typereport.models.cache.DelayCache;
import typestore.typereport.models.cache.ReportCache;
import typestore.typereport.utils.ConfigUtils;

public class Main extends JavaPlugin {

	private String prefix;

	private ConfigLoader configloader;
	private ConfigUtils MenuReportYaml;
	private ConfigUtils MenuAcoesRapidasYaml;

	private MySqlSqlLiteDataBase mysqlsqllitedatabase;
	private StorageDataBase storagedatabase;

	private ReportCache reportcache;
	private DelayCache delaycache;
	private ChatCache chatcache;

	private ReportManager reportmanager;

	private MenuReports menureportsinventario;
	private MenuDetalhes menudetalhesinventario;
	private MenuAcoesRapidas menuacoesrapidasinventario;

	@Override
	public void onEnable() {
		long before = System.currentTimeMillis();
		loadAll();
		long now = System.currentTimeMillis();
		long total = now - before;
		CommandSender sc = Bukkit.getConsoleSender();
		sc.sendMessage("§7[§bTypeStore§7]");
		sc.sendMessage("");
		sc.sendMessage(prefix + " §aPlugin iniciado com sucesso em §e" + total + " §ams!");
		sc.sendMessage("§7Para reportar bugs e da sugestoes acesse:");
		sc.sendMessage("");
		sc.sendMessage("§ehttps://discord.gg/MpRMgzEBJN");
		sc.sendMessage("");
	}

	@Override
	public void onDisable() {
		reportmanager.saveAllReports();
		reportmanager.deleteReports();
	}

	private void loadAll() {
		prefix = "§e[§aTypeReport§e]";
		loadYamls();
		loadDataBase();
		loadCaches();
		loadManagers();
		loadListenerAndCommands();
		loadMenus();
		run();
		reportcache.setAllItens();

	}

	private void loadYamls() {
		getConfig().options().copyDefaults(false);
		saveDefaultConfig();
		configloader = new ConfigLoader(this);
		createFolder("menus");
		MenuReportYaml = new ConfigUtils(this, "menus/MenuReport.yml");
		MenuAcoesRapidasYaml = new ConfigUtils(this, "menus/MenuAcoesRapidas.yml");
	}

	private void loadDataBase() {
		String host = configloader.host;
		int port = configloader.port;
		String user = configloader.user;
		String password = configloader.password;
		String database = configloader.database;

		CredentialsDataBase credentials = new CredentialsDataBase(host, port, user, password, database);

		mysqlsqllitedatabase = new MySqlSqlLiteDataBase(credentials, this);
		storagedatabase = new StorageDataBase(mysqlsqllitedatabase);
	}

	private void loadCaches() {
		reportcache = new ReportCache(this);
		delaycache = new DelayCache();
		chatcache = new ChatCache();
	}

	private void loadManagers() {
		reportmanager = new ReportManager(this);
	}

	private void loadMenus() {
		menureportsinventario = new MenuReports(this);
		menudetalhesinventario = new MenuDetalhes(this);
		menuacoesrapidasinventario = new MenuAcoesRapidas(this);
	}

	private void loadListenerAndCommands() {
		Bukkit.getPluginManager().registerEvents(new PlayerClickEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerChatEvent(this), this);
		((CraftServer) getServer()).getCommandMap().register(getConfig().getString("Comandos.comando_principal"),
				(Command) new CommandReport(getConfig().getString("Comandos.comando_principal"), this));
		((CraftServer) getServer()).getCommandMap().register(getConfig().getString("Comandos.comando_staff"),
				(Command) new CommandReports(getConfig().getString("Comandos.comando_staff"), this));

	}

	public String getPrefix() {
		return prefix;
	}

	public ConfigLoader getConfigLoader() {
		return configloader;
	}

	public ConfigUtils getMenuReportYaml() {
		return MenuReportYaml;
	}

	public ConfigUtils getMenuAcoesRapidaYaml() {
		return MenuAcoesRapidasYaml;
	}

	public MySqlSqlLiteDataBase getMysqlSqlLiteDataBase() {
		return mysqlsqllitedatabase;
	}

	public StorageDataBase getStorageDataBase() {
		return storagedatabase;
	}

	public ReportCache getReportCache() {
		return reportcache;
	}

	public DelayCache getDelayCache() {
		return delaycache;
	}
	public ChatCache getChatCache() {
		return chatcache;
	}

	public ReportManager getReportManager() {
		return reportmanager;
	}

	public MenuReports getMenuReportsInventario() {
		return menureportsinventario;
	}

	public MenuDetalhes getMenuDetalhes() {
		return menudetalhesinventario;
	}

	public MenuAcoesRapidas getMenuAcoesRapidas() {
		return menuacoesrapidasinventario;
	}

	private void createFolder(String folder) {
		try {
			File langsFolder = new File(getDataFolder() + File.separator + folder + File.separator);
			if (!langsFolder.exists())
				langsFolder.mkdirs();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) new DelayReport(this), 20L, 20L);
	}

}
