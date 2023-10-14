package tobinio.usefulsavedhotbars.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tobinio.usefulsavedhotbars.client.UsefulSavedHotbarsClient;
import tobinio.usefulsavedhotbars.client.SavedHotbarScreen;

@Mixin (Keyboard.class)
public abstract class KeyboardMixin {

    @Inject (method = "processF3", at = @At (value = "RETURN", ordinal = 17), cancellable = true)
    private void processF3(int key, CallbackInfoReturnable<Boolean> cir) {

        //todo fix duplicated code

        KeyboardAccessor keyboardAccessor = (KeyboardAccessor) this;

        if (UsefulSavedHotbarsClient.LoadHotbarsKeyBinding.matchesKey(key, key)) {
            if (keyboardAccessor.getClient().player.isCreative()) {
                keyboardAccessor.getClient()
                        .setScreen(new SavedHotbarScreen(keyboardAccessor.getClient(), SavedHotbarScreen.Type.LOAD));
            } else {
                keyboardAccessor.invokeDebugLog("debug.gamemodes.error");
            }

            cir.setReturnValue(true);
        }

        if (UsefulSavedHotbarsClient.SaveHotbarsKeyBinding.matchesKey(key, key)) {
            if (keyboardAccessor.getClient().player.isCreative()) {
                keyboardAccessor.getClient()
                        .setScreen(new SavedHotbarScreen(keyboardAccessor.getClient(), SavedHotbarScreen.Type.SAVE));
            } else {
                keyboardAccessor.invokeDebugLog("debug.gamemodes.error");
            }

            cir.setReturnValue(true);
        }
    }
}
