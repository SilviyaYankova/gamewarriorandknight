package org.example.characters;

import org.example.decorators.WarriorInArmyDecorator;

public class Lancer extends WarriorImpl {
    private static final int ATTACK = 6;
    private static final int ADDITIONAL_DAMAGE = 50;

    @Override
    public int getAttack() {
        return ATTACK;
    }

    public int getAdditionalDamage() {
        return ADDITIONAL_DAMAGE;
    }

    @Override
    public void hit(Warrior opponent) {
        int healthBeforeAttacked = opponent.getHealth();
        super.hit(opponent);

        if (opponent instanceof WarriorInArmyDecorator opponentWithNext) {
            Warrior nextWarrior = opponentWithNext.getWarriorBehind();
            if (nextWarrior != null) {
                int healthAfterAttacked = opponent.getHealth();
                int damageDealt = healthBeforeAttacked - healthAfterAttacked;
                int percentages = 100;
                int attack = damageDealt * getAdditionalDamage() / percentages;
                nextWarrior.receiveDamage(attack);
            }
        }
    }
}
