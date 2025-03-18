package org.cdc.liberate.utils;

import net.mcreator.plugin.EventMap;
import net.mcreator.plugin.JavaPlugin;
import net.mcreator.plugin.PluginLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

public class PluginLoaderUtils {
    public static Collection<JavaPlugin> getJavaPlugins()   {
        try {
            Method method = PluginLoader.class.getDeclaredMethod("getJavaPlugins");
            method.setAccessible(true);
            return (Collection<JavaPlugin>) method.invoke(PluginLoader.INSTANCE);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static EventMap getListeners(JavaPlugin plugin){
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getListeners");
            method.setAccessible(true);
            return (EventMap) method.invoke(plugin);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
