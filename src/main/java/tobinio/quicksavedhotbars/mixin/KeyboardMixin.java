package tobinio.quicksavedhotbars.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.quicksavedhotbars.QuickSavedHotbars;
import tobinio.quicksavedhotbars.client.QuickSavedHotbarsClient;
import tobinio.quicksavedhotbars.client.SavedHotbarScreen;

@Mixin (Keyboard.class)
public abstract class KeyboardMixin {

    @Inject (method = "processF3", at = @At (value = "RETURN", ordinal = 17), cancellable = true)
    private void processF3(int key, CallbackInfoReturnable<Boolean> cir) {

        KeyboardAccessor keyboardAccessor = (KeyboardAccessor) this;

        if (QuickSavedHotbarsClient.QuickSavedHotbarKeyBinding.matchesKey(key, key)) {
            if (keyboardAccessor.getClient().player.isCreative()) {
                keyboardAccessor.getClient().setScreen(new SavedHotbarScreen(keyboardAccessor.getClient()));
            } else {
                keyboardAccessor.invokeDebugLog("debug.gamemodes.error");
            }

            cir.setReturnValue(true);
        }
    }
}
