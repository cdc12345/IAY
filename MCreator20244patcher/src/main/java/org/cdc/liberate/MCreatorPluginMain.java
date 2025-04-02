package org.cdc.liberate;

import net.mcreator.io.OS;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.events.ApplicationLoadedEvent;
import net.mcreator.plugin.events.WorkspaceSelectorLoadedEvent;
import net.mcreator.util.DesktopUtils;
import org.cdc.liberate.utils.MetaData;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class MCreatorPluginMain extends JavaPlugin {

    private final boolean noSense = true;

    public MCreatorPluginMain(Plugin plugin) {
        super(plugin);
        final var liberate = Liberate.getInstance();
        if (liberate.isNotAgentMod()) {
            if (OS.getOS() == OS.WINDOWS) {
                liberate.getMcreatorPluginLogger().info("Working Directory: {}", new File("").getAbsolutePath());
                this.addListener(ApplicationLoadedEvent.class, a -> {
                    try {
                        if (liberate.isDevEnv()) {
                            liberate.getMcreatorPluginLogger().warn("Running in the dev environment,sleep");
                        } else {
                            Path mcreatorBat = Path.of(MetaData.LAUNCHER_FILE_NAME);
                            Path mcreatorABat = Path.of(MetaData.PURE_LAUNCHER_FILE_NAME);
                            Files.copy(Objects.requireNonNull(PluginLoader.INSTANCE.getResourceAsStream(MetaData.PURE_LAUNCHER_FILE_NAME)), mcreatorABat, StandardCopyOption.REPLACE_EXISTING);
                            Files.copy(Objects.requireNonNull(PluginLoader.INSTANCE.getResourceAsStream(MetaData.CONFIGURATION_FILE_NAME)), mcreatorBat, StandardCopyOption.REPLACE_EXISTING);
                            //Files.copy(plugin.getFile().toPath(),Path.of("agent.jar"), StandardCopyOption.REPLACE_EXISTING);
                            Path configFile = Path.of(MetaData.CONFIGURATION_FILE_NAME);
                            if (!Files.exists(configFile))
                                Files.copy(generateRunningScriptConfigFile(), configFile);
                            Thread.sleep(100);
                            DesktopUtils.openSafe(mcreatorBat.toFile());
                            System.exit(-1);
                        }
                    } catch (Exception ignore) {
                    }
                });
            } else {
                liberate.getMcreatorPluginLogger().warn("Running in not supported system,sleep!");
            }
        } else {
            liberate.setPluginInstance(this);
        }
        addListener(WorkspaceSelectorLoadedEvent.class, a -> {
            if (Liberate.getInstance().isNotAgentMod())
                JOptionPane.showMessageDialog(a.getWorkspaceSelector(), "通用库插入失败,请注意不要让安装路径出现中文.", "Liberate " + this.getPlugin().getInfo().getVersion(), JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private InputStream generateRunningScriptConfigFile() throws IOException {
        String content = new String(Objects.requireNonNull(PluginLoader.INSTANCE.getResourceAsStream(MetaData.CONFIGURATION_FILE_NAME)).readAllBytes());
        return new ByteArrayInputStream(String.format(content, this.plugin.getFile().getAbsolutePath()).getBytes(MetaData.DEFAULT_CONFIGURATION_ENCODING));
    }
}
