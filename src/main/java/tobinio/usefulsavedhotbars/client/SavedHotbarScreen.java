package tobinio.usefulsavedhotbars.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.HotbarStorage;
import net.minecraft.client.option.HotbarStorageEntry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import tobinio.usefulsavedhotbars.UsefulSavedHotbars;
import tobinio.usefulsavedhotbars.client.hotbarWidget.HotbarWidget;
import tobinio.usefulsavedhotbars.client.hotbarWidget.LoadHotbarWidget;
import tobinio.usefulsavedhotbars.client.hotbarWidget.SaveHotbarWidget;

import java.util.ArrayList;
import java.util.List;

@Environment (EnvType.CLIENT)
public class SavedHotbarScreen extends Screen {

    public static final Identifier BG_TEXTURE = new Identifier(UsefulSavedHotbars.modID, "textures/gui/container/background.png");

    public static final int BG_WIDTH = 162;
    public static final int BG_HEIGHT = 199;

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

        int x = (this.width / 2) - HotbarWidget.WIDTH / 2;
        int y = (this.height / 2) - BG_HEIGHT / 2 + 24;

        for (int i = 0; i < HotbarStorage.STORAGE_ENTRY_COUNT; i++) {
            HotbarStorageEntry hotbar = hotbars.getSavedHotbar(i);

            switch (this.type) {
                case LOAD ->
                        this.hotbarWidgets.add(new LoadHotbarWidget(x, y + i * (HotbarWidget.HEIGHT - 1), i, hotbar, client));
                case SAVE ->
                        this.hotbarWidgets.add(new SaveHotbarWidget(x, y + i * (HotbarWidget.HEIGHT - 1), i, hotbar, client));
            }
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.checkForClose();

        if (!this.mouseUsedForSelection) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.mouseUsedForSelection = true;
        }

        boolean mouseHasNotMoved = this.lastMouseX == mouseX && this.lastMouseY == mouseY;

        super.render(context, mouseX, mouseY, delta);

        RenderSystem.enableBlend();

        context.drawTexture(BG_TEXTURE, this.width / 2 - BG_WIDTH / 2, this.height / 2 - BG_HEIGHT / 2, 0, 0, BG_WIDTH, BG_HEIGHT, BG_WIDTH, BG_HEIGHT);

        String text = this.type == Type.SAVE ? "Save" : "Load";
        text += " Hotbar " + (this.selected + 1);

        context.drawCenteredTextWithShadow(this.textRenderer, text, this.width / 2, this.height / 2 - 92, -1);

        for (HotbarWidget hotbarWidget : this.hotbarWidgets) {
            hotbarWidget.render(context, mouseX, mouseY, delta);

            hotbarWidget.setSelected(hotbarWidget.getHotbarIndex() == selected);
            if (!mouseHasNotMoved && hotbarWidget.isSelected()) {
                this.selected = hotbarWidget.getHotbarIndex();
            }
        }
    }

    private void checkForClose() {
        if (!InputUtil.isKeyPressed(this.client.getWindow().getHandle(), GLFW.GLFW_KEY_F3)) {

            this.hotbarWidgets.get(selected).apply();
            this.close();
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (toggleKeyPressed(keyCode, scanCode)) {
            this.selected++;
            this.selected %= this.hotbarWidgets.size();
            this.mouseUsedForSelection = false;

            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    private boolean toggleKeyPressed(int keyCode, int scanCode) {
        switch (this.type) {
            case LOAD -> {
                return UsefulSavedHotbarsClient.LoadHotbarsKeyBinding.matchesKey(keyCode, scanCode);
            }
            case SAVE -> {
                return UsefulSavedHotbarsClient.SaveHotbarsKeyBinding.matchesKey(keyCode, scanCode);
            }
            default -> {
                return false;
            }
        }
    }

    public enum Type {
        LOAD,
        SAVE
    }
}
