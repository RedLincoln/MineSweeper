package View;

import javax.swing.*;

public enum ViewImages {
    CLOCK(new ImageIcon(ViewImages.class.getResource("Icons/clock.png"))),
    RESET(new ImageIcon(ViewImages.class.getResource("Icons/reset.png"))),
    FLAG(new ImageIcon(ViewImages.class.getResource("Icons/flag.png"))),
    SMALIEFACE(new ImageIcon(ViewImages.class.getResource("Icons/smalieFace.png"))),
    SADFACE(new ImageIcon(ViewImages.class.getResource("Icons/sadFace.png"))),
    MINE(new ImageIcon(ViewImages.class.getResource("Icons/mine.png")));

    private ImageIcon imageIcon;

    ViewImages(ImageIcon imageIcon) {
        try {
            this.imageIcon = imageIcon;
        }catch(Exception e){
            this.imageIcon = null;
        }
    }

    public ImageIcon get(){
        return imageIcon;
    }
}
