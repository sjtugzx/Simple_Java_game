package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.WarringStatesGame.stringArray;
import static org.junit.Assert.*;

public class WarringStatesGameTest {

    @Test
    public void stringArrayTest() {

        assertEquals("AGMSY4",concat(stringArray("AGMSY4",'A','4')));
        assertEquals("ABCDEF",concat(stringArray("ABCDEF",'A','C')));
        assertEquals("Z0123",concat(stringArray("YHZ0123",'Z','1')));
        assertEquals("BHNT",concat(stringArray("BHNTZ5",'Z','B')));
        assertEquals("STUVWX",concat(stringArray("STUVWX",'S','X')));
        assertEquals("NOPQR",concat(stringArray("MNOPQR",'N','R')));
        assertEquals("BHNTZ5",concat(stringArray("BHNTZ5",'B','T')));
        assertEquals("28",concat(stringArray("EKQW28",'2','8')));
        assertEquals("1DJPV",concat(stringArray("DJPV17",'7','1')));
        assertEquals("HIG",concat(stringArray("GHIJKL",'J','H')));

    }

    public String concat(String[] strings) {
        String test = "";
        for (int i = 0; i < strings.length; i++) {
            test += strings[i];
        }
        return test;
    }
}