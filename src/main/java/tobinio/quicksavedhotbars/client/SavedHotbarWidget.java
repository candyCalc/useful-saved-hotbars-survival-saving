package tobinio.quicksavedhotbars.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameModeSelectionScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import tobinio.quicksavedhotbars.QuickSavedHotbars;

@Environment (EnvType.CLIENT)
public class SavedHotbarWidget extends ClickableWidget {

    public static final Identifier BASE_TEXTURE = new Identifier(QuickSavedHotbars.modID, "textures/gui/container/base.png");
    public static final Identifier BOARDER_TEXTURE = new Identifier(QuickSavedHotbars.modID, "textures/gui/container/boarder.png");


    private boolean selected;
    private final HotbarStorageEntry hotbar;
    private final int hotbarIndex;

    public SavedHotbarWidget(int x, int y, int hotbarIndex, HotbarStorageEntry hotbar) {
        super(x, y, PlayerInventory.getHotbarSize() * 16, 16, Text.of("hotbar " + hotbarIndex));

        this.hotbar = hotbar;
        this.hotbarIndex = hotbarIndex;
    }

    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawBackground(context);

        for (int i = 0; i < PlayerInventory.getHotbarSize(); i++) {
            ItemStack itemStack = hotbar.get(i);

            context.drawItem(itemStack, this.getX() + i * 16, this.getY());
        }

        if (this.selected) {
            this.drawSelectionBox(context);
        }
    }

    public void appendClickableNarrations(NarrationMessageBuilder builder) {
        this.appendDefaultNarrations(builder);
    }

    public boolean isSelected() {
        return super.isSelected() || this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private void drawBackground(DrawContext context) {
        context.drawTexture(BASE_TEXTURE, this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight());
    }

    private void drawSelectionBox(DrawContext context) {
        context.drawTexture(BOARDER_TEXTURE, this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
    }

    public HotbarStorageEntry getHotbar() {
        return hotbar;
    }

    public int getHotbarIndex() {
        return hotbarIndex;
    }
}
