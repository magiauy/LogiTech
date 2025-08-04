package me.matl114.logitech;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import me.matl114.logitech.utils.AddUtils;
import me.matl114.logitech.utils.Debug;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class Language {
    public static String LANGUAGE_CODE = "en-US"; // Default language code
    public static final HashMap<String ,Object> LANGUAGE=new HashMap<>(){{
    }};
    public static final HashMap<String,String> PLACEHOLDERS=new HashMap<>();
    
    public static HashMap<String ,Object> getLanguage() {
        return LANGUAGE;
    }
    
    public static String get(String key){
        String s=(String)getLanguage().get(key);
        if (s != null) {
            return s;
        } else {
            Debug.logger("Missing language key: " + key);
            return key; // Return the key itself as fallback
        }
    }
    
    @SuppressWarnings("unchecked")
    public static List<String> getList(String key){
        List<String> ls= (List<String>) getLanguage().get(key);
        if (ls != null) {
            return ls;
        } else {
            Debug.logger("Missing language list key: " + key);
            return new ArrayList<>();
        }
    }
    
    public static String getPlaceHolder(String key){
        return PLACEHOLDERS.get("%%%s%%".formatted(key));
    }
    
    public static Object replacePlaceHolder(Object val){
        if(val instanceof String s){
            for(Map.Entry<String,String> entry:PLACEHOLDERS.entrySet()){
                s=s.replace(entry.getKey(),entry.getValue());
            }
            return s;
        }else if(val instanceof List<?> lst){
            return lst.stream().map(Language::replacePlaceHolder).toList();
        }else return val;
    }
    
    /**
     * Load language config from main config file and lang directory
     */
    public static boolean loadConfig(Config mainConfig, Plugin plugin){
        // Clear existing data
        LANGUAGE.clear();
        PLACEHOLDERS.clear();
        
        // Get language code from main config
        String configLang = mainConfig.getString("language");
        LANGUAGE_CODE = (configLang != null) ? configLang : "en-US";
        Debug.logger("Loading language: " + LANGUAGE_CODE);
        
        // Initialize language files (create if not exists)
        initLanguageFiles(plugin);
        
        // Try to load language files from lang directory
        return loadLanguageFiles(plugin);
    }
    
    /**
     * Initialize language files - create lang directory and copy default files if not exists
     */
    private static void initLanguageFiles(Plugin plugin) {
        try {
            // Create lang directory in plugin data folder
            File langDir = new File(plugin.getDataFolder(), "lang");
            if (!langDir.exists()) {
                langDir.mkdirs();
                Debug.logger("Created lang directory: " + langDir.getPath());
            }
            
            // List of default language files to copy from resources
            String[] defaultLanguageFiles = {"en-us.yml", "zh-cn.yml"}; // Add more as needed
            
            for (String fileName : defaultLanguageFiles) {
                File langFile = new File(langDir, fileName);
                
                // If file doesn't exist, copy from resources
                if (!langFile.exists()) {
                    copyLanguageFileFromResources(plugin, fileName, langFile);
                }
            }
            
        } catch (Exception e) {
            Debug.logger("Failed to initialize language files: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Copy language file from plugin resources to external folder
     */
    private static void copyLanguageFileFromResources(Plugin plugin, String fileName, File targetFile) {
        try {
            InputStream resourceStream = plugin.getResource("lang/" + fileName);
            if (resourceStream != null) {
                Files.copy(resourceStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Debug.logger("Copied language file: " + fileName + " to " + targetFile.getPath());
                resourceStream.close();
            } else {
                Debug.logger("Resource not found: lang/" + fileName);
            }
        } catch (Exception e) {
            Debug.logger("Failed to copy language file " + fileName + ": " + e.getMessage());
        }
    }
    
    /**
     * Load language files from lang/{language_code}.yml file
     */
    private static boolean loadLanguageFiles(Plugin plugin) {
        try {
            // Convert language code to lowercase for file name consistency
            String languageFileName = LANGUAGE_CODE.toLowerCase() + ".yml";
            Config languageConfig = loadConfigFile(plugin, languageFileName);
            
            if (languageConfig != null) {
                // Load placeholders first
                loadPlaceholders(languageConfig);
                
                // Load all language data recursively
                loadConfigRecursively(languageConfig, "");
                
                // Apply placeholder replacements
                applyPlaceholders();
                
                Debug.logger("Language config loaded successfully for: " + LANGUAGE_CODE);
                return true;
            } else {
                Debug.logger("Failed to load language file: " + languageFileName);
                return false;
            }
            
        } catch (Exception e) {
            Debug.logger("Failed to load language files for: " + LANGUAGE_CODE);
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Load a specific config file from external lang directory
     */
    private static Config loadConfigFile(Plugin plugin, String fileName) {
        try {
            File langFile = new File(plugin.getDataFolder(), "lang/" + fileName);
            
            if (langFile.exists()) {
                Debug.logger("Loading language file from: " + langFile.getPath());
                return new Config(langFile);
            } else {
                Debug.logger("Language file not found: " + langFile.getPath());
                return null;
            }
        } catch (Exception e) {
            Debug.logger("Could not load " + fileName + " for language: " + LANGUAGE_CODE);
            Debug.logger("Error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load placeholders from placeholder config
     */
    private static void loadPlaceholders(Config config) {
        Set<String> placeHolders = config.getKeys("placeholder");
        for(String placeHolder : placeHolders){
            PLACEHOLDERS.put("%%%s%%".formatted(placeHolder), config.getString("placeholder."+placeHolder));
        }
    }
    
    /**
     * Load config data recursively
     */
    private static void loadConfigRecursively(Config config, String path) {
        Set<String> keys = config.getKeys(path.isEmpty() ? "" : path);
        for (String key : keys) {
            String fullPath = path.isEmpty() ? key : path + "." + key;
            String nextPath = path.isEmpty() ? key : path + "." + key;
            
            // Skip certain keys
            if(key.endsWith("key")||key.endsWith("enable")){
                continue;
            }
            
            // Check if this key has sub-keys
            Set<String> subKeys = config.getKeys(nextPath);
            if (!subKeys.isEmpty()) {
                // This is a section, recurse into it
                loadConfigRecursively(config, nextPath);
            } else {
                // This is a leaf value, store it
                Object value = config.getValue(fullPath);
                if (value != null) {
                    LANGUAGE.put(fullPath, value);
                }
            }
        }
    }
    
    /**
     * Apply placeholder replacements to all loaded language data
     */
    private static void applyPlaceholders() {
        Iterator<Map.Entry<String, Object>> it = LANGUAGE.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            entry.setValue(replacePlaceHolder(entry.getValue()));
        }
    }
    
    /**
     * @deprecated Use loadConfig(Config mainConfig, Plugin plugin) instead
     */
    @Deprecated
    public static boolean loadConfig(Config cfg){
        Set<String> placeHolders=cfg.getKeys("placeholder");
        for(String placeHolder : placeHolders){
            PLACEHOLDERS.put("%%%s%%".formatted(placeHolder),cfg.getString("placeholder."+placeHolder));
        }
        Set<String> keys=cfg.getKeys("en_US");
        if(keys.isEmpty()) return false;
        for (String ns : keys) {
            loadRecursively(cfg,ns);
        }
        Iterator<Map.Entry<String, Object>> it=LANGUAGE.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry=it.next();
            entry.setValue(replacePlaceHolder(entry.getValue()));
        }
        Debug.logger("语言配置加载完毕");
        return true;
    }
    
    /**
     * @deprecated Use loadConfigRecursively instead
     */
    @Deprecated
    public static void loadRecursively(Config config,String path){
        Set<String> keys=config.getKeys(String.join( ".", "en_US",path));
        for (String key : keys) {
            String nextPath=path+"."+key;
            if(key.endsWith("lan")){
                LANGUAGE.put(path,config.getValue(String.join(".", "en_US",nextPath)));
            }else if(key.endsWith("key")||key.endsWith("enable")){

            }else if(key.endsWith("lore")||key.endsWith("name")){
                LANGUAGE.put(AddUtils.concat(path,".",key),config.getValue(String.join(".", "en_US",path,key)));
            }else if(path.equals("gui")){
                // Load gui keys directly
                LANGUAGE.put(AddUtils.concat(path,".",key),config.getValue(String.join(".", "en_US",path,key)));
            }
            else {
                loadRecursively(config,nextPath);
            }
        }
    }
}
