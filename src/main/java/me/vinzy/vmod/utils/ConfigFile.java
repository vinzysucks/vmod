package me.vinzy.vmod.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Loader;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigFile {

    public static int blink_Key = Keyboard.KEY_NONE;
    public static int saveGui_Key = Keyboard.KEY_NONE;
    public static int ghostBlock_Key = Keyboard.KEY_NONE;
    public static int restoreGhosts_Key = Keyboard.KEY_NONE;
    public static int reopenGui_Key = Keyboard.KEY_NONE;
    public static int toggleTouch_Key = Keyboard.KEY_NONE;
    public static int printNbt_Key = Keyboard.KEY_NONE;

    private static void newKeybindConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "vmod_keybinds.json");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("blink", Keyboard.KEY_B);
        jsonObject.addProperty("savegui", Keyboard.KEY_F6);
        jsonObject.addProperty("ghostblock", Keyboard.KEY_G);
        jsonObject.addProperty("reopengui", Keyboard.KEY_V);
        jsonObject.addProperty("restoreghosts", Keyboard.KEY_NONE);
        jsonObject.addProperty("toggletouch", Keyboard.KEY_J);
        jsonObject.addProperty("printnbt", Keyboard.KEY_BACKSLASH);
        try {
            FileWriter config = new FileWriter(configFile);
            config.write(jsonObject.toString());
            config.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkForConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "vmod_keybinds.json");
        if (!configFile.exists()) newKeybindConfig();
        try (FileReader reader = new FileReader(configFile)) {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(reader);
            if (!jsonElement.isJsonObject()) {
                System.out.println("Invalid config file");
                return;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            blink_Key = jsonObject.has("blink") ? jsonObject.get("blink").getAsInt() : Keyboard.KEY_NONE;
            saveGui_Key = jsonObject.has("savegui") ? jsonObject.get("savegui").getAsInt() : Keyboard.KEY_NONE;
            ghostBlock_Key = jsonObject.has("ghostblock") ? jsonObject.get("ghostblock").getAsInt() : Keyboard.KEY_NONE;
            restoreGhosts_Key = jsonObject.has("restoreghosts") ? jsonObject.get("restoreghosts").getAsInt() : Keyboard.KEY_NONE;
            reopenGui_Key = jsonObject.has("reopengui") ? jsonObject.get("reopengui").getAsInt() : Keyboard.KEY_NONE;
            toggleTouch_Key = jsonObject.has("toggletouch") ? jsonObject.get("toggletouch").getAsInt() : Keyboard.KEY_NONE;
            printNbt_Key = jsonObject.has("printnbt") ? jsonObject.get("printnbt").getAsInt() : Keyboard.KEY_NONE;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
