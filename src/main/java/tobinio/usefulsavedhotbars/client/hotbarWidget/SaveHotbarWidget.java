package tobinio.usefulsavedhotbars.client.hotbarWidget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import tobinio.usefulsavedhotbars.UsefulSavedHotbars;

@Environment (EnvType.CLIENT)
public class SaveHotbarWidget extends HotbarWidget {

    public static final Identifier BASE_TEXTURE = new Identifier(UsefulSavedHotbars.modID, "textures/gui/container/base.png");
    public static final Identifier BOARDER_TEXTURE = new Identifier(UsefulSavedHotbars.modID, "textures/gui/container/selected.png");

    public SaveHotbarWidget(int x, int y, int hotbarIndex, HotbarStorageEntry hotbar, MinecraftClient client) {
        super(x, y, hotbarIndex, hotbar, client);
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        RenderSystem.enableBlend();

        context.drawTexture(BASE_TEXTURE, this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());

        if (this.selected) {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
                ItemStack itemStack = client.player.getInventory().getStack(i);

                context.drawItem(itemStack, this.getX() + i * 16 + 2, this.getY() + 2);
            }

            context.drawTexture(BOARDER_TEXTURE, this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        } else {
            for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
                ItemStack itemStack = hotbar.get(i);

                context.drawItem(itemStack, this.getX() + i * 16 + 2, this.getY() + 2);
            }
        }
    }

    @Override
    public void apply() {

        ClientPlayerEntity clientPlayerEntity = client.player;
        HotbarStorage hotbarStorage = client.getCreativeHotbarStorage();
        HotbarStorageEntry hotbarStorageEntry = hotbarStorage.getSavedHotbar(this.hotbarIndex);

        for (int i = 0; i < PlayerInventory.getHotbarSize(); ++i) {
            hotbarStorageEntry.set(i, clientPlayerEntity.getInventory().getStack(i).copy());
        }

        hotbarStorage.save();
    }
}
