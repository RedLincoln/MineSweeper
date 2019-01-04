package View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public enum Images {
    FLAG("Images/flag.png"),
    MINE("Images/mine.png"),
    CLOCK("Images/clock.png"),
    RESET("Images/reset.png");

    private BufferedImage image;

    Images(String path) {
        try {
            this.image = ImageIO.read(new File(path));
        }catch(Exception e){
            this.image = null;
        }
    }

    public BufferedImage getImage(){
        return image;
    }

}
