package include;

import java.awt.Color;
import java.awt.Image;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import model.UserInterface;

public class User_Connected_Box extends javax.swing.JPanel implements UserInterface {

    private ImageIcon icon;
    private int ID;
    private String fName;
    private LocalDateTime time;

    @Override
    public void set(ImageIcon image, int ID, String name, LocalDateTime time) {
        System.out.println("User_Connected_Box.set.1");
        if (image != null) {
            Image img;
            if (image.getIconWidth() > image.getIconHeight()) {
                img = image.getImage().getScaledInstance(60, -1, Image.SCALE_SMOOTH);
            } else {
                img = image.getImage().getScaledInstance(-1, 60, Image.SCALE_SMOOTH);
            }
            icon = new ImageIcon(img);
            profile.setIcon(icon);
        }
        this.ID = ID;
        lbName.setText(name+"!"+ID);
        this.fName = name;
        if(time!=null){
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            lbTime.setText(time.format(myFormatObj));
            this.time = time;
        }else{
            lbTime.setText("99:99");
        }
    }

    public void itMe() {
        lbName.setForeground(new Color(0, 0, 255));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        profile = new javax.swing.JLabel();
        line = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(210, 61));

        jLayeredPane1.setBackground(new java.awt.Color(255, 255, 255));
        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/user_icon.jpg"))); // NOI18N
        jLayeredPane1.add(profile);

        line.setBackground(new java.awt.Color(216, 215, 215));
        line.setOpaque(true);

        lbTime.setForeground(new java.awt.Color(102, 102, 102));
        lbTime.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbTime.setText("12:00 PM");

        lbName.setFont(new java.awt.Font("Khmer SBBIC Serif", 1, 13)); // NOI18N
        lbName.setForeground(new java.awt.Color(51, 51, 51));
        lbName.setText("Friend Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(line, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbName, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lbTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTime))
        );
    }// </editor-fold>//GEN-END:initComponents
    @Override
    public ImageIcon getImage() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public User_Connected_Box() {
        initComponents();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel line;
    private javax.swing.JLabel profile;
    // End of variables declaration//GEN-END:variables
}
