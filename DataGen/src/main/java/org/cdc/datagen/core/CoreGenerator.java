package org.cdc.datagen.core;

import org.cdc.framework.MCreatorPluginFactory;
import org.cdc.framework.builder.LanguageBuilder;
import org.cdc.framework.utils.*;

import java.util.Map;

public class CoreGenerator {

	public static void main(String[] args) {

		MCreatorPluginFactory pluginFactory = MCreatorPluginFactory.createFactory("Core/src/main/resources");

		LanguageBuilder en = pluginFactory.createDefaultLanguage();

		pluginFactory.createDataList().setName("effect_categories").appendElement("BENEFICIAL").appendElement("HARMFUL")
				.appendElement("NEUTRAL").initGenerator().buildAndOutput();

		pluginFactory.createProcedure("effect_get_category").setOutput(BuiltInTypes.Boolean).setInputsInline(true)
				.setColor(BuiltInBlocklyColor.LOGIC.toString()).appendArgs0InputValue("key", "Object")
				.setCategory("entitydata").setGroup("name")
				.appendArgs0FieldDataListSelector("value", "effect_categories", "NEUTRAL").initGenerator()
				.buildAndOutput();

		pluginFactory.getToolKit().createProcedureWithEntityIterator("entity_spawn_new","foreach")
				.setToolBoxId(BuiltInToolBoxId.Procedure.ENTITY_MANAGEMENT)
				.appendArgs0InputValue("x", BuiltInTypes.Number).appendArgs0InputValue("y", BuiltInTypes.Number)
				.appendArgs0InputValue("z", BuiltInTypes.Number)
				.appendArgs0FieldDataListSelector("entity", "spawnableEntity", "EntityMinecart")
				.toolBoxInitBuilder().setName("_placeholder")
				.appendEntityIterator().buildAndReturn().setColor(35).initGenerator()
				.setLanguage(en, "spawn entity %4 in x:%1 y:%2 z:%3, %5: %6").toolBoxInitBuilder().setName("x")
				.appendReferenceBlock("coord_x").buildAndReturn().toolBoxInitBuilder().setName("y")
				.appendReferenceBlock("coord_y").buildAndReturn().toolBoxInitBuilder().setName("z")
				.appendReferenceBlock("coord_z").buildAndReturn().appendDependency("world", BuiltInTypes.World)
				.buildAndOutput();

		pluginFactory.createProcedure("entity_get_invulnerabletime").setOutput(BuiltInTypes.Number)
				.setInputsInline(true).appendArgs0InputValue("entity", BuiltInTypes.Entity)
				.setColor(BuiltInBlocklyColor.MATH.toString()).toolBoxInitBuilder().setName("entity")
				.appendDefaultEntity().buildAndReturn().setLanguage(en, "get invulnerableTime of %1")
				.setCategory(BuiltInToolBoxId.Procedure.ENTITY_DATA).initGenerator().buildAndOutput();
		BuilderUtils.createCommonProcedure(pluginFactory, "entity_set_invulnerabletime")
				.appendArgs0InputValue("entity", BuiltInTypes.Entity).setColor(BuiltInBlocklyColor.TEXTS.toString())
				.appendArgs0InputValue("value", BuiltInTypes.Number).toolBoxInitBuilder().setName("entity")
				.appendDefaultEntity().buildAndReturn().toolBoxInitBuilder().setName("value").appendConstantNumber(0)
				.buildAndReturn().setCategory(BuiltInToolBoxId.Procedure.ENTITY_MANAGEMENT).initGenerator()
				.setLanguage(en, "set entity %1 invulnerableTime to %2").buildAndOutput();
		pluginFactory.getToolKit().createOutputProcedure("entity_get_spawn_type", BuiltInTypes.Boolean)
				.appendArgs0InputValue("entity", BuiltInTypes.Entity).appendArgs0FieldDropDown("spawn_type",
						Map.ofEntries(Map.entry("NATURAL", "NATURAL"), Map.entry("CHUNK_GENERATION", "CHUNK_GENERATION"),
								Map.entry("SPAWNER", "SPAWNER"), Map.entry("STRUCTURE", "STRUCTURE"),
								Map.entry("BREEDING", "BREEDING"), Map.entry("MOB_SUMMONED", "MOB_SUMMONED"),
								Map.entry("JOCKEY", "JOCKEY"), Map.entry("EVENT", "EVENT"),
								Map.entry("CONVERSION", "CONVERSION"), Map.entry("REINFORCEMENT", "REINFORCEMENT"),
								Map.entry("TRIGGERED", "TRIGGERED"), Map.entry("BUCKET", "BUCKET"),
								Map.entry("SPAWN_EGG", "SPAWN_EGG"), Map.entry("COMMAND", "COMMAND"),
								Map.entry("DISPENSER", "DISPENSER"), Map.entry("PATROL", "PATROL")))
				.setToolBoxId(BuiltInToolBoxId.Procedure.ENTITY_DATA).toolBoxInitBuilder().setName("entity")
				.appendDefaultEntity().buildAndReturn().setColor(BuiltInBlocklyColor.LOGIC.toString()).initGenerator()
				.setLanguage(en, "the spawn type of %1 is %2").buildAndOutput();

		pluginFactory.createTrigger().setName("block_replace").setSide(Side.Server).setCancelable(true)
				.appendDependency("world", BuiltInTypes.World).appendDependency("entity", BuiltInTypes.Entity)
				.appendDependency("blockstate", BuiltInTypes.BlockState)
				.appendDependency("placedagainst", BuiltInTypes.BlockState).appendDependency("x", BuiltInTypes.Number)
				.appendDependency("y", BuiltInTypes.Number).appendDependency("z", BuiltInTypes.Number)
				.appendDependency("px", BuiltInTypes.Number).appendDependency("py", BuiltInTypes.Number)
				.appendDependency("pz", BuiltInTypes.Number).appendDependency("oldblockstate", BuiltInTypes.BlockState)
				.setLanguage(en, "IAY: Block replace").initGenerator().buildAndOutput();

		en.buildAndOutput();

		pluginFactory.initGenerator(Generators.FORGE1201);
	}
}
