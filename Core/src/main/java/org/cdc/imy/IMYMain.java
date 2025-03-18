package org.cdc.imy;

import net.mcreator.element.ModElementType;
import net.mcreator.element.ModElementTypeLoader;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.PreGeneratorsLoadingEvent;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.ui.MCreator;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.FolderElement;
import net.mcreator.workspace.elements.ModElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cdc.imy.elements.DefaultElement;
import org.cdc.imy.elements.FurnaceCanBurnEventGUI;
import org.cdc.imy.elements.StringPacketGUI;
import org.cdc.imy.elements.TargetFilterEventGUI;

public class IMYMain extends JavaPlugin {

    public static final Logger LOG = LogManager.getLogger("IMY");
    private static ModElementType<DefaultElement> defaultModElement;

    public IMYMain(Plugin plugin) {
        super(plugin);

        //注册占位符元素
        this.addListener(PreGeneratorsLoadingEvent.class,event -> {
            defaultModElement = new ModElementType<>("strpacket",null, (a, b, c)->{
                if (b.getName().equals("StringPacket")){
                    return new StringPacketGUI(a,b,c);
                } else if (b.getName().equals("TargetFilterEvent")){
                    return new TargetFilterEventGUI(a,b,c);
                } else if (b.getName().equals("FurnaceCanBurnEvent")){
                    return new FurnaceCanBurnEventGUI(a,b,c);
                }
                return null;
            }, DefaultElement.class);
            ModElementTypeLoader.register(defaultModElement);
        });

        //添加默认元素
        this.addListener(MCreatorLoadedEvent.class, event -> {
            var mcreator = event.getMCreator();
            var workspace = mcreator.getWorkspace();

            if (workspace.getGeneratorConfiguration().getDefinitionsProvider().getModElementDefinition(defaultModElement) == null){
                return;
            }
            var foldElement = new FolderElement("Defaults",FolderElement.ROOT);
            if (workspace.getFoldersRoot().getDirectFolderChildren().stream().noneMatch(a-> a.getName().equals("Defaults"))){
                foldElement.moveTo(workspace,workspace.getFoldersRoot());
            }

            addDefaultElement(workspace, mcreator, foldElement,"StringPacket");
            addDefaultElement(workspace,mcreator,foldElement,"TargetFilterEvent");
            addDefaultElement(workspace,mcreator,foldElement,"FurnaceCanBurnEvent");


        });
    }

    private static void addDefaultElement(Workspace workspace, MCreator mcreator, FolderElement foldElement,String name) {
        if (workspace.getModElementByName(name) == null) {
            var modelement = new ModElement(mcreator.getWorkspace(), name, defaultModElement);
            var generate = new DefaultElement(modelement);
            modelement.setParentFolder(foldElement);
            workspace.addModElement(modelement);
            generate.group=name;
            mcreator.getWorkspace().getGenerator().generateElement(generate);
            mcreator.getModElementManager().storeModElement(generate);
            mcreator.reloadWorkspaceTabContents();
        }
    }
}
