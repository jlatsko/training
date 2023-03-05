package com.jim.codesignal;

import java.text.ParseException;
import java.util.Optional;

public class CodeSignal {
    private static final String INVALID_STRING = "No Matching Close Parenthesis for Open Paren at position %s";

    public String reverseCharsInParentheses(String s) throws ParseException {
        // and value is a Record containing the reversed string between the parens and if it is nested
        int prevClosePPos = 0;
        int openPPos = s.indexOf("(");
        if (openPPos == -1) {
            throw new ParseException(INVALID_STRING, openPPos);
        }

        StringBuffer result = new StringBuffer(s.substring(0, openPPos));

        do {
            // TODO need to set isNested based on the next parenthesis. If the next parenthesis is open than isNested = true
            //  and openPPos becomes the position of the nested open parenthesis.
            //  matchParens = processStringForNestedParens(s, openPPos)
            MatchParens matchParens = processStringForNestedParens(s, openPPos).get();

            Optional<MatchParens> optionalMatchParens = findMatchingCloseParenAndReverse(matchParens);
            // TODO - when isNested==true, we need the nested reversed chars and go back to beginning of the loop, do NOT append to result
            matchParens = optionalMatchParens.get();
            result.append(matchParens.reversed());

            prevClosePPos = matchParens.startSearchPos();  // 1 char past the close paren
//            if (((s.indexOf("(", openPPos)) == -1) && (!s.substring(openPPos, s.length()).isEmpty()))
//            if (!s.substring(prevClosePPos, s.length()).isEmpty())
            if (((s.indexOf("(", prevClosePPos)) != -1) && (!s.substring(prevClosePPos, s.indexOf("(", prevClosePPos)).isEmpty()))
                result.append(s.substring(prevClosePPos, s.indexOf("(", prevClosePPos)));

        } while ((openPPos = s.indexOf("(", prevClosePPos)) != -1);  // end do while

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
        int nextOpenPPos;
        int closePPos;
        if (matchParens.isNested()) {
            // search backwards, find the outermost enclosing parens
            int nestedOpenPPos = matchParens.startSearchPos();
            closePPos = matchParens.reversed().indexOf(")", matchParens.startSearchPos() + 1);

            String reversed = reverseStringBetweenParens(matchParens.reversed(), nestedOpenPPos, closePPos).orElse("NOT FOUND");
            return Optional.of(new MatchParens(reversed, matchParens.reversed().lastIndexOf("(", matchParens.startSearchPos()), false));
        }
        // nominal case, no nesting
        /// nextOpenPPos = matchParens.reversed().indexOf("(", matchParens.startSearchPos()+1);
        closePPos = matchParens.reversed().indexOf(")", matchParens.startSearchPos());

        String reversed = reverseStringBetweenParens(matchParens.reversed(), matchParens.startSearchPos(), closePPos).orElse("NOT FOUND");
        return Optional.of(new MatchParens(reversed, closePPos + 1, false));
    }

    private Optional<String> reverseStringBetweenParens(String s, int openPPos, int closePPos) {
        String revSegment = new StringBuilder(s.substring(openPPos + 1, closePPos))
                .reverse()
                .toString();
        return Optional.of(revSegment);
    }
}
