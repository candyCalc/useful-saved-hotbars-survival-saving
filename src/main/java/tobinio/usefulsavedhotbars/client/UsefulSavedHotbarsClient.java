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

    public static KeyBinding LoadSaveHotbarsKeyBinding;

    @Override
    public void onInitializeClient() {
        LoadSaveHotbarsKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.usefulsavedhotbars.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_L, "category.usefulsavedhotbars.savedhotbar"));
    }
}
