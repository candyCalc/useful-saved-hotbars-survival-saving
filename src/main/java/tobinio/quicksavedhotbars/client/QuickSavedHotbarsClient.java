package tobinio.quicksavedhotbars.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class QuickSavedHotbarsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */

    public static KeyBinding QuickSavedHotbarKeyBinding;

    @Override
    public void onInitializeClient() {
        QuickSavedHotbarKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.quicksavedhotbars.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_L, "category.quicksavedhotbars.savedhotbar"));
    }
}
