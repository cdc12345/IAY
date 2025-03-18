package org.cdc.forceJ;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.mcreator.preferences.PreferencesManager;
import net.mcreator.preferences.entries.StringEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Arrays;
import java.util.EventObject;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

public class JavaHomeEntry extends StringEntry {
    private final LinkedHashSet<String> javaHomes;

    private final String[] constants = new String[]{"Default Java Home","New Java Home"};

    public JavaHomeEntry(String id) {
        super(id, "", false);
        javaHomes = new LinkedHashSet<>();
    }

    @Override
    public JComponent getComponent(Window parent, Consumer<EventObject> fct) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addActionListener(fct::accept);
        comboBox.setModel(new DefaultComboBoxModel<>(){
            @Override
            public String getElementAt(int index) {
                if (index < javaHomes.size()) {
                    return javaHomes.toArray(new String[0])[index];
                } else {
                    int count = index - javaHomes.size();
                    return constants[count];
                }
            }

            @Override
            public int getSize() {
                return javaHomes.size() + 2;
            }

            @Override
            public void setSelectedItem(Object anObject) {
                if ("New Java Home".equals(anObject)) {
                    String home = JOptionPane.showInputDialog("Please Input Your Java Home");
                    if (home!=null && !home.isEmpty()){
                        javaHomes.add(home);
                    }
                } else {
                    if ("Default Java Home".equals(anObject))
                        JavaHomeEntry.this.value = System.getProperty("java.home");
                    super.setSelectedItem(anObject);
                }
            }
        });
        comboBox.setSelectedItem(System.getProperty("java.home").equals(value)?"":value);
        return comboBox;
    }

    @Override
    public void setValueFromJsonElement(JsonElement object) {
        if (object.isJsonObject() && object.getAsJsonObject().has("selected") && object.getAsJsonObject().has("list")) {
            value = object.getAsJsonObject().get("selected").getAsString();
            if (value == null) {
                this.value = System.getProperty("java.home");
            }
            javaHomes.addAll(object.getAsJsonObject().getAsJsonArray("list").asList().stream().filter(a->new File(a.getAsString()).exists()).map(JsonElement::getAsString).toList());
        } else if (object.isJsonArray()){
            this.value = System.getProperty("java.home");
            javaHomes.addAll(object.getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList());
        } else {
            javaHomes.add(object.getAsString());
        }
    }

    @Override
    public void setValueFromComponent(JComponent component) {
        JComboBox<String> comboBox = (JComboBox<String>)component;
        if (Arrays.asList(constants).contains(comboBox.getSelectedItem())){
            return;
        }
        super.setValueFromComponent(component);
    }

    @Override
    public JsonElement getSerializedValue() {
        if (this.value!= null && !this.value.isEmpty()) {
            PreferencesManager.PREFERENCES.hidden.java_home.set(getBinJavaFile(this.value));
        }
        JsonObject jsonObject = new JsonObject();
        if (this.value != null) {
            jsonObject.add("selected",new JsonPrimitive(this.value));
        }
        jsonObject.add("list",new Gson().toJsonTree(javaHomes).getAsJsonArray());
        return jsonObject;
    }

    public File getBinJavaFile(String value){
        var temp = new File(value);
        if (temp.isFile()){
            return temp;
        } else {
            return new File("this.value+\\bin\\java.exe");
        }
    }
}
