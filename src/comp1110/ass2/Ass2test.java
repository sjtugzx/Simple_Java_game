package comp1110.ass2;

import org.junit.Test;

import java.util.Random;

import static java.lang.Character.isDigit;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class Ass2test {
    static boolean threeCharacters(String cardString){
        if (cardString.length()==3){
            return true;
        } else {
            return false;
        }


    }

    static boolean firstInRange(char ch) {
        if ((ch >= 97 && ch <= 103) || (ch == 122)) {
            return true;
        } else return false;
    }

    static boolean thirdInRange(char ch)
    {
        if (((ch>=65)&&(ch<=90))||((ch>=48)&&(ch<=57)))
        {
            return true;
        } else return false;
    }


    @Test
    public void testJudge(){
        //simple test
        String [] str=new String[]{"a","abc","bbbbbb"};

        assertTrue(threeCharacters("abc"));
        assertFalse(threeCharacters(str[0]));
        assertFalse(threeCharacters(str[2]));
        //random length test
        for (int i=0;i<10;++i)
        {
            Random rand=new Random();
            int length=(int)(Math.random()*20);


            StringBuffer sBuffer=new StringBuffer();
            for (int j=0; j< length ;++j)
            {
                sBuffer.append("a");
            }

            if (length==3)
            {
                assertTrue(threeCharacters(sBuffer.toString()));
            } else assertFalse(threeCharacters(sBuffer.toString()));

        }



    }
    static boolean isCardPlacementWellFormed(String cardPlacement) {
        // FIXME Task 2: determine whether a card placement is well-formed]

        char kingdom = cardPlacement.charAt(0);
        char num = cardPlacement.charAt(1);
;
        boolean flag1=false;
        boolean flag2=false;
        boolean flag3=false;

        flag1=threeCharacters(cardPlacement);
        flag2=firstInRange(kingdom);

        flag3=thirdInRange(cardPlacement.charAt(2));
        if(flag1&&flag2&&flag3&&isDigit(num)&&((kingdom == 'a' && num < 57)
                || (kingdom == 'b' && num < 55)
                || (kingdom == 'c' && num < 54)
                || (kingdom == 'd' && num < 53)
                || (kingdom == 'e' && num < 52)
                || (kingdom == 'f' && num < 51)
                || (kingdom == 'g' && num < 50)
                || (kingdom == 'z' && num == 57)))
        {
            return true;
        } else return false;

    }
}
