package org.example.characters;

import org.example.weapons.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WarriorImpl implements Warrior {
    private static final int ATTACK = 5;
    private int health;
    private final int initialHealth;
    private Warrior warrior;
    private List<Weapon> weapons;

    public WarriorImpl() {
        this(50);
    }

    public WarriorImpl(Warrior warrior) {
        this(50);
        this.warrior = warrior;
    }

    protected WarriorImpl(int health) {
        this.initialHealth = health;
        this.health = health;
        weapons = new ArrayList<>();
    }

    @Override
    public List<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public int getAttack() {
        int bonusAttack = getWeapons().stream().mapToInt(Weapon::getAttack).sum();
        return Math.max(0, ATTACK + bonusAttack);
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = Math.min(initialHealth, health);
    }

    @Override
    public void healBy(int healPoints) {
        setHealth(getHealth() + healPoints);
    }

    @Override
    public void hit(Warrior opponent) {
        opponent.receiveDamage(getAttack());
    }

    @Override
    public void receiveDamage(int attack) {
        setHealth(health - attack);
    }


    @Override
    public void equipWeapon(Weapon weapon) {
        getWeapons().add(weapon);
        setHealth(Math.max(0, getHealth() + weapon.getHealth()));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " {health = " + health + "}";
    }
}
