package dev.moriaenergy;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VictoryScreen extends QuittablePanel {
    
    public static Main frame = null;
    static boolean officialLevel = true;
    static int max_level = 0;

    public VictoryScreen(Main parent,boolean plusieurConfig){
        frame = parent;
        JLabel labelVictoire = new JLabel("Victoire !");
        if(plusieurConfig) labelVictoire.setText("Victoire ! Il y a plusieurs configurations");
        
        JButton menuPrincipal = new JButton("Retour");
        setBoutonRetour(menuPrincipal,parent);
        JButton niveauSuivant = new JButton("Suivant");
        setBoutonSuivant(niveauSuivant);

        setMinimumSize(new Dimension(600,400));

        setLayout(new GridBagLayout());
        this.setLayout(new GridLayout(2,1,10,10));
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(1,2, 10, 10));
        
        JPanel menuPrincipalPanel = new JPanel(); 
        menuPrincipalPanel.setSize(25, 25);
        menuPrincipalPanel.add(menuPrincipal);
        JPanel niveauSuivantPanel = new JPanel(); 
        niveauSuivantPanel.setSize(25, 25);
        niveauSuivantPanel.add(niveauSuivant);
        
        buttonPanel.add(menuPrincipalPanel);
        File folder = new File("src/main/resources/official level");
        if(officialLevel && getNextLevel(LevelPlayer.levelRef) <= folder.listFiles().length ){
            buttonPanel.add(niveauSuivantPanel);
        }

    

        this.add(labelVictoire);
        this.add(buttonPanel);
    }

    private static void setBoutonSuivant(JButton boutonSuivant){
        AbstractAction boutonSuivantPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Main.instance.switchTo( new LevelPlayer("level" + getNextLevel(LevelPlayer.levelRef)));
                }catch(Exception error){
                    error.printStackTrace();
                }
            }

        };

        boutonSuivant.addActionListener(boutonSuivantPresser);
        boutonSuivant.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,0), "S_pressed");
        boutonSuivant.getActionMap().put("S_pressed", boutonSuivantPresser);
    }

    private static void setBoutonRetour(JButton boutonRetour,Main parent){
        AbstractAction boutonRetourPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Parser.levelFolder = "official level";
                    officialLevel = true;
                    Main.instance.switchTo(new MainMenu(parent));
                }catch(Exception error){
                    error.printStackTrace();
                }
            }

        };

        boutonRetour.addActionListener(boutonRetourPresser);
        boutonRetour.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,0), "Z_pressed");
        boutonRetour.getActionMap().put("Z_pressed", boutonRetourPresser);
    }


    public static int getNextLevel(String actualLevel){
        String result = "";

        for(int i =0;i<actualLevel.length();i++){
            char test = actualLevel.charAt(i);
            if(test == '0' || test == '1' || test == '2' || test == '3' || test == '4' || test == '5' || test == '6' || test == '7' || test == '8' || test == '9')
                result += test ;
        }

        int x = Integer.parseInt(result);
        x++;

        return x;
    }

}
