package org.cdc.datagen.core;

import org.cdc.framework.MCreatorPluginFactory;
import org.cdc.framework.utils.*;

public class CoreGenerator {

    public static void main(String[] args) {
        MCreatorPluginFactory pluginFactory = MCreatorPluginFactory.createFactory("Core/src/main/resources");

        pluginFactory.createDataList().setName("effect_categories").appendElement("BENEFICIAL").appendElement("HARMFUL").appendElement("NEUTRAL").initGenerator().buildAndOutput();

        pluginFactory.createProcedure("effect_get_category").setOutput(BuiltInTypes.String).setInputsInline(true)
                .setColor(BuiltInBlocklyColor.TEXTS.toString()).appendArgs0InputValue("key","Object")
                .setCategory("entitydata").setGroup("name").appendArgs0FieldDataListSelector("value","effect_categories","NEUTRAL").initGenerator().buildAndOutput();

        pluginFactory.initGenerator(Generators.FORGE1201);
    }
}
