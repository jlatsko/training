package com.jim.codesignal;

/**
 * MatchParens
 *
 * @param reversed       - obvious, see unit tests for findMatchingCloseParenAndReverse.
 * @param startSearchPos - depends: before calling findMatchingCloseParenAndReverse, open parenthesis position
 *                       response from findMatchingCloseParenAndReverse, close parenthesis position + 1
 * @param isNested       - when true, the startSearchPos parens is the outermost parens in a nested scenario
 */
public record MatchParens(String reversed, int startSearchPos, boolean isNested) {

}
