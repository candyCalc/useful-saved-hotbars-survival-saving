package tobinio.usefulsavedhotbars.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.glfw.GLFW;
import tobinio.usefulsavedhotbars.client.hotbarWidget.HotbarWidget;
import tobinio.usefulsavedhotbars.client.hotbarWidget.LoadHotbarWidget;
import tobinio.usefulsavedhotbars.client.hotbarWidget.SaveHotbarWidget;

import java.util.ArrayList;
import java.util.List;

@Environment (EnvType.CLIENT)
public class SavedHotbarScreen extends Screen {

    private final MinecraftClient client;
    private final Type type;
    private final List<HotbarWidget> hotbarWidgets;

    private int selected = 0;

    private int lastMouseX;
    private int lastMouseY;
    private boolean mouseUsedForSelection;

    public SavedHotbarScreen(MinecraftClient client, Type type) {
        super(NarratorManager.EMPTY);

        this.client = client;
        this.type = type;

        this.hotbarWidgets = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();

        HotbarStorage hotbars = client.getCreativeHotbarStorage();

        int x = (this.width / 2) - (PlayerInventory.getHotbarSize() * 16 / 2);
        int y = (this.height / 2) - (HotbarStorage.STORAGE_ENTRY_COUNT * 16 / 2);

        for (int i = 0; i < HotbarStorage.STORAGE_ENTRY_COUNT; i++) {
            HotbarStorageEntry hotbar = hotbars.getSavedHotbar(i);

            switch (this.type) {
                case LOAD -> this.hotbarWidgets.add(new LoadHotbarWidget(x, y + i * 16, i, hotbar, client));
                case SAVE -> this.hotbarWidgets.add(new SaveHotbarWidget(x, y + i * 16, i, hotbar, client));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        if (this.checkForClose())
            return;

        if (!this.mouseUsedForSelection) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseUsedForSelection = true;
        }

        boolean mouseHasNotMoved = this.lastMouseX == mouseX && this.lastMouseY == mouseY;

        super.render(context, mouseX, mouseY, delta);

        for (HotbarWidget hotbarWidget : this.hotbarWidgets) {
            hotbarWidget.render(context, mouseX, mouseY, delta);

            hotbarWidget.setSelected(hotbarWidget.getHotbarIndex() == selected);
            if (!mouseHasNotMoved && hotbarWidget.isSelected()) {
                this.selected = hotbarWidget.getHotbarIndex();
            }
        }
    }

    //todo needs to return bool?
    private boolean checkForClose() {
        if (!InputUtil.isKeyPressed(this.client.getWindow().getHandle(), GLFW.GLFW_KEY_F3)) {

            this.hotbarWidgets.get(selected).apply();
            this.close();

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (UsefulSavedHotbarsClient.LoadHotbarsKeyBinding.matchesKey(keyCode, scanCode)) {
            this.selected++;
            this.selected %= this.hotbarWidgets.size();
            this.mouseUsedForSelection = false;

            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    public enum Type {
        LOAD,
        SAVE
    }
}
