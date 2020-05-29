package maowcraft.simplecapes.mixin;

import maowcraft.simplecapes.SimpleCapes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(SettingsScreen.class)
public abstract class SettingsScreenMixin extends Screen {
    protected SettingsScreenMixin(Text title) {
        super(title);
    }

    private final int capeIndex = SimpleCapes.identifiers.size();
    private int currentIndex = SimpleCapes.identifiers.indexOf(SimpleCapes.selectedIdentifier);

    @Inject(method = "init", at = @At("TAIL"))
    public void addCapeInfo(CallbackInfo ci) {
        if (!SimpleCapes.identifiers.isEmpty()) {
            this.addButton(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, "Cycle Cape", (buttonWidget) -> {
                currentIndex = cycleInt(capeIndex, currentIndex);
                SimpleCapes.selectedIdentifier = SimpleCapes.identifiers.get(currentIndex);
                SimpleCapes.validateConfigExistence();
                SimpleCapes.writeConfig();
            }));
        }
    }

    private int cycleInt(int index, int i) {
        if (i != (index - 1)) {
            i = i + 1;
        } else {
            i = 0;
        }
        return i;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/SettingsScreen;drawCenteredString(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"))
    public void addCapeInfoPart2(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!SimpleCapes.identifiers.isEmpty()) {
            this.drawString(this.font, "Selected: " + SimpleCapes.selectedIdentifier, this.width / 2 + 8, this.height / 6 + 146, 16777215);
        } else {
            this.drawCenteredString(this.font, "No capes found.", this.width / 2, this.height / 6 + 146, 16777215);
        }
    }
}
