package com.jim.codesignal;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class CodeSignal {
    private static final String INVALID_STRING = "No Matching Close Parenthesis for Open Paren at position %s";

    public String reverseCharsInParentheses(String s) throws ParseException {
        List<MatchParens> mpList = new ArrayList<>();
        // and value is a Record containing the reversed string between the parens and if it is nested
        int prevClosePPos = 0;

        int openPPos = s.indexOf("(");
        if (openPPos == -1) {
            throw new ParseException(INVALID_STRING, openPPos);
        }

        do {
            MatchParens matchParens = processStringForNestedParens(s, openPPos).get();

            if (matchParens.isNested()) {
                // TODO - do something special, we have to process the inner parens first and than the outer
                matchParens = processNestedParensAndReverse(matchParens).get();
                mpList.add(matchParens);
            } else {
                matchParens = findMatchingCloseParenAndReverse(matchParens).get();
                mpList.add(matchParens);
            }

            prevClosePPos = matchParens.startSearchPos();  // 1 char past the close paren
        } while ((openPPos = s.indexOf("(", prevClosePPos)) != -1);  // end do while

        // Build the result
        Iterator mpIter = mpList.listIterator();
        StringBuffer result = new StringBuffer(s.substring(0, s.indexOf("(")));
        while (mpIter.hasNext()) {
            MatchParens mp = (MatchParens) mpIter.next();
            if (mp.isNested()) {
                int nestedClosePPos = mp.startSearchPos() - 1;
                int nestedOpenPPos = s.lastIndexOf("(", nestedClosePPos);
                openPPos = s.lastIndexOf("(", nestedOpenPPos);
                int closePPos = s.indexOf(")", nestedClosePPos);

                result.append(s.substring(openPPos, nestedOpenPPos));
                result.append(mp.reversed());
                result.append(s.substring(nestedClosePPos, closePPos));
            } else {
                result.append(mp.reversed());
                if (s.indexOf("(", mp.startSearchPos()) != -1) {
                    result.append(s.substring(mp.startSearchPos(), s.indexOf("(", mp.startSearchPos())));
                } else {
                    result.append(s.substring(mp.startSearchPos(), s.length()));
                }
            }
        }

        return result.toString();
    }

    public Optional<MatchParens> processStringForNestedParens(String s, int startSearchPos) {
        int nextOpenPPos = s.indexOf("(", startSearchPos + 1);
        int closePPos = s.indexOf(")", startSearchPos);
        if ((nextOpenPPos != -1) && (nextOpenPPos < closePPos)) {
            // isNested = true
            return Optional.of(new MatchParens(s, nextOpenPPos, true));
        }
        return Optional.of(new MatchParens(s, startSearchPos, false));
    }

    /**
     * Given that the startSearchPos is either 0 or the char of an open paren; this methods finds
     * the matching closing paren position and reverses the chars between the parentheses.
     * <p>
     * NOTE:  The calling method has to determine the open paren position.
     * NOTE:  The calling method has to account for nested parens and set isNested to true.
     *
     * @return - obvious
     */
    public Optional<MatchParens> findMatchingCloseParenAndReverse(MatchParens matchParens) {
        int closePPos;
        closePPos = matchParens.reversed().indexOf(")", matchParens.startSearchPos());

        String reversed = reverseStringBetweenParens(matchParens.reversed(), matchParens.startSearchPos(), closePPos).orElse("NOT FOUND");
        return Optional.of(new MatchParens(reversed, closePPos + 1, false));
    }

    /**
     * @param matchParens
     * @return
     */
    public Optional<MatchParens> processNestedParensAndReverse(MatchParens matchParens) {
        if (matchParens.isNested()) {
            int nestedOpenPPos = matchParens.startSearchPos();
            int nestedClosePPos = matchParens.reversed().indexOf(")", matchParens.startSearchPos() + 1);
            String nestedReversed = reverseStringBetweenParens(matchParens.reversed(), nestedOpenPPos, nestedClosePPos).orElse("NOT FOUND");

            // now process the outer parens
            int outerOpenPPos = matchParens.reversed().lastIndexOf("(", matchParens.startSearchPos() - 1);
            int outerClosePPos = matchParens.reversed().indexOf(")", nestedClosePPos + 1);

            StringBuffer combinedStr = new StringBuffer(matchParens.reversed().substring(outerOpenPPos + 1, nestedOpenPPos));
            combinedStr.append(nestedReversed);
            combinedStr.append(matchParens.reversed().substring(nestedClosePPos + 1, outerClosePPos));
            combinedStr.reverse();

            return Optional.of(new MatchParens(combinedStr.toString(), outerClosePPos + 1, false));
        }
        return Optional.empty();
    }

    private Optional<String> reverseStringBetweenParens(String s, int openPPos, int closePPos) {
        String revSegment = new StringBuilder(s.substring(openPPos + 1, closePPos))
                .reverse()
                .toString();
        return Optional.of(revSegment);
    }
}
