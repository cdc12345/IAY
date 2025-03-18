package org.cdc.liberate;

import net.mcreator.plugin.JavaPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cdc.liberate.transfer.AgentClassTransfer;
import org.cdc.liberate.transfer.ClassVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarFile;

public class Liberate {
    private static Liberate instance;

    public static Liberate getInstance() {
        if (instance == null) instance = new Liberate();
        return instance;
    }

    private boolean agentMod;
    private boolean safeMod;

    private Logger mcreatorPluginLogger;

    protected AgentClassTransfer agentClassTransfer;

    private Properties mappings;
    private JavaPlugin pluginInstance;

    private JarFile agentJar;


    private Liberate() {
    }

    public boolean isDevEnv() {
        return !Files.exists(Path.of("mcreator.exe"));
    }

    public Logger getMcreatorPluginLogger() {
        if (mcreatorPluginLogger == null) mcreatorPluginLogger = LogManager.getLogger("Liberate-Plugin");
        return mcreatorPluginLogger;
    }

    public List<ClassVisitor> getClassVisitors() {
        return agentClassTransfer.getVisitors();
    }

    public boolean isAgentMod() {
        return this.agentMod;
    }

    public boolean isNotAgentMod() {
        return !isAgentMod();
    }

    public boolean isSafeMod() {
        return this.safeMod;
    }

    public AgentClassTransfer getAgentClassTransfer() {
        return this.agentClassTransfer;
    }

    public void setAgentMod(boolean agentMod) {
        this.agentMod = agentMod;
    }

    public void setSafeMod(boolean safeMod) {
        this.safeMod = safeMod;
    }

    public Properties getMappings() throws IOException {
        if (mappings == null) {
            mappings = new Properties();
            mappings.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mappings.properties"));
        }
        return mappings;
    }

    public JavaPlugin getPluginInstance() {
        return this.pluginInstance;
    }

    public JarFile getAgentJar() {
        return this.agentJar;
    }

    public void setPluginInstance(JavaPlugin pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    public void setAgentJar(JarFile agentJar) {
        this.agentJar = agentJar;
    }
}
