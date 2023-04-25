package dev.moriaenergy;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

public class MainMenu extends QuittablePanel {
    
    public boolean dansUnNiveau = false;
    
    public MainMenu(Main parent){

        setLayout(new GridBagLayout());

        this.setLayout(new GridLayout(1,2,10,10));
        setMinimumSize(new Dimension(600,400));

        // Initialisation des grid layouts
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10));
        JPanel levelPanel = new JPanel(new GridBagLayout());
        levelPanel.setLayout(new GridLayout(1, 2, 10, 10));

        JButton placeHolder = new JButton("PLACE HOLDER");
        placeHolder.setVisible(false);


        JButton boutonQuitter = new JButton("Quitter");
        JButton boutonJouer = new JButton("Jouer");
        JButton boutonEditer = new JButton("Editeur de niveau");
        //var panel = this;


        JPanel editerPanel = new JPanel(); 
        editerPanel.setSize(25, 25);
        editerPanel.add(boutonEditer);

        JPanel jouerPanel = new JPanel();
        jouerPanel.setSize(25, 25);
        jouerPanel.add(boutonJouer);

        JPanel quitterPanel = new JPanel();
        quitterPanel.setSize(25, 25);
        quitterPanel.add(boutonQuitter);

        buttonPanel.add(jouerPanel);
        buttonPanel.add(editerPanel);
        buttonPanel.add(placeHolder);
        buttonPanel.add(quitterPanel);


        //charger les niveaux 
        File ressourceOfficiel = new File("src/main/resources/official level");
        File ressourcePersonalise = new File("src/main/resources/custom level");
        //combobox
        String[] choixBanque = {"Niveau officiel","Niveau personnalisé"};
        JComboBox<String> choixBanqueCombo = new JComboBox<>(choixBanque);
        String[] combo = loadLevelBank(ressourceOfficiel);
        JComboBox<String> comboBox = new JComboBox<>(combo); 
        

        JPanel choixBanquePanel = new JPanel();
        choixBanquePanel.setSize(25, 25);
        choixBanquePanel.add(choixBanqueCombo);
        JPanel choixNiveau = new JPanel();
        choixNiveau.setSize(25, 25);
        choixNiveau.add(comboBox);

        //ajout des listeners

        setComboBox(choixBanqueCombo,comboBox,ressourceOfficiel,ressourcePersonalise);
        setBoutonJouer(boutonJouer, comboBox);
        setBoutonEditer(boutonEditer, parent);
        setBoutonQuitter(boutonQuitter, parent);

        levelPanel.add(choixBanquePanel);
        levelPanel.add(choixNiveau);

        this.add(buttonPanel);
        this.add(levelPanel);

    }




    private static String[] loadLevelBank(File folder){
        ArrayList<String> liste = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
                if(fileEntry.getName().endsWith(".nrg")) liste.add(fileEntry.getName());
        }


        String[] result = new String[liste.size()];
        for(int i =0;i<liste.size();i++){
            result[i] = mySplit(liste.get(i));
        }
        return result;
    }

    private static String mySplit(String str){
        String result = "";
        for(int i =0;i<str.length();i++){
            if(str.charAt(i) == '.'){
                return result;
            }else{result += str.charAt(i);}
        }
        return result;
    }

    private static void setComboBox(JComboBox<String> choixBanqueCombo,JComboBox<String> comboBox,File ressourceOfficiel,File ressourcePersonalise){
        
        AbstractAction cbActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] nouveauxItems;
                if(choixBanqueCombo.getSelectedItem().equals("Niveau officiel") ){
                    Parser.levelFolder = "official level";
                    VictoryScreen.officialLevel = true;
                    nouveauxItems = loadLevelBank(ressourceOfficiel);
                    
                }else{
                    Parser.levelFolder = "custom level";
                    VictoryScreen.officialLevel = false;
                    nouveauxItems = loadLevelBank(ressourcePersonalise);
                }

                int size = comboBox.getItemCount();
                for(int i=0;i<size;i++){
                    comboBox.removeItemAt(0);
                }
                
                for(int i =0;i<nouveauxItems.length;i++){
                    comboBox.addItem(nouveauxItems[i]);
                }
            }
        };
        choixBanqueCombo.addActionListener(cbActionListener);
    }

    private static void setBoutonJouer(JButton boutonJouer, JComboBox<String> comboBox){
        AbstractAction boutonJouerPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Main.instance.switchTo( new LevelPlayer((String) comboBox.getSelectedItem()) );
                }catch(Exception error){
                    error.printStackTrace();
                }
            }
        };

        boutonJouer.addActionListener(boutonJouerPresser);
        boutonJouer.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,0), "Z_pressed");
        boutonJouer.getActionMap().put("Z_pressed", boutonJouerPresser);
    }

    private static void setBoutonEditer(JButton boutonEditer,Main parent){
        AbstractAction boutonEditerPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    parent.levelMakerPopup();
                }catch(Exception error){
                    error.printStackTrace();
                }
            }

        };
        boutonEditer.addActionListener(boutonEditerPresser);
        boutonEditer.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,0), "E_pressed");
        boutonEditer.getActionMap().put("E_pressed", boutonEditerPresser);
    }

    private static void setBoutonQuitter(JButton boutonQuitter,Main parent){
        AbstractAction boutonQuitterPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setVisible(false);
                parent.dispose();
            }
        };

        boutonQuitter.addActionListener(boutonQuitterPresser);
        boutonQuitter.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,0), "A_pressed");
        boutonQuitter.getActionMap().put("A_pressed", boutonQuitterPresser);

    }

}
