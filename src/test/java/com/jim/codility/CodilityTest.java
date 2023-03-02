package com.jim.codility;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodilityTest {
    private Codility codility;


    @Test
    public void BinaryGapTest() {
//        int given=123;
        int given = 5903;
        char[] givenBCAr = Integer.toBinaryString(given).toCharArray();
        System.out.println("Binary String " + Integer.toBinaryString(given));

        final char zeroC = '0';
        int largestGap = 0;
        int[] onesPosAr = new int[givenBCAr.length - 1];
        for (int idx = 1; idx != givenBCAr.length - 1; idx++) {
            if (givenBCAr[idx] == zeroC) {
                int tmplargestGap = lookAheadAndFindNextOne(idx, givenBCAr);
                if (tmplargestGap > largestGap)
                    largestGap = tmplargestGap;
                idx += largestGap;
            }
        }
        assertEquals(4, largestGap);
    }

    public int lookAheadAndFindNextOne(int zeroCursor, char[] cAr) {
        System.out.println("inside lookAhead, starting 0 at " + zeroCursor);
        int idx = zeroCursor;

        final char zeroC = '0';
        while ((idx != cAr.length - 1) && (cAr[idx] == zeroC)) {
            idx++;
            System.out.println("inside lookAhead, found a 0 at position " + idx);
        }
        return idx - zeroCursor;
    }

    @Test
    public void rotateArrayByIndex() {
        int[] ar = {3, 8, 9, 7, 6};
        int idx = 3;
//        int[] ar = {1};
//        int idx = 4;

        int[] result = new int[ar.length];
        if ((ar.length <= 1) || (idx == 0))
            result = ar;
        else {
            // Map key=original position, value=new position that is shifted to the right by index
            Map<Integer, Integer> arPosMap = new HashMap<>();
            for (int arPos = 0; arPos < ar.length; arPos++) {
                int rotatedIdx = idx + arPos;
                if (rotatedIdx >= ar.length)
                    rotatedIdx = rotatedIdx - ar.length;
                arPosMap.put(arPos, rotatedIdx);
            }

            Iterator iter = arPosMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iter.next();
                result[entry.getValue()] = ar[entry.getKey()];
            }
        }

//        Assertions.assertThat(result).containsExactly(1);
        Assertions.assertThat(result).containsExactly(9, 7, 6, 3, 8);
    }

    @Test
    public void streamRotateArrayByIndex() {
        int[] ar = {3, 8, 9, 7, 6};
        int idx = 3;

        codility = new Codility();
        int[] result = codility.streamRotateArrayByIndex(ar, idx);
        assertThat(result).containsExactly(9, 7, 6, 3, 8);

        System.out.println("\nSTARTING SECOND TEST");
        int[] ar1 = {1};
        int idx1 = 4;
        int[] result2 = codility.streamRotateArrayByIndex(ar1, idx1);
        assertThat(result2).containsExactly(1);
    }

    @Test
    public void streamRotateArrayByIndex2() {
        int[] ar = {3, 8, 9, 7, 6};
        int idx = 3;

        codility = new Codility();
        int[] result = codility.addShiftToEachArrayElement(ar, idx);
        assertThat(result).containsExactly(6, 11, 12, 10, 9);
    }
}
