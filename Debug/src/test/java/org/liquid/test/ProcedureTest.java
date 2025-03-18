package org.liquid.test;

import org.cdc.imy.IMYMain;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class ProcedureTest {
    private static final Logger LOG = Logger.getLogger("ProceduresTest");

    @Test
    public void testProcedureLangFile() throws IOException {
        var lang = IMYMain.class.getResourceAsStream("/lang/texts.properties");
        var properties = new Properties();
        properties.load(lang);

        var iterator = new File("src\\main\\resources\\procedures");
        if (!iterator.exists()) return;
        for (File element: Objects.requireNonNull(iterator.listFiles())){
            var keyName =element.getName().replace(".json","").replace("$","");
            String key;
            if (element.getName().startsWith("$")){
                key = "blockly.category."+keyName;
            } else {
                key = "blockly.block." + keyName;
            }
            if (!properties.containsKey(key)){
                throw new RuntimeException("缺少键值"+key);
            }
        }
    }
    @Test
    public void testTriggerLangFile() throws IOException {
        var lang = IMYMain.class.getResourceAsStream("/lang/texts.properties");
        var properties = new Properties();
        properties.load(lang);

        var iterator = new File("src\\main\\resources\\triggers");
        if (!iterator.exists()) return;
        for (File element: Objects.requireNonNull(iterator.listFiles())){
            var keyName =element.getName().replace(".json","");
            String key = "trigger."+keyName;
            if (!properties.containsKey(key)){
                throw new RuntimeException("缺少键值"+key);
            }
        }
    }

    @Test
    public void testAiTasksLangFile() throws IOException {
        var lang = IMYMain.class.getResourceAsStream("/lang/texts.properties");
        var properties = new Properties();
        properties.load(lang);

        var iterator = new File("src\\main\\resources\\aitasks");
        if (!iterator.exists()) return;
        for (File element: Objects.requireNonNull(iterator.listFiles())){
            var keyName =element.getName().replace(".json","").replace("$","");
            String key;
            if (element.getName().startsWith("$")){
                key = "blockly.category."+keyName;
            } else {
                key = "blockly.block." + keyName;
            }
            if (!properties.containsKey(key)){
                throw new RuntimeException("缺少键值"+key);
            }
        }
    }
}
