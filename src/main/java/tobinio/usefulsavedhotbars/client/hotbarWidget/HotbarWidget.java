package tobinio.usefulsavedhotbars.client.hotbarWidget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import tobinio.usefulsavedhotbars.UsefulSavedHotbars;

@Environment (EnvType.CLIENT)
public abstract class HotbarWidget extends ClickableWidget {
    protected final MinecraftClient client;

    protected boolean selected;
    protected final HotbarStorageEntry hotbar;
    protected final int hotbarIndex;

    public HotbarWidget(int x, int y, int hotbarIndex, HotbarStorageEntry hotbar, MinecraftClient client) {
        super(x, y, PlayerInventory.getHotbarSize() * 16, 16, Text.of("hotbar " + hotbarIndex));

        this.hotbar = hotbar;
        this.hotbarIndex = hotbarIndex;
        this.client = client;
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

    public int getHotbarIndex() {
        return hotbarIndex;
    }

    public abstract void renderButton(DrawContext context, int mouseX, int mouseY, float delta);

    public abstract void apply();
}
