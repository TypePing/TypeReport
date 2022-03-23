package typestore.typereport.utils;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
public class ConfigUtils {


	  private final File file;
	  
	  private final FileConfiguration config;
	  
	  public ConfigUtils(JavaPlugin plugin, String name) {
	    this.file = new File(plugin.getDataFolder(), name);
	    if (!this.file.exists()) {
	      this.file.getParentFile().mkdirs();
	      plugin.saveResource(name, false);
	    } 
	    this.config = (FileConfiguration)new YamlConfiguration();
	    try {
	      this.config.load(this.file);
	    } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
	      e.printStackTrace();
	    } 
	  }
	  
	  public FileConfiguration getConfig() {
	    return this.config;
	  }
	  
	  public File getFile() {
	    return this.file;
	  }
	  
	  public void save() {
	    try {
	      this.config.save(this.file);
	    } catch (Exception exception) {}
	  }
	

}
