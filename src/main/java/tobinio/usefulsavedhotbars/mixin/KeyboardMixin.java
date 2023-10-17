package tobinio.usefulsavedhotbars.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.usefulsavedhotbars.client.UsefulSavedHotbarsClient;
import tobinio.usefulsavedhotbars.client.SavedHotbarScreen;

@Mixin (Keyboard.class)
public abstract class KeyboardMixin {

    @Inject (method = "processF3", at = @At (value = "TAIL"), cancellable = true)
    private void processF3(int key, CallbackInfoReturnable<Boolean> cir) {

        KeyboardAccessor keyboardAccessor = (KeyboardAccessor) this;

        if (UsefulSavedHotbarsClient.LoadHotbarsKeyBinding.matchesKey(key, key)) {
            setSavedHotbarScreen(keyboardAccessor, SavedHotbarScreen.Type.LOAD, cir);
        }

        if (UsefulSavedHotbarsClient.SaveHotbarsKeyBinding.matchesKey(key, key)) {
            setSavedHotbarScreen(keyboardAccessor, SavedHotbarScreen.Type.SAVE, cir);
        }
    }

    @Unique
    private static void setSavedHotbarScreen(KeyboardAccessor keyboardAccessor, SavedHotbarScreen.Type save,
            CallbackInfoReturnable<Boolean> cir) {
        if (keyboardAccessor.getClient().player.isCreative()) {
            keyboardAccessor.getClient()
                    .setScreen(new SavedHotbarScreen(keyboardAccessor.getClient(), save));
        } else {
            keyboardAccessor.invokeDebugLog("debug.gamemodes.error");
        }

        cir.setReturnValue(true);
    }
}
