package swfw.ch11.chess.Manager;

import swfw.ch11.chess.Model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
    private static final List<Player> players = new ArrayList<>();

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static List<Player> getPlayers() {
        return players;
    }
}

