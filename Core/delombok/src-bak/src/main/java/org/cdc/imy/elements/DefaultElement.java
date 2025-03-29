package org.cdc.imy.elements;

import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.workspace.elements.ModElement;

public class DefaultElement extends GeneratableElement {

    public String group = "StringPacket";

    //StringPacket
    //TargetFilterEvent
    public Procedure onHandler;

    //TargetFilterEvent
    public String defaultFilterResult;

    public DefaultElement(ModElement element) {
        super(element);
    }
}
