package org.liquid.convenient;

import com.google.gson.*;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.ModElementTypeLoader;
import net.mcreator.element.types.CustomElement;
import net.mcreator.generator.GeneratorTemplate;
import net.mcreator.java.CodeCleanup;
import net.mcreator.java.ImportFormat;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.Plugin;
import net.mcreator.plugin.events.workspace.MCreatorLoadedEvent;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.init.L10N;
import net.mcreator.ui.workspace.WorkspacePanel;
import net.mcreator.workspace.elements.IElement;
import net.mcreator.workspace.elements.ModElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.liquid.convenient.render.TilesModListRender;
import org.liquid.convenient.utils.JsonUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class TransferMain extends JavaPlugin {

    public static final Logger LOG = LogManager.getLogger("TransferMain");

    private JsonObject descMap = new JsonObject();

    public TransferMain(Plugin plugin) {
        super(plugin);

        this.addListener(MCreatorLoadedEvent.class, a -> {
            var mcreator = a.getMCreator();
            var bar = mcreator.getMainMenuBar();

            JMenu transfer = new JMenu();
            transfer.setText("Tool");
            //shallow copy
            JMenuItem toSerializable = buildShallowCopyMenu(mcreator);
            JMenuItem replaceSelectedElement = buildSelectToReplace(mcreator);
            //Deep Copy
            JMenuItem multipleToSerializable = buildDeepCopyMenu(mcreator);
            JMenuItem create = buildUnpackMenu(mcreator);
            //Comment
            JMenuItem setComment = buildAddCommentMenu(mcreator);

            //Language Support
            JMenuItem language = new JMenuItem(L10N.t("mainbar.menu.fromjsontolangall"));
            language.addActionListener(b->{
                String str = JOptionPane.showInputDialog("Input Json Lang");
                if (str == null){
                    return;
                }
                JsonObject lang = new Gson().fromJson(str,JsonObject.class);

                for (Map.Entry<String,JsonElement> entry:lang.entrySet()){
                    mcreator.getWorkspace().setLocalization(entry.getKey(),entry.getValue().getAsString());
                }
            });
            JMenuItem language1 = new JMenuItem(L10N.t("mainbar.menu.loadjsontospecificlang"));
            language1.addActionListener(b->{
                String selected = JOptionPane.showInputDialog("Input Lang");
                String str = JOptionPane.showInputDialog("Input Json Lang");
                if (str == null || selected == null){
                    return;
                }
                JsonObject lang = new Gson().fromJson(str,JsonObject.class);
                var map =mcreator.getWorkspace().getLanguageMap().get(selected);
                LOG.info("Target:{}", selected);

                for (Map.Entry<String,JsonElement> entry:lang.entrySet()){
                    map.put(entry.getKey(),entry.getValue().getAsString());
                }
            });
            //Register
            transfer.add(toSerializable);
            transfer.add(replaceSelectedElement);
            transfer.addSeparator();
            transfer.add(multipleToSerializable);
            transfer.add(create);
            transfer.addSeparator();
            transfer.add(setComment);
            transfer.add(language);
            transfer.add(language1);
            bar.add(transfer);
        });

        this.addListener(MCreatorLoadedEvent.class, a -> {
            var mcreator = a.getMCreator();
            File comments = new File(mcreator.getWorkspaceFolder(),"comments.json");
            descMap = new JsonObject();
            try {
                this.descMap = new Gson().fromJson(new FileReader(comments),JsonObject.class);
            } catch (FileNotFoundException ignored) {}
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                workspacePanel.list.addListSelectionListener(b-> TilesModListRender.updateRenderer(mcreator,TransferMain.this));
                TilesModListRender.updateRenderer(mcreator,this);
            }
        });
    }

    //paste
    private JMenuItem buildSelectToReplace(MCreator mcreator) {
        JMenuItem deserializer = new JMenuItem(L10N.t("mainbar.menu.pastereplace"));
        deserializer.addActionListener(b -> {
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                ModElement element = (ModElement) workspacePanel.list.getSelectedValue();

                if (element == null) {
                    JOptionPane.showMessageDialog(mcreator, L10N.t("common.tip.notselected"));
                    return;
                }

                var manager = element.getModElementManager();

                String input = JOptionPane.showInputDialog("Input your element base64");

                byte[] raw = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));

                String json;
                try {
                    json = new String(JsonUtils.uncompress(raw));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Gson gson = new Gson();
                JsonArray array = new JsonArray();
                try {
                    array = gson.fromJson(json, JsonArray.class);
                } catch (Exception ignored) {
                    array.add(gson.fromJson(json, JsonObject.class));
                }

                if (array == null) {
                    JOptionPane.showMessageDialog(workspacePanel, "Invalid Element");
                    return;
                }
                GeneratableElement generatableElement;
                JsonObject object = array.get(0).getAsJsonObject();
                if (JsonUtils.isRegularPack(object)) {
                    generatableElement = manager.fromJSONtoGeneratableElement(array.get(0).getAsJsonObject().get("content").getAsString(), element);
                    if (generatableElement == null) {
                        generatableElement = element.getGeneratableElement();
                    }
                } else {
                    generatableElement = manager.fromJSONtoGeneratableElement(array.get(0).toString(), element);
                }
                if (generatableElement == null) {
                    JOptionPane.showMessageDialog(mcreator, "Invalid Element");
                    LOG.warn("generatableElement == null");
                    return;
                }

                if (object.has("code")) {
                    element.setCodeLock(true);
                    List<File> modElementFiles = mcreator.getGenerator().getModElementGeneratorTemplatesList(generatableElement)
                            .stream().map(GeneratorTemplate::getFile).toList();

                    CodeCleanup codeCleanup = new CodeCleanup();
                    String code = object.get("code").getAsString();
                    code = codeCleanup.reformatTheCodeAndOrganiseImports(mcreator.getWorkspace(), code);
                    File modElementFile = modElementFiles.getFirst();
                    try {
                        Files.copy(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)), modElementFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ignored) {
                    }
                }
                generatableElement.setModElement(element);
                manager.storeModElement(generatableElement);

                JOptionPane.showMessageDialog(workspacePanel, element.getName() + ":" + json);

                LOG.info("{}:{}", element.getName(), json);
            }
        });
        return deserializer;
    }

    private JMenuItem buildUnpackMenu(MCreator mcreator) {
        JMenuItem replaceSelectedElement = new JMenuItem(L10N.t("mainbar.menu.pastetocreate"));
        replaceSelectedElement.addActionListener(b -> {
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                var manager = mcreator.getModElementManager();

                String input = JOptionPane.showInputDialog("Input your element base64");

                var gson = new Gson();

                JsonArray jsonElements;

                byte[] raw = Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8));

                String json;
                try {
                    json = new String(JsonUtils.uncompress(raw));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    jsonElements = gson.fromJson(json, JsonArray.class);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(workspacePanel, "Invalid Element");
                    return;
                }

                if (jsonElements == null) {
                    JOptionPane.showMessageDialog(workspacePanel, "Invalid Element");
                    return;
                }

                for (JsonElement jsonElement : jsonElements) {
                    var object = jsonElement.getAsJsonObject();
                    if (!JsonUtils.isRegularPack(object)) {
                        JOptionPane.showMessageDialog(workspacePanel, "Invalid Element:" + object);
                        continue;
                    }
                    String name = object.get("name").getAsString();
                    String type1 = object.get("type").getAsString();
                    var type = ModElementTypeLoader.getModElementType(type1);
                    var modElement = new ModElement(mcreator.getWorkspace(), name, type);
                    GeneratableElement generatableElement = manager.fromJSONtoGeneratableElement(object.get("content").getAsString(), modElement);
                    if (object.has("code")) {
                        if (generatableElement == null)
                            generatableElement = modElement.getGeneratableElement();
                        modElement.setCodeLock(true);
                        List<File> modElementFiles = mcreator.getGenerator().getModElementGeneratorTemplatesList(generatableElement)
                                .stream().map(GeneratorTemplate::getFile).toList();

                        CodeCleanup codeCleanup = new CodeCleanup();
                        String code = object.get("code").getAsString();
                        code = codeCleanup.reformatTheCodeAndOrganiseImports(mcreator.getWorkspace(), code);
                        File modElementFile = modElementFiles.getFirst();
                        try {
                            if (!modElementFile.getParentFile().exists()) {
                                if (!modElementFile.getParentFile().mkdirs()){
                                    LOG.error("Dir failed");
                                }
                            }
                            Files.copy(new ByteArrayInputStream(code.getBytes(StandardCharsets.UTF_8)), modElementFile.toPath());
                        } catch (IOException ignored) {
                        }

                    }

                    if (generatableElement == null) {
                        JOptionPane.showMessageDialog(mcreator, "Invalid Element");
                        return;
                    }
                    mcreator.getWorkspace().addModElement(modElement);
                    generatableElement.setModElement(modElement);
                    manager.storeModElement(generatableElement);
                }

                mcreator.reloadWorkspaceTabContents();


                JOptionPane.showMessageDialog(workspacePanel, jsonElements);

                LOG.info(jsonElements.toString());
            }
        });
        return replaceSelectedElement;
    }

    //Copy
    private JMenuItem buildShallowCopyMenu(MCreator mcreator) {
        JMenuItem toSerializable = new JMenuItem(L10N.t("mainbar.menu.copyselected"));
        toSerializable.addActionListener(b -> {
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                ModElement element = (ModElement) workspacePanel.list.getSelectedValue();

                if (element == null) {
                    JOptionPane.showMessageDialog(workspacePanel, L10N.t("common.tip.notselected"));
                    return;
                }

                Gson gson = new Gson();
                String json = mcreator.getWorkspace().getModElementManager().generatableElementToJSON(element.getGeneratableElement());

                if (json == null || element.getGeneratableElement() instanceof CustomElement) {
                    JOptionPane.showMessageDialog(workspacePanel, "Invalid Element");
                    return;
                }

                JsonArray result = new JsonArray();
                result.add(gson.fromJson(json, JsonObject.class));
                JOptionPane.showMessageDialog(workspacePanel, element.getName() + ":" + result);

                byte[] compressed;
                try {
                    compressed = JsonUtils.compress(result.toString(),JsonUtils.GZIP_ENCODE_UTF_8);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String base64 = Base64.getEncoder().encodeToString(compressed);

                LOG.info("{}:{}", element.getName(), result.toString());

                StringSelection stringSelection = new StringSelection(base64);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });
        return toSerializable;
    }

    private JMenuItem buildDeepCopyMenu(MCreator mcreator) {
        JMenuItem toSerializable = new JMenuItem(L10N.t("mainbar.menu.copyselectedmultiple"));
        toSerializable.addActionListener(b -> {
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                List<IElement> elements = workspacePanel.list.getSelectedValuesList();

                if (elements == null || elements.isEmpty()) {
                    JOptionPane.showMessageDialog(mcreator, L10N.t("common.tip.notselected"));
                    return;
                }
                JsonArray jsonElements = new JsonArray();

                for (IElement element : elements) {
                    if (element instanceof ModElement element1) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.add("name", new JsonPrimitive(element.getName()));
                        String type = element1.getType().getRegistryName();
                        jsonObject.add("type", new JsonPrimitive(type));
                        if (List.of("code", "mixin").contains(type)) {
                            List<File> modElementFiles = mcreator.getGenerator().getModElementGeneratorTemplatesList(element1.getGeneratableElement())
                                    .stream().map(GeneratorTemplate::getFile).toList();

                            File modElementFile = modElementFiles.getFirst();

                            String code;
                            try {
                                code = new String(Files.readAllBytes(modElementFile.toPath()));
                                code = ImportFormat.removeImports(code, "");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            jsonObject.add("code", new JsonPrimitive(code));
                        }
                        jsonObject.add("content", new JsonPrimitive(mcreator.getWorkspace().getModElementManager()
                                .generatableElementToJSON(element1.getGeneratableElement())));
                        jsonElements.add(jsonObject);
                    }
                }
                JOptionPane.showMessageDialog(workspacePanel, jsonElements);

                byte[] compressed;
                try {
                    compressed = JsonUtils.compress(jsonElements.toString(),null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String base64 = Base64.getEncoder().encodeToString(compressed);

                LOG.info(jsonElements.toString());

                StringSelection stringSelection = new StringSelection(base64);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
            }
        });
        return toSerializable;
    }

    //More
    private JMenuItem buildAddCommentMenu(MCreator mcreator) {
        JMenuItem comment = new JMenuItem(L10N.t("mainbar.menu.addcomment"));
        comment.addActionListener(a -> {
            String comment1 = JOptionPane.showInputDialog("Input your comment");
            if (mcreator.workspaceTab.getContent() instanceof WorkspacePanel workspacePanel) {
                IElement element = workspacePanel.list.getSelectedValue();
                if (element instanceof ModElement modElement) {
                    descMap.add(modElement.getName(), new JsonPrimitive(comment1));
                    workspacePanel.list.setCellRenderer(new TilesModListRender(this));
                    File comments = new File(mcreator.getWorkspaceFolder(),"comments.json");
                    try {
                        Files.copy(new ByteArrayInputStream(descMap.toString().getBytes(StandardCharsets.UTF_8)),comments.toPath(),StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return comment;
    }
    public String getOrDefault(String key,String defaultValue){
        if (descMap.has(key)){
            return descMap.get(key).getAsString();
        } else {
            return defaultValue;
        }
    }
}
