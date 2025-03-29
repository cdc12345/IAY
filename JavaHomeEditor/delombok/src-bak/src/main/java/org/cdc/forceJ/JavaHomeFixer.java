package org.cdc.forceJ;

import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.ApplicationLoadedEvent;
import net.mcreator.preferences.PreferencesManager;
import net.mcreator.preferences.entries.StringEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class JavaHomeFixer extends JavaPlugin {

    private static final Logger LOG = LogManager.getLogger("Java Home Fixer");
    public static Properties javaHome = null;

    private StringEntry java_home;

    public JavaHomeFixer(Plugin plugin) throws IOException {
        super(plugin);

        java_home = new JavaHomeEntry("java_home");
        addListener(ApplicationLoadedEvent.class,a->{
            PreferencesManager.PREFERENCES.gradle.addPluginEntry("javaHomeFixer",java_home);
        });
    }

    private void fix(File fl) throws IOException {
        String str =new String(Files.readAllBytes(fl.toPath()));
        Files.copy(new ByteArrayInputStream(str.replace('\\','/').getBytes(StandardCharsets.UTF_8)),fl.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}
