package com.jim.leettest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class LeetTest {
    @Test
    public void romanToIntStreamTest() {
        String s = "VII";
        AtomicInteger result = new AtomicInteger();

        IntStream intStream = s.chars();
        intStream.forEach(i -> {
            char c = (char) i;
            char previousC;
//            if (isCGreaterThanPrevC(c, previousC)) {
//
//            }

            switch (c) {
                case 'I': {
                    result.getAndIncrement();
                    break;
                }
                case 'V': {
                    result.getAndAdd(5);
                    break;
                }
                case 'X': {
                    result.getAndAdd(10);
                    break;
                }
                case 'L': {
                    result.getAndAdd(50);
                    break;
                }
                case 'C': {
                    result.getAndAdd(100);
                    break;
                }
                case 'D': {
                    result.getAndAdd(500);
                    break;
                }
                case 'M': {
                    result.getAndAdd(1000);
                    break;
                }
            }
            previousC = c;
        });

        System.out.println("Final result: " + result.get());
    }

    @Test
    /**
     * imperative style
     */
    public void romanToIntArrayTest() {
        String s = "IX";
        char[] sAr = s.toCharArray();
        int result = 0;

        Character prevC = null;
        for (char c : sAr) {
            int tempResult = isCGreaterThanPrevC(c, prevC);
            if (tempResult == 0) {
                // Switch statement on Roman Chars
                switch (c) {
                    case 'I': {
                        result++;
                        break;
                    }
                    case 'V': {
                        result = result + 5;
                        break;
                    }
                    case 'X': {
                        result = result + 10;
                        break;
                    }
                    case 'L': {
                        result = result + 50;
                        break;
                    }
                    case 'C': {
                        result = result + 100;
                        break;
                    }
                    case 'D': {
                        result = result + 500;
                        break;
                    }
                    case 'M': {
                        result = result + 1000;
                        break;
                    }
                }
            } else {
                result = result + tempResult;
            }
            prevC = c;
        }
        System.out.println("Completed romanToIntArrayTest: " + result);
    }

    public int isCGreaterThanPrevC(char c, Character prevC) {
        if (prevC == null)
            return 0;

        if ((c == 'V') && (prevC == 'I'))
            return 3;
        if ((c == 'X') && (prevC == 'I'))
            return 8;
        else
            return 0;
    }

    @Test
    public void sortStringUsingReferenceString() {
        String refStr = "sitgo";
        String longStr = "thisIsALongString";

        Set<Character> refSet = new HashSet();
        for (Character c : refStr.toCharArray())
            refSet.add(c);

        Map<String, String> refMap = new HashMap<String, String>();

        for (Character keyC : longStr.toCharArray()) {
            if (!refSet.contains(keyC)) {
                if (refMap.containsKey("*")) {
                    String value = refMap.get("*");
                    String newValue = value + keyC;
//                    System.out.println("Updating OTHER from: " + value + " to " + newValue);
                    refMap.put("*", newValue);
                } else {
//                    System.out.println("Adding " + keyC + " to OTHER");
                    refMap.put("*", keyC.toString());
                }
            } else {
                if (refMap.containsKey(keyC.toString())) {
                    String value = refMap.get(keyC.toString());
//                    System.out.println("Updating " + keyC.toString() + " from: " + value + " to " + value+keyC.toString());
                    refMap.put(keyC.toString(), value + keyC);
                } else
                    refMap.put(keyC.toString(), keyC.toString());
            }
        }

        StringBuffer result = new StringBuffer();
        Iterator iter = refMap.values().iterator();
        while (iter.hasNext()) {
            result.append(iter.next());
        }

        System.out.println("Result is: " + result);

    }

    /**
     * input {"flower", "flowers", "flour", "four"};
     * find the common prefix
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";

        if (strs.length == 1)
            return strs[0];

        // find shortest string in array
        List<String> strList = Arrays.asList(strs);
        Optional<String> shortestStr = strList.stream().reduce((x, y) -> x.length() < y.length() ? x : y);

        // if shortest string is empty
        if (shortestStr.get().isEmpty())
            return "";

        // use shortest string for maximum prefix query
        String commonPre;
        int index = 1;
        String prefix = shortestStr.get().substring(0, index);
        System.out.println("testing for prefix: " + prefix);
        while (isPrefixCommon(prefix, strs)) {
            index++;
            if (index != shortestStr.get().length())
                prefix = shortestStr.get().substring(0, index);
            else
                break;
        }
        if (index == 1)
            return "";
        else if (index == shortestStr.get().length())
            return shortestStr.get();
        else
            return shortestStr.get().substring(0, index - 1);
    }

    public boolean isPrefixCommon(String prefix, String[] strs) {
        for (int i = 0; i != strs.length; i++) {
            if (strs[i].startsWith(prefix))
                continue;
            else
                return false;
        }
        return true;
    }

    @Test
    public void testPalindrome() {
        isPalindrome(121);
    }

    public boolean isPalindrome(int x) {
        Integer input = new Integer(x);
        char[] inputCAr = input.toString().toCharArray();

        if (inputCAr.length == 1)
            return true;

        int frontIndx = 0;
        int backIndx = inputCAr.length - 1;
        while (isPalindrome(inputCAr, frontIndx, backIndx)) {
            if ((backIndx - frontIndx) <= 1)
                return true;
            frontIndx++;
            backIndx--;
        }
        return false;
    }

    public boolean isPalindrome(char[] inputCAr, int frontIndx, int backIndx) {
        if ((backIndx - frontIndx) > 0) {
            // System.out.println("frontx: "+frontIndx+" backx: "+backIndx+" "+inputCAr[frontIndx]+" "+inputCAr[backIndx]);
            char frontC = inputCAr[frontIndx];
            char backC = inputCAr[backIndx];
            return frontC == backC;
        } else return true;
    }

    @Test
    public void test1() {
        LocalDate date = LocalDate.of(2017, Month.JANUARY, 1);
        date.plusDays(10);
        System.out.println(date);
    }

    @Test
    public void calcEntities() {
        Entity one = new Entity("a");
        Entity two = new Entity("b");
        ArrayList<Entity> entityList = new ArrayList<Entity>();
        entityList.add(one);
        entityList.add(two);
        Entity three = new Entity("c", entityList);
        Entity four = new Entity("d", Arrays.asList(three));

        List<Entity> entities = new ArrayList<Entity>();
        entities.add(four);

        System.out.println(ListToMap.convertListToMap(entities).toString());
    }




    public Integer calculateAvg(List<Integer> values) {
        int sum = values.stream().mapToInt(Integer::intValue).sum();
        return sum / values.size();
    }

//    private List<Character> findCommonChars(String s1, String s2) {
//        char[] s1Chars = s1.toCharArray();
//        char[] s2Chars = s2.toCharArray();
//
//        if (s1.length() < s2.length()) {
//            commonC = findCommonChars(s1, s2);
//        } else
//            commonC = findCommonChars(s2, s1);

    // check s1 for any matching char in s2
//        boolean match = s1.codePoints().anyMatch(cp -> s2.codePoints().findAny(cp).isPresent());
//        boolean m = s2.codePoints().filter(s1.codePoints() -> {c}.findAny();
    // sort s1
    // s1.codePoints().mapToObj(c -> String.valueOf((char) c)).sorted().forEach(System.out::println);
    //  s1.codePoints().sorted().forEach(c -> System.out.println((char) c));

//        if (s1.codePoints().anyMatch(cp -> s2.contains((char) cp);))
//        { System.out.println("Match on " + s1);}

//        return new ArrayList<Character>();
//    }


}

class Student {
    String name;
    float gpa;
    List<Integer> scores;
}

class Entity {
    String id;
    List<Entity> children;

    public Entity(String id, List<Entity> children) {
        this.id = id;
        this.children = children;
    }

    public Entity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public String toString() {
        List<String> childIds = this.children.stream().map(Entity::getId).collect(toList());
        StringBuffer output = new StringBuffer("[ ");
        for (String id : childIds)
            output.append(id + " ");
        output.append("]");
        return output.toString();
    }
}

//  d [c [a, b]]
// [d,c,a,b]
class ListToMap {

    static Map<String, Entity> resultMap = new HashMap<String, Entity>();

    public static Map<String, Entity> convertListToMap(List<Entity> entities) {
        // Start the map
        if (entities.isEmpty() || (entities == null))
            return resultMap;

        for (Entity entity : entities) {
            resultMap.put(entity.id, entity);
            while (doesEntityHaveChildren(entity) == true) {
                System.out.println("Entity: " + entity.id + " has children. ");
                for (Entity entity1 : entity.children) {
                    recursivelyProcessEntity(entity1);
                    entity = entity1;
                }
            }
            System.out.println("DONE processing Entity: " + entity.id);
        }

        return resultMap;
    }


    public static void recursivelyProcessEntity(Entity entity) {
        if (doesEntityHaveChildren(entity)) {
            resultMap.put(entity.id, entity);
        } else {
            System.out.println("Entity: " + entity.getId() + " does not children. ");
            resultMap.put(entity.id, null);
        }
    }

    public static boolean doesEntityHaveChildren(Entity entity) {
        if (entity.getChildren() == null)
            return false;

        return !entity.getChildren().isEmpty();
    }
}


//class A{
//    void methodA() {
//        System.out.println("methodA()");
//    }
//
//    static class B{
//        void methodB() {
//            System.out.println("method B()");
//            methodA();
//        }
//    }
//}






