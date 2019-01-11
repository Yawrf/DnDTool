/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndtool;

import dndtool.MobTemplate.type;
import static dndtool.MobTemplate.type.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;

/**
 *
 * @author rewil
 */
public class FXMLDocumentController implements Initializable {
    
    // Time Tracker ----------------------------------------------------------------
    @FXML
    private TextField timeSlot;
    
    @FXML
    private TextField timeMod;
    
    
    private float time = 0.0f;
    boolean amPm = true;
    
    public void addTime() {
        String modS = timeMod.getText();
        float mod;
        try {
           mod = Float.parseFloat(modS); 
           time += mod;
           time %= 24;
           String output = "";
        } catch(Exception e) {}
       printTime();
    }
    
    public void changeTimeMode() {
        amPm = !amPm;
        printTime();
    }
    
    private void printTime() {
        String output = "";
        String hour = "";
        String minute = "";
        String suffix = "";
        
        int temp = (int)time;
        if(amPm) {
            hour += temp >= 13 ? (temp - 12): temp; 
            suffix += temp >= 12 ? " pm" : " am";
        } else {
            output += (int) temp;
        }
        temp = (int)((time % 1)*60);
        minute += temp;
        
        output += hour + ":" + minute + suffix;
        
        timeSlot.setText(output);
        timeMod.setText("");
    }
    
    // Randomizer ---------------------------------------------------------------
    
    @FXML
    private TextField randomMax;
    
    @FXML
    private TextField randomCount;
    
    @FXML
    private TextArea randomOutput;
    
    
    private ArrayList<Integer> randomSet;
    
    public void randomize() {
        try{
            int max = Integer.parseInt(randomMax.getText());
            int count = Integer.parseInt(randomCount.getText());
            Random rand = new Random();
            int[] output = new int[count];
            randomSet = new ArrayList<>();
            for(int i = 1; i <= max; ++i) {
                randomSet.add(i);
            }
            for(int i = 0; i < count; ++i) {
                output[i] = randomSet.remove(rand.nextInt(randomSet.size()));
            }
            randomMax.setText("");
            randomCount.setText("");
            String out = "";
            for(int i : output) {
                out += i + "\n";
            }
            randomOutput.setText(out);
        } catch(Exception e) {
            randomOutput.setText("Ensure there are no non-integer values");
        }
    }
    
    // Health Tracker -----------------------------------------------------------
    
    @FXML
    private ListView healthNames;
    
    @FXML
    private ListView healthNums;
    
    private ArrayList<String> HTNames = new ArrayList<>();
    private ArrayList<String> HTNums = new ArrayList<>();
    
    private void healthInit() {
        healthNames.setCellFactory(TextFieldListCell.forListView());
        healthNums.setCellFactory(TextFieldListCell.forListView());
        
        for(int i = 0; i < 19; ++i) {
            HTNames.add("");
            HTNums.add("0");
        }
        setHealthTexts();
    }
    private void setHealthTexts() {
        ObservableList<String> names = FXCollections.observableArrayList(HTNames);
        ObservableList<String> nums = FXCollections.observableArrayList(HTNums);
        healthNames.setItems(names);
        healthNums.setItems(nums);
    }
    
    // Zombies ------------------------------------------------------------------
    
    @FXML
    private CheckBox zomChkSmall;
    @FXML
    private CheckBox zomChkMSmall;
    @FXML
    private CheckBox zomChkMLarge;
    @FXML
    private CheckBox zomChkLarge;
    @FXML
    private CheckBox zomChkHuge;
    @FXML
    private CheckBox zomChkInfect;
    
    @FXML
    private TextField zomTxtName;
    
    @FXML
    private ListView listZombie;
    
    @FXML
    private ComboBox zomPicker;
    
    @FXML
    private TextField zomTxtType;
    @FXML
    private TextField zomTxtCount;
    @FXML
    private TextField zomTxtCR;
    @FXML
    private TextField zomTxtInit;
    @FXML
    private TextField zomTxtSpeed;
    @FXML
    private TextField zomTxtSpace;
    
    @FXML
    private TextField zomTxtHP;
    @FXML
    private TextField zomTxtMaxHP;
    @FXML
    private TextField zomTxtAC;
    
    @FXML
    private Button zomBtnFort;
    @FXML
    private Button zomBtnRef;
    @FXML
    private Button zomBtnWill;
    
    @FXML
    private TextField zomTxtInfect;
    @FXML
    private Button zomBtnMobUp;
    @FXML
    private Button zomBtnMUSplit2;
    @FXML
    private Button zomBtnMUSplit3;
    @FXML
    private Button zomBtnVolley;
    @FXML
    private Button zomBtnTrample;
    @FXML
    private Button zomBtnGrapple;
    
    @FXML 
    private TextField zomTxtDamage;
    @FXML
    private Button zomBtnDamApply;
    
    private final ArrayList<MobZombie> zombieMobs = new ArrayList<>();
    private MobZombie activeZombie = null;
    
    public void generateZom() {
        boolean small = zomChkSmall.isSelected();
        boolean mSmall = zomChkMSmall.isSelected();
        boolean mLarge = zomChkMLarge.isSelected();
        boolean large = zomChkLarge.isSelected();
        boolean huge = zomChkHuge.isSelected();
        boolean infectious = zomChkInfect.isSelected();
        
        Random rand = new Random();
        
        if(small || mSmall || mLarge || large || huge) {
            boolean done = false;
            type t = Small;
            while(!done) {
                switch(rand.nextInt(5)) {
                    case 0: if(small) {t = Small; done = true;}
                        break;
                    case 1: if(mSmall) {t = MSmall; done = true;}
                        break;
                    case 2: if(mLarge) {t = MLarge; done = true;}
                        break;
                    case 3: if(large) {t = Large; done = true;}
                        break;
                    case 4: if(huge) {t = Huge; done = true;}
                        break;
                }
            }
            int count = 0;
            switch(t) {
                case Small: {
                    count += rand.nextInt(6) + 1;
                    count += 8;
                } break;
                case MSmall: {
                    count += rand.nextInt(6) + 1;
                    count += 14;
                } break;
                case MLarge: {
                    count += rand.nextInt(17) + 1;
                    count += 20;
                } break;
                case Large: {
                    count += rand.nextInt(30) + 1;
                    count += 38;
                } break;
                case Huge: {
                    for(int i = 0; i < 3; ++i) {
                        count += rand.nextInt(20) + 1;
                    }
                    count += 68;
                } break;
            }
            String name = zomTxtName.getText();
            activeZombie = new MobZombie(name, t, count, infectious);
            zombieMobs.add(activeZombie);
            zomTxtName.setText("");
            setZomPicker();
            zomPicker.setValue(activeZombie.toString());
            updateZombieView();
            updateZombieList();
        }
    }
    
    public void setZomPicker() {
        ArrayList<String> temp = new ArrayList<>();
        for(MobZombie z : zombieMobs) {
            temp.add(z.toString());
        }
        ObservableList<String> options = FXCollections.observableArrayList(temp);
        zomPicker.setItems(options);
    }
    
    public void updateZombieMob() {
        if(zomPicker.getValue() instanceof String) {
            String s = (String) zomPicker.getValue();
            char[] chars = s.toCharArray();
            String temp = "";
            int i = 0;
            boolean done = false;
            while(!done) {
                if(chars[i + 1] != '|') {
                    temp += chars[i];
                } else {
                    done = true;
                }
                ++i;
            }
            for(MobZombie mz : zombieMobs) {
                if(mz.getName().equals(temp)) {
                    activeZombie = mz;
                }
            }
            updateZombieView();
        }
    }
    
    private void updateZombieView() {
        if(activeZombie != null) {
            zomTxtType.setText(activeZombie.getType().toString());
            zomTxtCount.setText("" + activeZombie.getCount());
            zomTxtCR.setText("" + activeZombie.getCR());
            zomTxtInit.setText("" + activeZombie.getInit());
            zomTxtSpeed.setText("" + activeZombie.getSpeed());
            zomTxtSpace.setText("" + activeZombie.getSpace());
            zomTxtHP.setText("" + activeZombie.getHP());
            zomTxtMaxHP.setText("" + activeZombie.getMaxHP());
            zomTxtAC.setText("" + activeZombie.getAC());
            zomTxtInfect.setText("" + activeZombie.MDM());
            setZombieEnables();
        } else {
            zomTxtType.setText("");
            zomTxtCount.setText("");
            zomTxtCR.setText("");
            zomTxtInit.setText("");
            zomTxtSpeed.setText("");
            zomTxtSpace.setText("");
            zomTxtHP.setText("");
            zomTxtMaxHP.setText("");
            zomTxtAC.setText("");
            zomTxtInfect.setText("");
        }
    }
    
    private void setZombieEnables() {
        boolean dead = false;
        if(activeZombie != null) {
            dead = activeZombie.isDead();
        }
        zomBtnFort.setDisable(dead);
        zomBtnRef.setDisable(dead);
        zomBtnWill.setDisable(dead);
        zomBtnMobUp.setDisable(dead);
        zomBtnVolley.setDisable(dead);
        zomBtnTrample.setDisable(dead);
        zomBtnGrapple.setDisable(dead);
        zomBtnDamApply.setDisable(dead);
    }
    
    public void deleteZombieMob() {
        if(activeZombie != null) {
            zombieMobs.remove(activeZombie);
            zomPicker.setValue(null);
            activeZombie = null;
            updateZombieView();
            updateZombieList();
        }
    }
    
    public void updateZombieList() {
        ArrayList<String> temp = new ArrayList<>();
        for(MobZombie z : zombieMobs) {
            temp.add(z.toString());
        }
        ObservableList<String> options = FXCollections.observableArrayList(temp);
        listZombie.setItems(options);
    }
    
    public void zomFortSave() {
        int[] save = activeZombie.fortSave();
        String out = "";
        switch(save[1]) {
            case 1: out += "Nat 1";
                break;
            case 20: out += "Nat 20";
                break;
            default: out += save[0];
        }
        zomBtnFort.setText(out);
    }
    public void zomRefSave() {
        int[] save = activeZombie.refSave();
        String out = "";
        switch(save[1]) {
            case 1: out += "Nat 1";
                break;
            case 20: out += "Nat 20";
                break;
            default: out += save[0];
        }
        zomBtnRef.setText(out);
    }
    public void zomWillSave() {
        int[] save = activeZombie.willSave();
        String out = "";
        switch(save[1]) {
            case 1: out += "Nat 1";
                break;
            case 20: out += "Nat 20";
                break;
            default: out += save[0];
        }
        zomBtnWill.setText(out);
    }
    public void zomGrapple() {
        int[] check = activeZombie.grapple();
        String out = "";
        switch(check[1]) {
            case 1: out += "Nat 1";
                break;
            case 20: out += "Nat 20";
                break;
            default: out += check[0];
        }
        zomBtnGrapple.setText(out);
    }
    
    public void zomMobUp() {
        zomBtnMobUp.setText("" + activeZombie.MobUp());
    }
    public void zomMUSplit2() {
        String s = zomMUSplit(2);
        zomBtnMUSplit2.setText(s);
    }
    public void zomMUSplit3() {
        String s = zomMUSplit(3);
        zomBtnMUSplit3.setText(s);
    }
    private String zomMUSplit(int split) {
        String dams = "";
        int[] temp = activeZombie.MUSplit(split); 
        for(int i = 0; i < temp.length - 1; ++i) {
            dams += temp[i];
            dams += " | ";
        }
        dams += temp[temp.length-1];
        return dams;
    }
    
    public void zomVolley() {
        int temp = activeZombie.Volley();
        zomBtnVolley.setText("" + (temp == 0 ? "N/A" : temp));
    }
    public void zomTrample() {
        zomBtnTrample.setText("" + activeZombie.Trample());
    }
    
    public void zomApplyDamage() {
        zomTxtDamage.setPromptText("");
        if(!zomTxtDamage.getText().equals("")){
            int damage = 0;
            try {
                damage = Integer.parseInt(zomTxtDamage.getText());
            } catch(Exception e) {
                zomTxtDamage.setPromptText("Error");
            }
            activeZombie.takeDamage(damage);
            updateZombieView();
            if(activeZombie.isDead()) {
                updateZombieList();
                zomPicker.setValue(activeZombie.toString());
            }
        } else {
            zomTxtDamage.setPromptText("Error");
        }
        zomTxtDamage.setText("");
    }
    
    
//---------------------------------------------------------------------------------
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        printTime();
        healthInit();
    }    
    
}
