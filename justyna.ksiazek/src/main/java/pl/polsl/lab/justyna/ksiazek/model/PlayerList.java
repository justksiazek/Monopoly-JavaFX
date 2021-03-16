package pl.polsl.lab.justyna.ksiazek.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representation of list of all playing players.
 * 
 * @author Justyna
 * @version 1.0
 * @since 2.0
 */
public class PlayerList {
    /** List of players */
    List<Player> players = new ArrayList();
    
    /** Initiates a {@link PlayerList} object. */
    public PlayerList() {}
    
    /**
     * Initiates a {@link PlayerList} object with number of players set in config.
     * @param config configuration object carrying number of players
     * @param args strings with arguments passed as quick start parameters
     */
    public PlayerList(Config config, String... args) {
        for (int i = 1; i < config.getNumberOfPlayers()+1; i++)
            this.players.add(new Player(args[i]));
    }

    /**
     * Gets list of the players.
     * @return list of the players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Looks for player in a list and returns him
     * @param name name of the player method is looking for
     * @return found player
     */
    public Player findPlayer(String name) {
        for(Player player : players)
            if(player.getName().equals(name))
                return player;
        return null;
    }

    /**
     * Adds player to the list.
     * @param name name of the player.
     */
    public void setPlayer(String name) {
        if (name.equals("")) {
            return;
        }
        this.players.add(new Player(name));
    }
}
