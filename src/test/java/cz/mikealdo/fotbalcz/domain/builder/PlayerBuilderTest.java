package cz.mikealdo.fotbalcz.domain.builder;

import cz.mikealdo.football.domain.Player;
import cz.mikealdo.football.domain.PlayerPosition;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class PlayerBuilderTest {

    PlayerBuilder builder;

    @Test
    public void shouldCreateBasicPlayer() throws Exception {
        String name = "name";
        int fotbalCzId = 234;
        int dressNumber = 10;
        builder = new PlayerBuilder(name, fotbalCzId, "Ú", dressNumber);

        Player player = builder.build();

        assertBasicAttributes(player, name, fotbalCzId, dressNumber, PlayerPosition.FORWARD);
    }

    private void assertBasicAttributes(Player player, String name, int fotbalCzId, int dressNumber, PlayerPosition playerPosition) {
        assertEquals(player.getName(), name);
        assertEquals(player.getFotbalCzId(), Integer.valueOf(fotbalCzId));
        assertEquals(player.getPlayerPosition(), playerPosition);
        assertEquals(player.getDressNumber(), Integer.valueOf(dressNumber));
    }

    @Test
    public void shouldCreateFullyPopulatedPlayer() throws Exception {
        String name = "name";
        int fotbalCzId = 234;
        int dressNumber = 10;
        builder = new PlayerBuilder(name, fotbalCzId, "Ú", dressNumber);

        Player player = builder
                .inMainLineUp(true)
                .captain(true)
                .firstSubstitutionMinute(Optional.of(1))
                .secondSubstitutionMinute(Optional.of(2))
                .redCardInMinute(Optional.of(3))
                .firstYellowCardInMinute(Optional.of(3))
                .secondYellowCardInMinute(Optional.of(4))
                .build();

        assertBasicAttributes(player, name, fotbalCzId, dressNumber, PlayerPosition.FORWARD);
        assertTrue(player.isCaptain());
        assertTrue(player.isInMainLineUp());
        assertEquals(player.getFirstSubstitutionMinute(), Integer.valueOf(1));
        assertEquals(player.getSecondSubstitutionMinute(), Integer.valueOf(2));
        assertEquals(player.getRedCardInMinute(), Integer.valueOf(3));
        assertEquals(player.getFirstYellowCardInMinute(), Integer.valueOf(3));
        assertEquals(player.getSecondYellowCardInMinute(), Integer.valueOf(4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForInvalidPlayerPosition() throws Exception {
        new PlayerBuilder("", 1, "invalid", 1);
    }
}