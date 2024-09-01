package include;

import controller.SocketClientController;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Msg_Box extends javax.swing.JPanel {

    public Msg_Box() {
        initComponents();
    }
    
    /*public void setGetMessage(String ms) {
        msg.setText(ms);
    }
    
    public void setSendMessage(String ms) {
        msg.setText(ms);
    }*/

    public void setSendMessage(int ID, String ms) {
        msg.setText(ms);
        if (SocketClientController.getFriends().get(ID).getImage() != null) {
            profile.setIcon(SocketClientController.getFriends().get(ID).getImage());
        }
    }
    
    public void setGetMessage(int ID, String ms) {
        msg.setText(ms);
        if (SocketClientController.getFriends().get(ID).getImage() != null) {
            profile.setIcon(SocketClientController.getFriends().get(ID).getImage());
        }
        lbName.setText(SocketClientController.getFriends().get(ID).getfName());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        msg = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        lbName = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, null));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(500, 100));
        setMinimumSize(new java.awt.Dimension(300, 100));
        setPreferredSize(new java.awt.Dimension(500, 80));
        setLayout(new java.awt.GridBagLayout());

        msg.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 80.0;
        gridBagConstraints.weighty = 100.0;
        add(msg, gridBagConstraints);
        msg.getAccessibleContext().setAccessibleName("");

        jPanel1.setLayout(new java.awt.BorderLayout());

        lbName.setFont(new java.awt.Font("Khmer SBBIC Serif", 1, 12)); // NOI18N
        lbName.setForeground(new java.awt.Color(29, 118, 206));
        lbName.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbName.setText("You");
        lbName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lbName.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jPanel1.add(lbName, java.awt.BorderLayout.SOUTH);

        profile.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        profile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile_small.png"))); // NOI18N
        jPanel1.add(profile, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbName;
    private javax.swing.JTextField msg;
    private javax.swing.JLabel profile;
    // End of variables declaration//GEN-END:variables
}
