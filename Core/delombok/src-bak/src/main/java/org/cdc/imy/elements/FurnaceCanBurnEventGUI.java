package org.cdc.imy.elements;

import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.laf.themes.Theme;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class FurnaceCanBurnEventGUI extends ModElementGUI<DefaultElement> {


    public FurnaceCanBurnEventGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        this.initGUI();
        this.finalizeGUI();
    }

    @Override
    protected void initGUI() {
        JPanel jPanel = new JPanel(new GridLayout(1,2));
        JLabel jLabel = new JLabel("No Tips");
        jPanel.add(PanelUtils.pullElementUp(jLabel));
        jLabel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Theme.current().getForegroundColor(), 1),
                "Tips", 0, 0, jLabel.getFont().deriveFont(12.0f),
                Theme.current().getForegroundColor()));
        addPage(PanelUtils.totalCenterInPanel(jPanel));
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void openInEditingMode(DefaultElement generatableElement) {

    }

    @Override
    public DefaultElement getElementFromGUI() {
        var result = new DefaultElement(modElement);
        result.group = modElement.getName();
        return result;
    }
}
