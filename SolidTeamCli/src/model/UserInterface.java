package model;

import java.time.LocalDateTime;
import javax.swing.ImageIcon;

public interface UserInterface {

    public void set(ImageIcon image, int ID, String name, LocalDateTime time);

    public ImageIcon getImage();

    public String getfName();
}
