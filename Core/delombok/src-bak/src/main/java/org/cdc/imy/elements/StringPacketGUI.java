package org.cdc.imy.elements;

import net.mcreator.blockly.data.Dependency;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.util.PanelUtils;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.modgui.ModElementGUI;
import net.mcreator.ui.procedure.ProcedureSelector;
import net.mcreator.ui.validation.AggregatedValidationResult;
import net.mcreator.workspace.elements.ModElement;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

public class StringPacketGUI extends ModElementGUI<DefaultElement> {
    private ProcedureSelector onHandler;

    public StringPacketGUI(MCreator mcreator, @Nonnull ModElement modElement, boolean editingMode) {
        super(mcreator, modElement, editingMode);

        this.initGUI();
        this.finalizeGUI();
    }

    @Override
    public void reloadDataLists() {
        super.reloadDataLists();
        onHandler.refreshListKeepSelected();
    }

    @Override
    protected void initGUI() {
        onHandler = new ProcedureSelector(this.withEntry("strpacket/onhandler"),mcreator, L10N.t("elementgui.common.onhandle"),
                Dependency.fromString("x:number/y:number/z:number/world:world/entity:entity/msg:string"));

        var triggers = new JPanel(new BorderLayout(10,10));
        var events = new JPanel(new GridLayout(1,1));
        events.setOpaque(false);
        events.add(onHandler);
        triggers.add("Center", PanelUtils.totalCenterInPanel(events));
        triggers.setOpaque(false);
        addPage(L10N.t("elementgui.common.page_triggers"), triggers);
    }

    @Override
    protected AggregatedValidationResult validatePage(int page) {
        return new AggregatedValidationResult.PASS();
    }

    @Override
    protected void openInEditingMode(DefaultElement generatableElement) {
        onHandler.setSelectedProcedure(generatableElement.onHandler);
    }

    @Override
    public DefaultElement getElementFromGUI() {
        var packet = new DefaultElement(modElement);
        packet.onHandler = onHandler.getSelectedProcedure();
        packet.group = modElement.getName();
        return packet;
    }
}
