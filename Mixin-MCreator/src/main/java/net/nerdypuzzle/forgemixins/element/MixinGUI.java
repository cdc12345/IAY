package net.nerdypuzzle.forgemixins.element;

import freemarker.template.Template;
import net.mcreator.generator.Generator;
import net.mcreator.generator.template.InlineTemplatesHandler;
import net.mcreator.io.FileIO;
import net.mcreator.plugin.PluginLoader;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.ui.validation.component.VTextField;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class MixinGUI extends ModElementGUI<Mixin> {
    private JCheckBox client;
    private VTextField target;

    public MixinGUI(MCreator mcreator, ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);
        setModElementCreatedListener(a->{
                a.getModElement().setCodeLock(true);
                regenerateMixins(mcreator.getWorkspace());
        });
        this.initGUI();
        this.finalizeGUI();
    }

    public static void regenerateMixins(Workspace current) {
        Generator currentGenerator = current.getGenerator();
        Map<String, Object> dataModel = currentGenerator.getBaseDataModelProvider().provide();
        var file = PluginLoader.INSTANCE.getResourceAsStream(currentGenerator.getGeneratorName() + "/templates/modbase/mixins.json.ftl");
        File mixins = new File(currentGenerator.getResourceRoot(), "mixins." + current.getWorkspaceSettings().getModID() + ".json");
        mixins.delete();
        try {
            String contents = IOUtils.toString(file, StandardCharsets.UTF_8);
            Template freemarkerTemplate = InlineTemplatesHandler.getTemplate(contents);
            StringWriter stringWriter = new StringWriter();
            freemarkerTemplate.process(dataModel, stringWriter, InlineTemplatesHandler.getConfiguration().getObjectWrapper());
            FileIO.writeStringToFile(stringWriter.getBuffer().toString(), mixins);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void initGUI() {
        JPanel page = new JPanel(new BorderLayout(10,10));
        JPanel control = new JPanel(new GridLayout(2,2,10,10));
        target = new VTextField();
        target.setText(modElement.getName());
        control.add(new JLabel("Mixin Target: "));
        control.add(target);
        client = new JCheckBox("Click to enable");
        control.add(new JLabel("Is Client: "));
        control.add(client);
        control.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                "Properties", 0, 0, control.getFont().deriveFont(12.0f),
                Theme.current().getForegroundColor()));
        page.add("Center", PanelUtils.totalCenterInPanel(control));
        this.addPage(page);
    }

    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    public void openInEditingMode(Mixin generatableElement) {
        client.setSelected(generatableElement.isClient);
        if (generatableElement.mixinClass != null) {
            target.setText(generatableElement.mixinClass);
        }
    }

    @Override
    public Mixin getElementFromGUI() {
        var mixin = new Mixin(modElement);
        mixin.isClient = client.isSelected();
        mixin.mixinClass = target.getText();
        return mixin;
    }

    @Override
    protected boolean allowCodePreview() {
        return false;
    }
}
