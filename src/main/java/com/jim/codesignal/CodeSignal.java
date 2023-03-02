package com.jim.codesignal;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class CodeSignal {
    private static final String INVALID_STRING = "No Matching Close Parenthesis for Open Paren at position %s";

    public String reverseCharsInParentheses(String s) throws ParseException {
        // create a Map where key=count of open close paren pairs
        // and value is a Record containing both open and close paren positions
        Map<Integer, MatchParens> parenMap = new HashMap<>();
        int searchPos = 0, cnt = 0;
        boolean isNested = false;

        while ((searchPos = s.indexOf("(", searchPos)) != -1) {

//            MatchParens matchParens = findMatchingCloseParenPos(s, searchPos, isNested).orElseThrow(() -> new ParseException(msg, -1));
            Optional<MatchParens> optMatchParens = findMatchingCloseParenPos(s, searchPos, isNested);
            if (optMatchParens.isPresent()) {
                MatchParens matchParens = optMatchParens.get();
                parenMap.put(cnt++, matchParens);
                if (matchParens.isNested()) {
//                    if (isNestedCnt == 1) {
//                        searchPos = matchParens.closeParenPos();
//                        // only process 1 level deep nesting
//                        isNested = false;
//                    } else {
                    searchPos--;
                    isNested = true;
//                        isNestedCnt++;
//                    }
                } else
//                    searchPos++;
                    searchPos = matchParens.closeParenPos();
            } else {
                String msg = String.format(INVALID_STRING, searchPos);  // TODO, probably better way since only for exception
                throw new ParseException(msg, -1);
            }
        }

        // TODO iterate on parenMap and rebuild the string by recursively calling const + reverse portions
        StringBuilder result = new StringBuilder();
        Iterator<MatchParens> iter = parenMap.values().iterator();
        int previousCloseParenPos = 0;
        while (iter.hasNext()) {
            MatchParens matchParens = iter.next();
            String constPart, revSegment, constBackPart;
            if (result.isEmpty()) {
                constPart = s.substring(previousCloseParenPos, matchParens.openParenPos());
                revSegment = new StringBuilder(s.substring(matchParens.openParenPos() + 1, matchParens.closeParenPos()))
                        .reverse()
                        .toString();

                constBackPart = s.substring(matchParens.closeParenPos() + 1);
                result.append(constPart).append(revSegment).append(constBackPart);
            } else {
                constPart = result.substring(0, matchParens.openParenPos());
                revSegment = new StringBuilder(result.substring(matchParens.openParenPos() + 1, result.indexOf(")")))
                        .reverse()
                        .toString();
                constBackPart = result.substring(previousCloseParenPos);
                result = new StringBuilder(constPart + revSegment + constBackPart);
            }
//            result.append(constPart).append(revSegment).append(constBackPart);

            if (matchParens.isNested())
                previousCloseParenPos = matchParens.closeParenPos();
            else
                previousCloseParenPos = matchParens.closeParenPos() + 1;
        }
        // get the final portion of the string following the last close paren.
//        String backConstPart = s.substring(parenMap.get(parenMap.size() - 1).closeParenPos() + 1);
//        result.append(backConstPart);

        return result.toString();
    }

    /**
     * finds the matching closing parenthesis position
     * edge case is considered when no closing parenthesis exists, hence Option.isEmpty()
     *
     * @param searchString - obvious
     * @param startPos     - open parenthesis position
     * @param isNested     - denotes the parens are the outermost parens in a nested scenario
     * @return - obvious
     */
    private Optional<MatchParens> findMatchingCloseParenPos(String searchString, int startPos, boolean isNested) {
        int nextOpenPPos;
        int closePPos;
        if (isNested) {
            // search backwards
            nextOpenPPos = searchString.lastIndexOf("(", startPos);
            // skip over the next close paren and get the second one
            int skipClose = searchString.indexOf(")", startPos);
            closePPos = searchString.indexOf(")", skipClose + 1);
            // NOW we need to set searchPos, in the while loop to look after the closePPos
        } else {
            nextOpenPPos = searchString.indexOf("(", startPos + 1);
            closePPos = searchString.indexOf(")", startPos);
        }

        if (closePPos == -1) {
            String msg = "No Matching Close Parenthesis for Open Paren at " + nextOpenPPos;
            return Optional.empty();
        }

        if ((nextOpenPPos != -1) && (nextOpenPPos < closePPos) && (!isNested)) {
//            // TODO nested parens case,
//           // Now we need to find the inner parens first than the outer and put them in the map in that order
//            // so on the second (outer) pass, we need to ignore the inner parens
//            // ADDED, isNested to MatchParens Record
//
            System.out.println("Detected nested parentheses, closing pos " + closePPos);
            MatchParens matchParens = new MatchParens(nextOpenPPos, closePPos, true);
            return Optional.of(matchParens);
        } else {
            MatchParens matchParens = new MatchParens(startPos, closePPos, false);
            return Optional.of(matchParens);
        }
    }
}
