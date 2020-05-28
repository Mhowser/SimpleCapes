package maowcraft.simplecapes;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleCapes implements ModInitializer {
    public static final String MODID = "simplecapes";
    public static final String NAME = "Simple Capes";

    private static final Logger logger = LogManager.getLogger(NAME);

    public static final Path mainDir = Paths.get(MODID);
    public static final Path capeFile = Paths.get(MODID + "/cape.png");

    @Override
    public void onInitialize() {
        logger.info("Simple Capes has initialized.");
        SimpleCapes.writeDirectories();
    }

    public static void writeDirectories() {
        try {
            if (!Files.exists(mainDir)) {
                logger.warn("\"" + mainDir.toString() + "\" directory does not exist, creating it now.");
                Files.createDirectory(mainDir);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
