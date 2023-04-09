package dev.moriaenergy;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu extends JPanel {
    

    public MainMenu(Main parent){
        
        setMinimumSize(new Dimension(600,400));

        JButton boutonQuitter = new JButton("Quitter");
        JButton boutonJouer = new JButton("Jouer");
        JButton boutonEditer = new JButton("Editeur de niveau");
        var frame = this;

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_A){
                    System.out.println("Bonjour");
                    parent.setVisible(false);
                    parent.dispose();
                }else{
                    System.out.println("Salut");
                }
            }
        });

        boutonQuitter.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    parent.setVisible(false);
                    parent.dispose();
                }
        }); 


        boutonJouer.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    parent.getContentPane().add(new LevelPlayer( 3 ));
                }catch(Exception error){
                    error.printStackTrace();
                }
            }
        });

        boutonEditer.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                try{
                    parent.setVisible(false);
                    parent.levelMakerPopup();
                    parent.setSize(600,400);
                    parent.setVisible(true);
                }catch(Exception error){
                    error.printStackTrace();
                }
            }
        });



        this.add(boutonQuitter);
        this.add(boutonJouer);
        this.add(boutonEditer);

        boutonQuitter.setBounds(300, 300, 100, 35);
    }


}
