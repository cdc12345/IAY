package net.nerdypuzzle.forgemixins;

import freemarker.template.Template;
import net.mcreator.element.ModElementType;
import net.mcreator.generator.Generator;
import net.mcreator.generator.GeneratorFlavor;
import net.mcreator.generator.template.InlineTemplatesHandler;
import net.mcreator.generator.template.base.BaseDataModelProvider;
import net.mcreator.io.FileIO;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.WorkspaceBuildStartedEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.nerdypuzzle.forgemixins.element.Mixin;
import net.nerdypuzzle.forgemixins.element.MixinGUI;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import static net.mcreator.element.ModElementTypeLoader.register;

public class MixinPluginMain extends JavaPlugin{
    public static final Logger LOG = LogManager.getLogger("Mixin");

    public static ModElementType<Mixin> mixinModElementType;

    public MixinPluginMain(Plugin plugin) {
        super(plugin);
        JavaPlugin parent = this;
		parent.addListener(MCreatorLoadedEvent.class, event -> {
			Generator currentGenerator = event.getMCreator().getGenerator();
			if (currentGenerator != null) {
				var generatorConfig = currentGenerator.getGeneratorConfiguration();

				if (generatorConfig.getGeneratorFlavor() == GeneratorFlavor.FORGE) {
                    MixinGUI.regenerateMixins(event.getMCreator().getWorkspace());
					Set<String> fileNames = PluginLoader.INSTANCE.getResourcesInPackage(currentGenerator.getGeneratorName() + ".templates.modbase");
					Map<String, Object> dataModel = (new BaseDataModelProvider(event.getMCreator().getWorkspace().getGenerator())).provide();
                    LOG.info(fileNames);
                    for (String file : fileNames) {
                        if (file.endsWith("build1.gradle")|| file.endsWith("settings.gradle")) {
                            LOG.info(file);
                            InputStream stream = PluginLoader.INSTANCE.getResourceAsStream(file);
                            File generatorFile = new File(event.getMCreator().getWorkspace().getWorkspaceFolder(), file.replace(currentGenerator.getGeneratorName() + "/templates/modbase/", "").replace("build1","build"));
                            try {
                                String contents = IOUtils.toString(stream, StandardCharsets.UTF_8);
                                Template freemarkerTemplate = InlineTemplatesHandler.getTemplate(contents);
                                StringWriter stringWriter = new StringWriter();
                                freemarkerTemplate.process(dataModel, stringWriter, InlineTemplatesHandler.getConfiguration().getObjectWrapper());
                                FileIO.writeStringToFile(stringWriter.getBuffer().toString(), generatorFile);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
				}
			}
		});

		parent.addListener(PreGeneratorsLoadingEvent.class, event -> {
            mixinModElementType = new ModElementType<>("mixin", 'M', MixinGUI::new, Mixin.class);
			register(mixinModElementType);
		});

        parent.addListener(WorkspaceBuildStartedEvent.class, a-> MixinGUI.regenerateMixins(a.getMCreator().getWorkspace()));

		LOG.info("Mixins plugin was loaded");
	}



}