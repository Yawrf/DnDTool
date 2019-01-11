package dndtool;


import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rewil
 */
public abstract class MobTemplate {
    protected int count;
    protected int CR;
    protected int AC;
    protected int BAB;
    protected int grapple;
    protected int HP;
    protected int MaxHP;
    protected type type;
    protected int init;
    protected int fort;
    protected int ref;
    protected int will;
    protected int speed;
    protected int space;
    protected int reach;
    protected String name;
    protected Random rand = new Random();
    
    public enum type {
        Small,
        MSmall,
        MLarge,
        Large,
        Huge
    }
    
    public MobTemplate(String name, type type, int count) {
        this.name = name;
        this.type = type;
        this.count = count;
        setStats();
        HP = genHP();
        MaxHP = HP;
        AC = genAC();
        grapple = genGrapple();
        CR = genCR();
        space = genSpace();
    }
    
    /**
     * Set BAB, init, fort, ref, will, speed, reach, and any others added by the Mob
     */
    protected abstract void setStats();
    
    private int genHP() {
        int temp = 0;
        for(int i = 0; i < count * 2; ++i) {
            temp += rand.nextInt(12) + 1;
        }
        temp += 3*count;
        return temp;
    }
    
    private int genAC() {
        int temp = 10;
        switch(this.type) {
            case Small:
            case MSmall: temp = 10;
                break;
            case MLarge: temp = 9;
                break;
            case Large: temp = 7;
                break;
            case Huge: temp = 3;
                break;
        } 
        return temp;
    }
    
    private int genGrapple() {
        int temp = 0;
        switch(this.type) {
            case Small:
            case MSmall: temp = 6;
                break;
            case MLarge: temp = 10;
                break;
            case Large: temp = 14;
                break;
            case Huge: temp = 18;
                break;
        }
        return temp;
    }
    
    private int genCR() {
        int temp = 0;
        switch(this.type) {
            case Small:
            case MSmall: temp = 3;
                break;
            case MLarge: temp = 5;
                break;
            case Large: temp = 8;
                break;
            case Huge: temp = 12;
                break;
        }
        return temp;
    }
    
    private int genSpace() {
        int temp = 10;
        switch(this.type) {
            case Small:
            case MSmall: temp = 10;
                break;
            case MLarge: temp = 15;
                break;
            case Large: temp = 20;
                break;
            case Huge: temp = 30;
                break;
        }
        return temp;
    }
    
    public int MDM() {
        float health = (float)HP / (float)MaxHP;
        if(health > 0.75f) {
            return 3;
        } else if(health > 0.5f) {
            return 2;
        }
        return 1;
    }
    
    public int MobUp() {
        int damage = 0;
        damage = MUAttacks(damage);
        damage *= MDM();
        return damage;
    }
    
    public int[] MUSplit(int split) {
        int[] dams = new int[split];
        int base = MobUp();
        base = base / split;
        int mod = (int)Math.ceil((float)base/10f);
        for(int i = 0; i < dams.length; ++i) {
            dams[i] = base + rand.nextInt(2*mod) - mod;
        }
        return dams;
    }
    
    /**
     * Mob Up Attacks: Calculate what the damage from attack rolls will be on the
     * MobUp attack. Do not include MDM or any other modifiers. Include 
     * adjustments based on size.
     * @param currentDamage
     * @return 
     */
    protected abstract int MUAttacks(int currentDamage);
    
    public int Volley() {
        int damage = 0;
        damage = VAttacks(damage);
        damage *= MDM();
        return damage;
    }
    
    /**
     * Volley Attacks: Calculate what the damage from attack rolls will be on the
     * Volley attack. Do not include MDM or any other modifiers. Include 
     * adjustments based on size.
     * @param currentDamage
     * @return 
     */
    protected abstract int VAttacks(int currentDamage);
    
    public int Trample() {
        int damage = 0;
        for(int i = 0; i < 2; ++i) {
            damage += rand.nextInt(6) + 1;
        }
        damage += 1;
        damage *= MDM();
        return damage;
    }
    
    public int[] grapple() {
        int roll = rand.nextInt(20) + 1;
        int result = roll + BAB + this.grapple;
        return new int[]{result,roll};
    }
    
    public int[]  fortSave() {
        int roll = rand.nextInt(20) + 1;
        int result = roll + fort;
        return new int[]{result, roll};
    }
    public int[]  refSave() {
        int roll = rand.nextInt(20) + 1;
        int result = roll + ref;
        return new int[]{result, roll};
    }
    public int[]  willSave() {
        int roll = rand.nextInt(20) + 1;
        int result = roll + will;
        return new int[]{result, roll};
    }
    
    public void takeDamage(int damage) {
        HP -= damage;
    }
    
    public boolean isDead() {
        return HP <= 0;
    }
    
    public int getHP() {
        return HP;
    }
    public int getMaxHP() {
        return MaxHP;
    }
    
    public String getName() {
        return name;
    }
    public int getCR() {
        return CR;
    }
    public int getAC() {
        return AC;
    }
    public type getType() {
        return type;
    }
    public int getCount() {
        return count;
    }
    public int getSpace() {
        return space;
    }
    public int getSpeed() {
        return speed;
    }
    public int getInit() {
        return init;
    }
    public int getReach() {
        return reach;
    }
    
    @Override
    public String toString() {
        String output = "";
        String buffer = " | ";
        output += name + buffer + (isDead() ? "Dead" : type) + buffer + count;
        return output;
    }
    
}
