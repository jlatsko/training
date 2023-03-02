package com.jim.codesignal;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeSignalTest {

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
        Integer input = Integer.valueOf(x);
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

    @Test
    public void missingSequenceNums() {
        List missingNums = new ArrayList<Integer>();
        int[] statues = {6, 2, 3, 8};
        Arrays.sort(statues);
        int oldValue = statues[0];
        for (int indx = 1; indx != statues.length; indx++) {
            if (oldValue + 1 != statues[indx]) {
                System.out.println("index: " + indx + " oldValue: " + oldValue + " statues: " + statues[indx]);
                missingNums.add(oldValue + 1);
                oldValue = oldValue + 1;
                indx--;
            } else
                oldValue = statues[indx];
        }
        missingNums.stream().forEach(System.out::println);

        assertThat(missingNums).containsExactly(4, 5, 7);
    }

    @Test
    public void firstLastCharInArray() {
        String[] a = {"dog", "cat", "mouse", "bird"};
        String[] b = new String[a.length], fAr = new String[a.length], lAr = new String[a.length];
        Arrays.stream(a).map(item -> item.substring(0, 1)).collect(toList()).toArray(fAr);
        Arrays.stream(a).map(item -> item.substring(item.length() - 1)).collect(toList()).toArray(lAr);

        for (int indx = 0; indx != a.length; indx++) {
            if (indx + 1 == a.length)
                b[indx] = fAr[indx] + lAr[0];
            else
                b[indx] = fAr[indx] + lAr[indx + 1];
        }
        assertThat(b).containsExactly("dt", "ce", "md", "bg");
    }

    @Test
    public void highestAveScore() {
        String[] a = {"John", "5", "Karen", "8", "Gwen", "4", "John", "6", "Karen", "5", "Gwen", "4"};
        Map<String, List<Integer>> scoreMap = new HashMap<>();
        for (int indx = 0; indx != a.length; indx = indx + 2) {
            if (indx % 2 == 0)
                if (scoreMap.containsKey(a[indx])) {
                    scoreMap.get(a[indx]).add(Integer.parseInt(a[indx + 1]));
                } else {
                    List<Integer> sc = new ArrayList<>();
                    Integer grade = Integer.parseInt(a[indx + 1]);
                    sc.add(grade);
                    scoreMap.put(a[indx], sc);
                }
        }

        String name = "";
        Integer gpa = Integer.valueOf(0);
        for (Map.Entry<String, List<Integer>> entry : scoreMap.entrySet()) {
            Integer avg = calculateAvg(entry.getValue());
            if (avg > gpa) {
                name = entry.getKey();
                gpa = avg;
            }
//            Integer gpa = entry.getValue().stream().collect(averagingInt(v));
        }
        System.out.println("Person with hightest gpa: " + name);

        // use the name for groupingBy classifier
//        Map<String, List<Integer>> scoreStr = Arrays.stream(a).collect(
//                groupingBy((String)key -> {
//                    try { new Integer(key); } catch (NumberFormatException nfe) { return key; }
//                    return "";
//                } ));
//        assertThat(scores).containsExactly(scoreStr);
    }

    @Test
    public void commonCharacterCount() {
        String s1 = "zzzz";
        String s2 = "zzzzzz";

        List<String> commonC = new ArrayList<>();

        int startIdx = 0, endIdx = 1;
        while (endIdx <= s1.length()) {
            if (s2.indexOf(s1.substring(startIdx, endIdx)) != -1) {
                String tmp = s1.substring(startIdx++, endIdx++);
                commonC.add(tmp);
                s2 = s2.replaceFirst(tmp, "#");
            } else {
                startIdx++;
                endIdx++;
            }
        }
        assertThat(commonC).isEqualTo(4);
    }

    public Integer calculateAvg(List<Integer> values) {
        int sum = values.stream()
                .mapToInt(Integer::intValue)
                .sum();
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

    @Test
    public void sortByHeight() {
        // replace all elements != -1 with 111
        int[] a = {-1, 150, 190, 170, -1, -1, 160, 180};

        // create a sorted non negative list, List<Integer>
        List<Integer> sortedList = Arrays.stream(a)
                .filter(i -> i != -1)
                .sorted()
                .boxed()
                .collect(toList());
        sortedList.removeIf(i -> i == -1);   // remove the -1s

        Iterator<Integer> sortedListIter = sortedList.listIterator();
        Integer[] result = new Integer[a.length];
//        for (int idx=0; idx!=a.length; idx++) {
//            if (a[idx]== -1)
//                result[idx]=-1;
//            else
//               result[idx] = sortedListIter.next();
//        }

//      reference: https://stackoverflow.com/questions/28790784/java-8-preferred-way-to-count-iterations-of-a-lambda
//        AtomicInteger idx = new AtomicInteger(0); then use idx.getAndIncrement() inside the lamda
        // Also, https://commons.apache.org/proper/commons-lang/javadocs/api-release/org/apache/commons/lang3/mutable/MutableInt.html
        var wrapper = new Object() {
            int idx = 0;
        };
        Arrays.stream(a)
                .forEach(i -> {
                    if (i != -1)
                        result[wrapper.idx++] = sortedListIter.next();
                    else
                        result[wrapper.idx++] = i;
                });

        Integer[] expected = {-1, 150, 160, 170, -1, -1, 180, 190};
        assertThat(result).containsExactly(expected);
    }
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

    @Test
    public void reverseChars() throws ParseException {
        CodeSignal cs = new CodeSignal();

        String s1 = "(bar)";
        String s1Expected = "rab";
        assertEquals(s1Expected, cs.reverseCharsInParentheses(s1));

        String s2 = "foo(bar)baz";
        String s2Expected = "foorabbaz";
        assertEquals(s2Expected, cs.reverseCharsInParentheses(s2));

//        String s4 = "foo(bar)baz(blim)";
//        String s4Expected = "foorabbazmilb";
//        assertEquals(s4Expected,cs.reverseCharsInParentheses(s4));

        String s3 = "foo(bar(baz))blim";        // foo(barzab)blim -> foobazrabblim
        String s3Expected = "foobazrabblim";
        assertEquals(s3Expected, cs.reverseCharsInParentheses(s3));
    }
}







