package org.cdc.imy.elements;

import net.mcreator.blockly.data.Dependency;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class TargetFilterEventGUI extends ModElementGUI<DefaultElement> {
    private ProcedureSelector onHandler;
    private JComboBox<String> defaultFilterResult;

    public TargetFilterEventGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        this.initGUI();
        this.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel jPanel = new JPanel(new GridLayout(1,2));
        JLabel jLabel = new JLabel("1是IAY:Nearest");
        jPanel.add(PanelUtils.pullElementUp(jLabel));
        jLabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                "Tips", 0, 0, jLabel.getFont().deriveFont(12.0f),
                Theme.current().getForegroundColor()));
        JPanel controls = new JPanel(new GridLayout(1,2));
        defaultFilterResult = new JComboBox<>(new String[]{"ALLOW","DENY"});
        controls.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                "Properties", 0, 0, controls.getFont().deriveFont(12.0f),
                Theme.current().getForegroundColor()));
        defaultFilterResult.setSelectedIndex(1);
        controls.add(new JLabel("Default Filter Result:"));
        controls.add(defaultFilterResult);
        jPanel.add(PanelUtils.pullElementUp(controls));
        addPage(PanelUtils.totalCenterInPanel(jPanel));
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void openInEditingMode(DefaultElement generatableElement) {
        if (generatableElement.defaultFilterResult != null){
            this.defaultFilterResult.setSelectedItem(generatableElement.defaultFilterResult);
        }
    }

    @Override
    public DefaultElement getElementFromGUI() {
        var result = new DefaultElement(modElement);
        result.group = modElement.getName();
        result.defaultFilterResult = (String) defaultFilterResult.getSelectedItem();
        return result;
    }
}
