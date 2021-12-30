package jessevii.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    /**
     * Sleeps using Thread.sleep and catches possible exception.
     * @param ms milliseconds to sleep
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws an x centered string to the screen using the passed graphics.
     * You can pass an rgb color with the string like this -:0, 255, 0:- and it will use the color with the upcoming text until u pass another color.
     */
    public static void drawCenterString(Graphics2D g, String text, int y, int size) {
        String startRegex = "-:";

        int widthPlus = 0;
        int textWidth = 0;
        String[] texts = {text};

        //Set font and enable anti aliasing
        g.setFont(new Font("Arial", Font.BOLD, size));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        //Split the text into list for each given color
        if (text.contains(startRegex)) {
            ArrayList<String> list = new ArrayList<String>();
            for (String s : text.split(startRegex)) {
                if (!s.isEmpty()) {
                    list.add(startRegex + s);

                    //Calculate total text width including all parts of the real text
                    String parsed = s.substring(s.indexOf(":-") + 2);
                    textWidth += g.getFontMetrics().stringWidth(parsed);
                }
            }

            String temp[] = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                temp[i] = list.get(i);
            }

            texts = temp;
        }

        //Loop through the splitted color text things and add the width of the text to widthPlus so next piece will be rendered in the correct location
        for (String s : texts) {
            //Set color
            Color color = Color.BLACK;
            if (s.contains(startRegex)) {
                String[] split = s.replace(startRegex, "").substring(0, s.indexOf(":-") - 2).replace(" ", "").split(",");
                color = new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
            }
            g.setColor(color);

            //Draw text and add its width to widthPlus
            String parsedText = s;
            if (parsedText.contains(startRegex)) {
                parsedText = parsedText.substring(parsedText.indexOf(":-") + 2);
            }

            g.drawString(parsedText, ((Tetris.instance.getWidth() / 2) - (textWidth / 2)) + widthPlus, y);
            widthPlus += g.getFontMetrics().stringWidth(parsedText);
        }
    }

    /**
     * Plays a sound with the given name thats in /sounds
     */
    public static void playSound(String name) {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(Tetris.class.getResourceAsStream("sounds/" + name))));
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
