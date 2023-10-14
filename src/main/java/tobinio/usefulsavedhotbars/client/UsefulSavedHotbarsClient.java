package tobinio.usefulsavedhotbars.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class UsefulSavedHotbarsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */

    public static KeyBinding LoadHotbarsKeyBinding;
    public static KeyBinding SaveHotbarsKeyBinding;

    @Override
    public void onInitializeClient() {
        LoadHotbarsKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.usefulsavedhotbars.load", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F5, "category.usefulsavedhotbars.savedhotbar"));
        SaveHotbarsKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.usefulsavedhotbars.save", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.usefulsavedhotbars.savedhotbar"));
    }
}
