package dev.moriaenergy;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class MainMenu extends QuittablePanel {

    JComboBox<String> bankComboBox = new JComboBox<>( new String[]{
              "Niveau officiel", "Niveau personnalisé" } );
    JComboBox<String> levelsComboBox = new JComboBox<>();

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
        JButton buttonEdit = new JButton("Editer");
        JButton boutonEditeurDeNiveau = new JButton("Createur de niveau");

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
        boutonEditeurDeNiveau.addActionListener(boutonEditerPresser);
        boutonEditeurDeNiveau.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E,0), "E_pressed");
        boutonEditeurDeNiveau.getActionMap().put("E_pressed", boutonEditerPresser);

        buttonEdit.addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                try {
                    Main.instance.switchTo( new LevelMaker(
                              Parser.parse( getSelectedLevelPath() )
                    ) );
                } catch( Exception ex ) {
                    throw new RuntimeException( ex );
                }
            }
        } );

        JPanel editerPanel = new JPanel(); 
        editerPanel.setSize(25, 25);
        editerPanel.add(boutonEditeurDeNiveau);

        JPanel jouerPanel = new JPanel();
        jouerPanel.setSize(25, 25);
        jouerPanel.add(boutonJouer);

        JPanel editLevelPanel = new JPanel();
        editLevelPanel.setSize(25,25);
        editLevelPanel.add(buttonEdit);

        JPanel quitterPanel = new JPanel();
        quitterPanel.setSize(25, 25);
        quitterPanel.add(boutonQuitter);

        buttonPanel.add(jouerPanel);
        buttonPanel.add(editLevelPanel);
        buttonPanel.add(editerPanel);
        buttonPanel.add(placeHolder);
        buttonPanel.add(quitterPanel);


        updateLevelsList();

        JPanel choixBanquePanel = new JPanel();
        choixBanquePanel.setSize(25, 25);
        choixBanquePanel.add(bankComboBox);
        JPanel choixNiveau = new JPanel();
        choixNiveau.setSize(25, 25);
        choixNiveau.add(levelsComboBox);


        //ajout des listeners
        AbstractAction cbActionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLevelsList();
            }
        };

        bankComboBox.addActionListener(cbActionListener);

        AbstractAction boutonJouerPresser = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.instance.switchTo( new LevelPlayer(getSelectedLevelPath()) );
                } catch(Exception error) {
                    error.printStackTrace();
                }
            }
        };

        boutonJouer.addActionListener(boutonJouerPresser);
        boutonJouer.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,0), "Z_pressed");
        boutonJouer.getActionMap().put("Z_pressed", boutonJouerPresser);

        levelPanel.add(choixBanquePanel);
        levelPanel.add(choixNiveau);

        this.add(buttonPanel);
        this.add(levelPanel);
    }


    public void updateLevelsList() {
        File ressourceOfficiel = new File("src/main/resources/official level");
        File ressourcePersonalise = new File("src/main/resources/custom level");

        String[] nouveauxItems;
        if(bankComboBox.getSelectedItem().equals("Niveau officiel") ){
            nouveauxItems = loadOfficialBank(ressourceOfficiel);
        } else{
            nouveauxItems = loadLevelBank(ressourcePersonalise);
        }

        int size = levelsComboBox.getItemCount();
        for(int i=0;i<size;i++){
            levelsComboBox.removeItemAt(0);
        }

        for( String nouveauxItem : nouveauxItems ) {
            levelsComboBox.addItem( nouveauxItem );
        }
    }

    private static String[] loadOfficialBank(File folder) {
        String[] list = loadLevelBank(folder);
        String[] result = new String[list.length];
        // tri de la liste
        for( String str : list ) {
            String level_str = str.substring(5);
            int level = Integer.parseInt( level_str );
            result[level - 1] = level_str;
        }
        return result;
    }

    private static String[] loadLevelBank(File folder){
        ArrayList<String> liste = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName();
            if(name.endsWith(".nrg"))
                liste.add(name.substring( 0, name.length()-4 ));
        }
        return liste.toArray(new String[0]);
    }


    private String getSelectedLevelPath() {
        String fileName = (String) levelsComboBox.getSelectedItem();
        if(bankComboBox.getSelectedItem().equals("Niveau officiel")) {
            fileName = "official level/level" + fileName + ".nrg";
        } else {
            fileName = "custom level/" + fileName + ".nrg";
        }
        return fileName;
    }

}
