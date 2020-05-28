package maowcraft.simplecapes;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RRPPreGenEntrypoint;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SimpleCapesRRP implements RRPPreGenEntrypoint {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("simplecapes:resource");

    @Override
    public void pregen() {
        BufferedImage capeImage;
        SimpleCapes.writeDirectories();
        try {
            capeImage = ImageIO.read(SimpleCapes.capeFile.toFile());
            RESOURCE_PACK.addTexture(new Identifier(SimpleCapes.MODID, "cape"), capeImage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        RRPCallback.EVENT.register(a -> a.add(1, RESOURCE_PACK));
    }
}
