package battles;

import characters.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DefenderTest {

    @DisplayName("Warrior's health")
    @ParameterizedTest(name = "[{index}] {0} fights against {1}, expected  result = {2}")
    @MethodSource("testWarriorHealth")
    void testWarriorsHealth(Warrior warrior1, Warrior warrior2, int health) {

        Battle.fight(warrior2, warrior2);

        assertEquals(health, warrior1.getHealth());
    }

    public static List<Arguments> testWarriorHealth() {
        return List.of(
                arguments(new Defender(), new Rookie(), 60)
        );

    }

    @DisplayName("Two armies fight")
    @ParameterizedTest(name = "[{index}] {0} fights against {1}, expected  result = {2}")
    @MethodSource("testArmiesFight")
    void testArmiesFight(Army army1, Army army2, boolean expected) {

        var test = Battle.fight(army1, army2);

        assertEquals(expected, test);
    }

    public static List<Arguments> testArmiesFight() {

        return List.of(
                arguments(new Army()
                                  .addUnits(Warrior::new, 5)
                                  .addUnits(Defender::new, 4)
                                  .addUnits(Defender::new, 5),
                          new Army()
                                  .addUnits(Warrior::new, 4),
                          true),
                arguments(new Army()
                                  .addUnits(Defender::new, 5)
                                  .addUnits(Warrior::new, 20)
                                  .addUnits(Defender::new, 4),
                          new Army()
                                  .addUnits(Warrior::new, 21),
                          true),
                arguments(new Army()
                                  .addUnits(Warrior::new, 10)
                                  .addUnits(Defender::new, 5)
                                  .addUnits(Defender::new, 10),
                          new Army()
                                  .addUnits(Defender::new, 10),
                          true),
                arguments(new Army()
                                  .addUnits(Defender::new, 2)
                                  .addUnits(Warrior::new, 1)
                                  .addUnits(Defender::new, 1),
                          new Army()
                                  .addUnits(Warrior::new, 5),
                          false),
                arguments(new Army()
                                  .addUnits(Warrior::new, 10)
                                  .addUnits(Knight::new, 5),
                          new Army()
                                  .addUnits(Warrior::new, 30),
                          false),
                arguments(new Army()
                                  .addUnits(Knight::new, 3),
                          new Army()
                                  .addUnits(Warrior::new, 3),
                          true)
        );
    }
}
