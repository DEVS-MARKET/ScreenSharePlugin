package eu.devsmarket.screenshare.logic;

import eu.devsmarket.screenshare.ScreenPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Transformation;
import org.joml.Vector3f;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScreenCreator {

    private final static ArrayList<TextDisplay> textDisplays = new ArrayList<>();
    private final FileConfiguration config = ScreenPlugin.CONFIG;
    private final Plugin plugin = ScreenPlugin.getPlugin(ScreenPlugin.class);
    private final int screenHeight = config.getInt("height");
    private final int screenWidth = config.getInt("width");
    private final int compressX = config.getInt("compressX");
    private final int compressY = config.getInt("compressY");
    private final String pixel = config.getString("pixel_char");

    public int renderPixelScreen(Location startLocation) {
        textDisplays.clear();
        double xStart = startLocation.getX();
        double yStart = startLocation.getY();
        double zStart = startLocation.getZ();
        List<String> colors = getColorsFromScreen();
        StringBuilder builder = new StringBuilder();
        int baseY = 36;
        int height = screenHeight / compressY - (baseY * 6 / compressY);
        int width = screenWidth / compressX;

        int colorIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.append("<").append(colors.get(colorIndex)).append(">").append(pixel);
                colorIndex++;
            }
            spawnText(new Location(startLocation.getWorld(), xStart, yStart - (y * 0.005), zStart), builder.toString());
            builder = new StringBuilder();
        }
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::updateScreen, 0L, config.getInt("frame_render_delay"));
    }

    public void updateScreen() {
        StringBuilder builder = new StringBuilder();
        List<String> colors = getColorsFromScreen();

        int baseX = 480;

        int width = screenWidth / compressX;
        int adjustedWidth = (baseX * 8 / compressX);
        int colorIndex = 0;

        for(TextDisplay textDisplay : textDisplays){
            for (int x = 0; x < width; x++) {
                builder.append("<").append(colors.get(colorIndex)).append(">").append(pixel);
                colorIndex++;
            }
            Component component = MiniMessage.miniMessage().deserialize(builder.substring(0, width * 10 - adjustedWidth));
            textDisplay.text(component);
            builder = new StringBuilder();
        }
    }

    private List<String> getColorsFromScreen() {
        List<String> colors = new ArrayList<>();
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(screenWidth, screenHeight);
            BufferedImage screenCapture = robot.createScreenCapture(screenRect);

            for (int y = 0; y < screenHeight; y += compressY) {
                for (int x = 0; x < screenWidth; x += compressX) {
                    java.awt.Color color = new java.awt.Color(screenCapture.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    colors.add(String.format("#%02x%02x%02x", red, green, blue));
                }
            }
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        return colors;
    }

    public void despawnAll() {
        textDisplays.forEach(Entity::remove);
    }

    private void spawnText(Location location, String text) {
        TextDisplay textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        Component component = MiniMessage.miniMessage().deserialize(text);
        textDisplay.text(component);
        textDisplay.setAlignment(TextDisplay.TextAlignment.CENTER);
        textDisplay.setShadowed(false);
        textDisplay.setDefaultBackground(false);
        textDisplay.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
        textDisplay.setLineWidth(5000);
        textDisplays.add(textDisplay);

        Vector3f scaleVector = new Vector3f(0.05F, 0.05F, 0.05F);
        Transformation textTransformation = textDisplay.getTransformation();
        Transformation transformation = new Transformation(textTransformation.getTranslation(), textTransformation.getLeftRotation(), scaleVector, textTransformation.getRightRotation());

        textDisplay.setTransformation(transformation);
    }

}
