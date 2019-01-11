/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dndtool;

/**
 *
 * @author rewil
 */
public class MobZombie extends MobTemplate {
    
    private final boolean infectious;
    
    public MobZombie(String name, type type, int count, boolean infectious) {
        super(name, type, count);
        
        this.infectious = infectious;
    }
    
    @Override
    protected int MUAttacks(int currentDamage) {
        int damage = currentDamage;
        switch(this.type) {
            case Small:
            case MSmall: {
                for(int i = 0; i < 2; ++i) {
                    damage += rand.nextInt(4) + 1;
                }
            } break;
            case MLarge: {
                for(int i = 0; i < 3; ++i) {
                    damage += rand.nextInt(4) + 1;
                }
            } break;
            case Large: {
                for(int i = 0; i < 4; ++i) {
                    damage += rand.nextInt(4) + 1;
                }
            } break;
            case Huge: {
                for(int i = 0; i < 6; ++i) {
                    damage += rand.nextInt(4) + 1;
                }
            } break;
        }
        damage += 1;
        return damage;
    }
    
    @Override
    protected int VAttacks(int currentDamage) {
        return 0;
    }
    
    public boolean getInfectious() {
        return infectious;
    }

    @Override
    protected void setStats() {
        
        BAB = 0;
        init = -1;
        fort = 0;
        ref= -1;
        will = 3;
        speed = 20;
        reach = 5;
        
    }
    
}
