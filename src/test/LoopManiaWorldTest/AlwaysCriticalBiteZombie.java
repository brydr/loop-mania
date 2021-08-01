package test.LoopManiaWorldTest;

import java.util.List;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.Character;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Zombie;

public class AlwaysCriticalBiteZombie extends Zombie {
    // A zombie that always critical bites (for testing)

    private final int criticalBiteChance = 0;

    public AlwaysCriticalBiteZombie(PathPosition position) {
        super(position);
    }

    @Override
    public void attack(Character character) {
        // criticalBiteChance = randomGenerator.nextInt(10);
        // A random number between 0 and 9.
        // System.out.println("Chance is " + criticalBiteChance);
        int attackPower = this.getAttack();
        List<AlliedSoldier> alliedSoldiers = character.getListAlliedSoldiers();
        // If the character has allied soldiers it should attack them instead.
        if (alliedSoldiers.size() > 0) {
            AlliedSoldier firstSoldier = alliedSoldiers.get(0);
            firstSoldier.takeDamage(attackPower);
            if (firstSoldier.getHp() <= 0) {
                character.removeAlliedSoldier(firstSoldier);
            } else if (criticalBiteChance == 0) {
                Zombie newZombie = new AlwaysCriticalBiteZombie(firstSoldier.getPosition());
                setConvertedToEnemy(character.convertToEnemy(firstSoldier, newZombie));
            }
        } else {
            character.takeDamage(attackPower);
        }
        // System.out.println(String.format("char hp %d", character.getHp()));
    }

}
