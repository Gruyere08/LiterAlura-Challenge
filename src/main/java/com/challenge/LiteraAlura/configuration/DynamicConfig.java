package com.challenge.LiteraAlura.configuration;

import java.io.*;
import java.util.Properties;

public class DynamicConfig {
    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();

    static {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            createDefaultConfig(configFile);
        }
        loadConfig();
    }

    // Load properties from the configuration file
    private static void loadConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }

    // Create a default configuration file with default parameters
    private static void createDefaultConfig(File configFile) {
        System.out.println("No se encontró el archivo de configuraciones, creando un archivo por defecto...");
        properties.setProperty("cantidad_opciones", "5");
        properties.setProperty("ocultarAutoresSinLibros", "true");
        properties.setProperty("ocultarLibrosSinAutores", "true");


        // Write the default properties to the new file
        try (OutputStream output = new FileOutputStream(configFile)) {
            properties.store(output, "Default configuration");
        } catch (IOException e) {
            System.err.println("Error creating the default configuration file: " + e.getMessage());
        }
    }

    // Get a property value
    public static String get(String key) {
        return properties.getProperty(key);
    }

    // Set a property value
    public static void set(String key, String value) {
        properties.setProperty(key, value);
    }

    // Save the properties back to the configuration file
    public static void save() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, "Updated configurations");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    // Reload the configuration from the file
    public static void reload() {
        loadConfig();
    }
}