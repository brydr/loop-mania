package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.javatuples.Pair;

import java.time.Duration;
import java.util.Arrays;

import unsw.loopmania.AlliedSoldier;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Slug;

public class AlliedSoldierTest {
    @Test
    public void takeDamageTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        AlliedSoldier ally = new AlliedSoldier(pos);
        assertEquals(ally.getHp(), 50);
        // take 20 dmg
        ally.takeDamage(20);
        assertEquals(ally.getHp(), 30);
        // take 40 dmg
        ally.takeDamage(40);
        // check negative health
        assertEquals(ally.getHp(), -10);
    }

    @Test
    public void tranceTimeTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Slug enemy = new Slug(pos);
        Duration fiveSeconds = Duration.ofSeconds(5);
        Duration threeSeconds = Duration.ofSeconds(3);
        AlliedSoldier ally1 = new AlliedSoldier(pos, fiveSeconds, enemy);
        AlliedSoldier ally2 = new AlliedSoldier(pos, fiveSeconds, enemy);
        // check trance is over 5-5=0
        assertTrue(ally1.isTranceOver(fiveSeconds));
        // check trance isn't over 5-3=2
        assertFalse(ally2.isTranceOver(threeSeconds));
        // check trance is over 3-5=0
        assertTrue(ally2.isTranceOver(fiveSeconds));
    }

    @Test
    public void attackTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2), new Pair<>(0, 3)));
        Slug slug = new Slug(pos);
        AlliedSoldier ally1 = new AlliedSoldier(pos);
        ally1.attack(slug);
        assertEquals(slug.getHp(), 30-8);
        ally1.attack(slug);
        ally1.attack(slug);
        ally1.attack(slug);
        assertEquals(slug.getHp(), -2);
    }

    @Test
    public void reactivateldEnemyTest(){
        PathPosition pos = new PathPosition(0, Arrays.asList(new Pair<>(0, 1), new Pair<>(0, 2)));
        Slug enemy = new Slug(pos);
        Duration fiveSeconds = Duration.ofSeconds(5);
        AlliedSoldier ally = new AlliedSoldier(pos, fiveSeconds, enemy);
        enemy.setInTrance(true);
        // check enemy is tranced
        assertTrue(enemy.getInTrance());
        // check enemy is not tranced and same hp as ally
        ally.reactivateOldEnemy();
        assertFalse(enemy.getInTrance());
        assertEquals(enemy.getHp(), ally.getHp());
    }
}
