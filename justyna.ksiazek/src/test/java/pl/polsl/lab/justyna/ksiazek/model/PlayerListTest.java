package pl.polsl.lab.justyna.ksiazek.model;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Testing of the methods of class {@link PlayerList}
 * 
 * @author Justyna Ksiazek
 * @version 1.0
 * @since 2.0
 */
public class PlayerListTest {
    /** tester object */
    private PlayerList tester;
    
    /** sets up tests */
    @BeforeEach
    public void setUp() {
        tester = new PlayerList(); 
    }
    
    /** 
     * Test of findPlayer method, of class PlayerList.
     * Checks if method returns null when list is empty or doesn't contain the object. 
     * Checks id method returns null when name is empty.
     * @param name names of player were looking for
     */
    @ParameterizedTest
    @ValueSource(strings = {"","aaa","bbb","CCC"})
    public void testFindPlayer(String name) {
        //GIVEN
        tester.setPlayer("ccc");
        
        //WHEN
        Player result = tester.findPlayer(name);
        
        //THEN
        assertEquals(result, null, "If the list or name is empty or name doesnt match objects in  list then return null");
    }   
}
