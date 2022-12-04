package org.example.characters;

import lombok.extern.slf4j.Slf4j;
import org.example.commands.ResurrectWarriorCommand;
import org.example.decorators.WarriorInArmy;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class Warlord extends WarriorImpl {
    private static final int ATTACK = 3;
    private static final int DEFENSE = 2;

    public Warlord() {
        super(100);
    }

    Iterator<Warrior> moveUnits(Iterable<Warrior> army, List<Warrior> deadUnits) {
        log.atDebug().log("Warlord is moving units...");

        List<Warrior> list = new ArrayList<>();
        for (Warrior warrior : army) {
            if (warrior != this) {
                list.add(warrior);
            }
        }

        Warrior wizard = list.stream().filter(w -> w instanceof Wizard).findFirst().orElse(null);

        if (!deadUnits.isEmpty() && wizard != null) {
            for (Warrior deadWarrior : deadUnits) {
                log.atDebug().log("\t - Dead warrior: {}", deadWarrior);
                WarriorInArmy wiz = new WarriorInArmy(wizard);
                wiz.processCommand(ResurrectWarriorCommand.INSTANCE, deadWarrior);
                if (deadWarrior.isAlive()) {
                    list.add(0, deadWarrior);
                }
            }
        }

        List<Warrior> lancers = list.stream().filter(w -> w instanceof Lancer).toList();
        List<Warrior> healers = list.stream().filter(w -> w instanceof Healer).toList();
        List<Warrior> others = list.stream()
                                   .filter(w -> !(w instanceof Lancer ||
                                           w instanceof Healer ||w instanceof Wizard)).toList();

        List<Warrior> result = new ArrayList<>();

        if (!lancers.isEmpty()) {
            lancers.stream().limit(1).findFirst().ifPresent(result::add);
        } else {
            others.stream().limit(1).findFirst().ifPresent(result::add);
        }

        if (!healers.isEmpty()) {
            result.addAll(healers);
        }

        if (lancers.size() > 1) {
            for (int i = 1; i < lancers.size(); i++) {
                result.add(lancers.get(i));
            }
        }

        if (others.size() > 1) {
            if (result.get(0) instanceof Lancer) {
                result.addAll(others);
            } else {
                for (int i = 1; i < others.size(); i++) {
                    result.add(others.get(i));
                }
            }

        }
        result.add(this);
        if (wizard != null) {
            result.add(wizard);
        }
        return result.iterator();
    }
}
