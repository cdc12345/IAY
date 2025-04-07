package org.cdc.datagen.core;

import org.cdc.framework.MCreatorPluginFactory;
import org.cdc.framework.builder.LanguageBuilder;
import org.cdc.framework.utils.*;

public class CoreGenerator {

    public static void main(String[] args) {
        MCreatorPluginFactory pluginFactory = MCreatorPluginFactory.createFactory("Core/src/main/resources");

        LanguageBuilder en = pluginFactory.createDefaultLanguage();

        pluginFactory.createDataList().setName("effect_categories").appendElement("BENEFICIAL").appendElement("HARMFUL").appendElement("NEUTRAL").initGenerator().buildAndOutput();

        pluginFactory.createProcedure("effect_get_category").setOutput(BuiltInTypes.String).setInputsInline(true)
                .setColor(BuiltInBlocklyColor.TEXTS.toString()).appendArgs0InputValue("key","Object")
                .setCategory("entitydata").setGroup("name").appendArgs0FieldDataListSelector("value","effect_categories","NEUTRAL").initGenerator().buildAndOutput();

        pluginFactory.createProcedure("entity_get_invulnerabletime").setOutput(BuiltInTypes.Number).setInputsInline(true)
                .appendArgs0InputValue("entity",BuiltInTypes.Entity).setColor(BuiltInBlocklyColor.MATH.toString()).toolBoxInitBuilder().setName("entity").appendDefaultEntity().buildAndReturn()
                .setLanguage(en,"get invulnerableTime of %1").setCategory(BuiltInToolBoxId.Procedure.ENTITY_DATA).initGenerator().buildAndOutput();
        BuilderUtils.createCommonProcedure(pluginFactory,"entity_set_invulnerabletime")
                .appendArgs0InputValue("entity",BuiltInTypes.Entity).setColor(BuiltInBlocklyColor.TEXTS.toString())
                .appendArgs0InputValue("value",BuiltInTypes.Number).toolBoxInitBuilder().setName("entity").appendDefaultEntity().buildAndReturn().toolBoxInitBuilder().setName("value").appendConstantNumber(0).buildAndReturn().setCategory(BuiltInToolBoxId.Procedure.ENTITY_MANAGEMENT)
                .initGenerator().setLanguage(en,"set entity %1 invulnerableTime to %2").buildAndOutput();

        en.buildAndOutput();

        pluginFactory.initGenerator(Generators.FORGE1201);
    }
}
