package org.cdc.imy.gen;

import org.cdc.framework.MCreatorPlugin;
import org.cdc.framework.utils.Side;
import org.cdc.framework.utils.StandardTypes;

import java.io.File;

public class DataGen {
    public static void main(String[] args) {
        MCreatorPlugin mCreatorPlugin = new MCreatorPlugin(new File("Core/src/main/resources"));
        System.out.println(mCreatorPlugin.rootPath().getAbsolutePath());

        var language = mCreatorPlugin.createDefaultLanguage();
        mCreatorPlugin.createProcedure().setName("list").markType().setColor(56).buildAndOutput();
        mCreatorPlugin.createProcedure().setName("targets").markType().setColor(195).setParentCategory("entityprocedures").buildAndOutput();
        mCreatorPlugin.createProcedure().setName("unsafe").markType().setColor(251).buildAndOutput();
        mCreatorPlugin.createProcedure().setName("recipe_remove").setColor(251).setInputsInline(true).setPreviousStatement(null).setNextStatement(null)
                .appendArgs0InputValue("name", StandardTypes.String.getHigherName()).setGroup("name").setToolBoxId("unsafe")
                .appendToolBoxInit("<value name=\"name\"><block type=\"text\"><field name=\"TEXT\">minecraft:diamond</field></block></value>")
                .appendDependency("world", "world").setLanguage(language, "Remove recipes whose name is %1").buildAndOutput();

        mCreatorPlugin.createDataList().setName("dimensions").appendElement("主世界").appendElement("下界").appendElement("末地").buildAndOutput();

        mCreatorPlugin.createTrigger().setName("tab_item_salt").appendDependency("tabname", StandardTypes.String.getLowerName()).appendDependency("itemstack",StandardTypes.ItemStack.getLowerName()).appendDependency("world","world").setSide(Side.Client).buildAndOutput();

        language.buildAndOutput();

        mCreatorPlugin.createVariable().setName("object").setBlocklyVariableType("Object").setIgnoredByCoverage(true).setNullable(true).setColor(355).buildAndOutput();
    }
}
