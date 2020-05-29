package maowcraft.simplecapes.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import maowcraft.simplecapes.SimpleCapes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PlayerListEntry.class)
public abstract class PlayerListEntryMixin {
    @Accessor
    public abstract Map<MinecraftProfileTexture.Type, Identifier> getTextures();

    @Accessor
    public abstract GameProfile getProfile();

    @Environment(EnvType.CLIENT)
    @Inject(method = "getCapeTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/PlayerListEntry;loadTextures()V"))
    public void injectIntoTextures(CallbackInfoReturnable<Identifier> cir) {
        String identifierHashCode = SimpleCapes.selectedIdentifier.substring(SimpleCapes.selectedIdentifier.indexOf('-') + 1);
        if (String.valueOf(Math.abs(getProfile().getName().hashCode())).equals(identifierHashCode)) {
            Identifier cape = new Identifier(SimpleCapes.MODID, "textures/" + SimpleCapes.selectedIdentifier + ".png");
            getTextures().put(MinecraftProfileTexture.Type.CAPE, cape);
        }
    }
}
