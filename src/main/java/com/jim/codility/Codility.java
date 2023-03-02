package com.jim.codility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Codility {
    /**
     * @param ar
     * @param shift
     * @return
     */
    public int[] rotateArrayByIndex(int[] ar, int shift) {
        int[] result = new int[ar.length];
        if ((ar.length <= 1) || (shift == 0))
            result = ar;
        else {
            // Map key=original position, value=new position that is shifted to the right by index
            Map<Integer, Integer> arPosMap = new HashMap<>();
            for (int arPos = 0; arPos < ar.length; arPos++) {
                int rotatedIdx = shift + arPos;
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
        return result;
    }

    public int[] streamRotateArrayByIndex(int[] ar, int shift) {
        // create a List where each value = shifted position
        final int length = ar.length;
        final int[] arPositionAr = IntStream
                .rangeClosed(0, length - 1)
                .map(position -> {
                    if (length <= 1)
                        return 0;
                    else {
                        position = shift + position - 1;
                        if (position >= length)
                            position = position - length;
                        return position;
                    }
                })
                .toArray();

        return IntStream
                .rangeClosed(0, length - 1)
                .map(iadx -> {
                    return ar[arPositionAr[iadx]];
                })
                .toArray();
    }

    public int[] addShiftToEachArrayElement(int[] ar, int shift) {
        Stream<Integer> positions =
                Arrays.stream(ar)
                        .mapToObj(el -> {
                            return el + shift;
                        });

        return positions
                .mapToInt(x -> x)
                .toArray();
    }
}
