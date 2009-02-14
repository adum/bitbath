/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package java.lang;

import java.io.Serializable;

/**
 * <p>
 * Character is the wrapper for the primitive type <code>char</code>. This
 * class also provides a number of utility methods for working with
 * <code>char</code>s.
 * </p>
 * 
 * <p>
 * Character data is based upon the Unicode Standard, 4.0. The Unicode
 * specification, character tables and other information are available at <a
 * href="http://www.unicode.org/">http://www.unicode.org/</a>.
 * </p>
 * 
 * <p>
 * Unicode characters are referred to as <i>code points</i>. The range of valid
 * code points is U+0000 to U+10FFFF. The <i>Basic Multilingual Plane (BMP)</i>
 * is the code point range U+0000 to U+FFFF. Characters above the BMP are
 * referred to as <i>Supplementary Characters</i>. On the Java platform, UTF-16
 * encoding and <code>char</code> pairs are used to represent code points in
 * the supplementary range. A pair of <code>char</code> values that represent
 * a supplementary character are made up of a <i>high surrogate</i> with a
 * value range of 0xD800 to 0xDBFF and a <i>low surrogate</i> with a value
 * range of 0xDC00 to 0xDFFF.
 * </p>
 * 
 * <p>
 * On the Java platform a <code>char</code> value represents either a single
 * BMP code point or a UTF-16 unit that's part of a surrogate pair. The
 * <code>int</code> type is used to represent all Unicode code points.
 * </p>
 * 
 * @since 1.0
 */
public final class Character {
    private static final long serialVersionUID = 3786198910865385080L;

    private final char value;

    /**
     * The minimum possible Character value.
     */
    public static final char MIN_VALUE = '\u0000';

    /**
     * The maximum possible Character value.
     */
    public static final char MAX_VALUE = '\uffff';

    /**
     * The minimum possible radix used for conversions between Characters and
     * integers.
     */
    public static final int MIN_RADIX = 2;

    /**
     * The maximum possible radix used for conversions between Characters and
     * integers.
     */
    public static final int MAX_RADIX = 36;

//    /**
//     * The <code>char</code> {@link Class} object.
//     */
//    @SuppressWarnings("unchecked")
//    public static final Class<Character> TYPE = (Class<Character>) new char[0]
//            .getClass().getComponentType();

    // Note: This can't be set to "char.class", since *that* is
    // defined to be "java.lang.Character.TYPE";

    /**
     * Unicode category constant Cn.
     */
    public static final byte UNASSIGNED = 0;

    /**
     * Unicode category constant Lu.
     */
    public static final byte UPPERCASE_LETTER = 1;

    /**
     * Unicode category constant Ll.
     */
    public static final byte LOWERCASE_LETTER = 2;

    /**
     * Unicode category constant Lt.
     */
    public static final byte TITLECASE_LETTER = 3;

    /**
     * Unicode category constant Lm.
     */
    public static final byte MODIFIER_LETTER = 4;

    /**
     * Unicode category constant Lo.
     */
    public static final byte OTHER_LETTER = 5;

    /**
     * Unicode category constant Mn.
     */
    public static final byte NON_SPACING_MARK = 6;

    /**
     * Unicode category constant Me.
     */
    public static final byte ENCLOSING_MARK = 7;

    /**
     * Unicode category constant Mc.
     */
    public static final byte COMBINING_SPACING_MARK = 8;

    /**
     * Unicode category constant Nd.
     */
    public static final byte DECIMAL_DIGIT_NUMBER = 9;

    /**
     * Unicode category constant Nl.
     */
    public static final byte LETTER_NUMBER = 10;

    /**
     * Unicode category constant No.
     */
    public static final byte OTHER_NUMBER = 11;

    /**
     * Unicode category constant Zs.
     */
    public static final byte SPACE_SEPARATOR = 12;

    /**
     * Unicode category constant Zl.
     */
    public static final byte LINE_SEPARATOR = 13;

    /**
     * Unicode category constant Zp.
     */
    public static final byte PARAGRAPH_SEPARATOR = 14;

    /**
     * Unicode category constant Cc.
     */
    public static final byte CONTROL = 15;

    /**
     * Unicode category constant Cf.
     */
    public static final byte FORMAT = 16;

    /**
     * Unicode category constant Co.
     */
    public static final byte PRIVATE_USE = 18;

    /**
     * Unicode category constant Cs.
     */
    public static final byte SURROGATE = 19;

    /**
     * Unicode category constant Pd.
     */
    public static final byte DASH_PUNCTUATION = 20;

    /**
     * Unicode category constant Ps.
     */
    public static final byte START_PUNCTUATION = 21;

    /**
     * Unicode category constant Pe.
     */
    public static final byte END_PUNCTUATION = 22;

    /**
     * Unicode category constant Pc.
     */
    public static final byte CONNECTOR_PUNCTUATION = 23;

    /**
     * Unicode category constant Po.
     */
    public static final byte OTHER_PUNCTUATION = 24;

    /**
     * Unicode category constant Sm.
     */
    public static final byte MATH_SYMBOL = 25;

    /**
     * Unicode category constant Sc.
     */
    public static final byte CURRENCY_SYMBOL = 26;

    /**
     * Unicode category constant Sk.
     */
    public static final byte MODIFIER_SYMBOL = 27;

    /**
     * Unicode category constant So.
     */
    public static final byte OTHER_SYMBOL = 28;

    /**
     * Unicode category constant Pi.
     * @since 1.4
     */
    public static final byte INITIAL_QUOTE_PUNCTUATION = 29;

    /**
     * Unicode category constant Pf.
     * @since 1.4
     */
    public static final byte FINAL_QUOTE_PUNCTUATION = 30;

    /**
     * Unicode bidirectional constant.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_UNDEFINED = -1;

    /**
     * Unicode bidirectional constant L.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT = 0;

    /**
     * Unicode bidirectional constant R.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT = 1;

    /**
     * Unicode bidirectional constant AL.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC = 2;

    /**
     * Unicode bidirectional constant EN.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER = 3;

    /**
     * Unicode bidirectional constant ES.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_SEPARATOR = 4;

    /**
     * Unicode bidirectional constant ET.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_EUROPEAN_NUMBER_TERMINATOR = 5;

    /**
     * Unicode bidirectional constant AN.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_ARABIC_NUMBER = 6;

    /**
     * Unicode bidirectional constant CS.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_COMMON_NUMBER_SEPARATOR = 7;

    /**
     * Unicode bidirectional constant NSM.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_NONSPACING_MARK = 8;

    /**
     * Unicode bidirectional constant BN.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_BOUNDARY_NEUTRAL = 9;

    /**
     * Unicode bidirectional constant B.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_PARAGRAPH_SEPARATOR = 10;

    /**
     * Unicode bidirectional constant S.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_SEGMENT_SEPARATOR = 11;

    /**
     * Unicode bidirectional constant WS.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_WHITESPACE = 12;

    /**
     * Unicode bidirectional constant ON.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_OTHER_NEUTRALS = 13;

    /**
     * Unicode bidirectional constant LRE.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_EMBEDDING = 14;

    /**
     * Unicode bidirectional constant LRO.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_LEFT_TO_RIGHT_OVERRIDE = 15;

    /**
     * Unicode bidirectional constant RLE.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING = 16;

    /**
     * Unicode bidirectional constant RLO.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE = 17;

    /**
     * Unicode bidirectional constant PDF.
     * @since 1.4
     */
    public static final byte DIRECTIONALITY_POP_DIRECTIONAL_FORMAT = 18;
    
    /**
     * <p>
     * Minimum value of a high surrogate or leading surrogate unit in UTF-16
     * encoding - <code>'\uD800'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MIN_HIGH_SURROGATE = '\uD800';

    /**
     * <p>
     * Maximum value of a high surrogate or leading surrogate unit in UTF-16
     * encoding - <code>'\uDBFF'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MAX_HIGH_SURROGATE = '\uDBFF';

    /**
     * <p>
     * Minimum value of a low surrogate or trailing surrogate unit in UTF-16
     * encoding - <code>'\uDC00'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MIN_LOW_SURROGATE = '\uDC00';

    /**
     * Maximum value of a low surrogate or trailing surrogate unit in UTF-16
     * encoding - <code>'\uDFFF'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MAX_LOW_SURROGATE = '\uDFFF';

    /**
     * <p>
     * Minimum value of a surrogate unit in UTF-16 encoding - <code>'\uD800'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MIN_SURROGATE = '\uD800';

    /**
     * <p>
     * Maximum value of a surrogate unit in UTF-16 encoding - <code>'\uDFFF'</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final char MAX_SURROGATE = '\uDFFF';

    /**
     * <p>
     * Minimum value of a supplementary code point - <code>U+010000</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 0x10000;

    /**
     * <p>
     * Minimum code point value - <code>U+0000</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final int MIN_CODE_POINT = 0x000000;

    /**
     * <p>
     * Maximum code point value - <code>U+10FFFF</code>.
     * </p>
     * 
     * @since 1.5
     */
    public static final int MAX_CODE_POINT = 0x10FFFF;

    /**
     * <p>
     * Constant for the number of bits to represent a <code>char</code> in
     * two's compliment form.
     * </p>
     * 
     * @since 1.5
     */
    public static final int SIZE = 16;



    /**
     * Constructs a new instance of the receiver which represents the char
     * valued argument.
     * 
     * @param value
     *            the char to store in the new instance.
     */
    public Character(char value) {
        this.value = value;
    }

    /**
     * Answers the char value which the receiver represents.
     * 
     * @return char the value of the receiver
     */
    public char charValue() {
        return value;
    }

    /**
     * Compares the receiver to the specified Character to determine the
     * relative ordering.
     * 
     * @param c
     *            the Character
     * @return an int < 0 if this Character is less than the specified
     *         Character, 0 if they are equal, and > 0 if this Character is
     *         greater
     * @throws NullPointerException
     *             if <code>c</code> is <code>null</code>.
     * @since 1.2
     */
    public int compareTo(Character c) {
        return value - c.value;
    }
    
    /**
     * <p>
     * Returns a <code>Character</code> instance for the <code>char</code>
     * value passed. This method is preferred over the constructor, as this
     * method may maintain a cache of instances.
     * </p>
     * 
     * @param c The char value.
     * @return A <code>Character</code> instance.
     * @since 1.5
     */
    public static Character valueOf(char c) {
        if (c >= CACHE_LEN ) {
            return new Character(c);
        }
        return valueOfCache.CACHE[c];
    }

    private static final int CACHE_LEN = 512;

    static class valueOfCache {
        /*
        * Provides a cache for the 'valueOf' method. A size of 512 should cache the
        * first couple pages of Unicode, which includes the ASCII/Latin-1
        * characters, which other parts of this class are optimized for.
        */
        private static final Character[] CACHE = new Character[CACHE_LEN ];

        static {
            for(int i=0; i<CACHE.length; i++){
                CACHE[i] =  new Character((char)i);
            }
        }
    }
    /**
     * <p>
     * A test for determining if the <code>codePoint</code> is a valid Unicode
     * code point.
     * </p>
     * 
     * @param codePoint The code point to test.
     * @return A boolean value.
     * @since 1.5
     */
    public static boolean isValidCodePoint(int codePoint) {
        return (MIN_CODE_POINT <= codePoint && MAX_CODE_POINT >= codePoint);
    }

    /**
     * <p>
     * A test for determining if the <code>codePoint</code> is within the
     * supplementary code point range.
     * </p>
     * 
     * @param codePoint The code point to test.
     * @return A boolean value.
     * @since 1.5
     */
    public static boolean isSupplementaryCodePoint(int codePoint) {
        return (MIN_SUPPLEMENTARY_CODE_POINT <= codePoint && MAX_CODE_POINT >= codePoint);
    }

    /**
     * <p>
     * A test for determining if the <code>char</code> is a high
     * surrogate/leading surrogate unit that's used for representing
     * supplementary characters in UTF-16 encoding.
     * </p>
     * 
     * @param ch The <code>char</code> unit to test.
     * @return A boolean value.
     * @since 1.5
     * @see #isLowSurrogate(char)
     */
    public static boolean isHighSurrogate(char ch) {
        return (MIN_HIGH_SURROGATE <= ch && MAX_HIGH_SURROGATE >= ch);
    }

    /**
     * <p>
     * A test for determining if the <code>char</code> is a high
     * surrogate/leading surrogate unit that's used for representing
     * supplementary characters in UTF-16 encoding.
     * </p>
     * 
     * @param ch The <code>char</code> unit to test.
     * @return A boolean value.
     * @since 1.5
     * @see #isHighSurrogate(char)
     */
    public static boolean isLowSurrogate(char ch) {
        return (MIN_LOW_SURROGATE <= ch && MAX_LOW_SURROGATE >= ch);
    }

    /**
     * <p>
     * A test for determining if the <code>char</code> pair is a valid
     * surrogate pair.
     * </p>
     * 
     * @param high The high surrogate unit to test.
     * @param low The low surrogate unit to test.
     * @return A boolean value.
     * @since 1.5
     * @see #isHighSurrogate(char)
     * @see #isLowSurrogate(char)
     */
    public static boolean isSurrogatePair(char high, char low) {
        return (isHighSurrogate(high) && isLowSurrogate(low));
    }

    /**
     * <p>
     * Calculates the number of <code>char</code> values required to represent
     * the Unicode code point. This method only tests if the
     * <code>codePoint</code> is greater than or equal to <code>0x10000</code>,
     * in which case <code>2</code> is returned, otherwise <code>1</code>.
     * To test if the code point is valid, use the
     * {@link #isValidCodePoint(int)} method.
     * </p>
     * 
     * @param codePoint The code point to test.
     * @return An <code>int</code> value of 2 or 1.
     * @since 1.5
     * @see #isValidCodePoint(int)
     * @see #isSupplementaryCodePoint(int)
     */
    public static int charCount(int codePoint) {
        return (codePoint >= 0x10000 ? 2 : 1);
    }

    /**
     * <p>
     * Converts a surrogate pair into a Unicode code point. This method assume
     * that the pair are valid surrogates. If the pair are NOT valid surrogates,
     * then the result is indeterminate. The
     * {@link #isSurrogatePair(char, char)} method should be used prior to this
     * method to validate the pair.
     * </p>
     * 
     * @param high The high surrogate unit.
     * @param low The low surrogate unit.
     * @return The decoded code point.
     * @since 1.5
     * @see #isSurrogatePair(char, char)
     */
    public static int toCodePoint(char high, char low) {
        // See RFC 2781, Section 2.2
        // http://www.faqs.org/rfcs/rfc2781.html
        int h = (high & 0x3FF) << 10;
        int l = low & 0x3FF;
        return (h | l) + 0x10000;
    }

    /**
     * <p>
     * Returns the code point at the index in the <code>CharSequence</code>.
     * If <code>char</code> unit at the index is a high-surrogate unit, the
     * next index is less than the length of the sequence and the
     * <code>char</code> unit at the next index is a low surrogate unit, then
     * the code point represented by the pair is returned; otherwise the
     * <code>char</code> unit at the index is returned.
     * </p>
     * 
     * @param seq The sequence of <code>char</code> units.
     * @param index The index into the <code>seq</code> to retrieve and
     *        convert.
     * @return The Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if the <code>index</code> is negative
     *         or greater than or equal to <code>seq.length()</code>.
     * @since 1.5
     */
    public static int codePointAt(CharSequence seq, int index) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length();
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }

        char high = seq.charAt(index++);
        if (index >= len) {
            return high;
        }
        char low = seq.charAt(index);
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return high;
    }

    /**
     * <p>
     * Returns the code point at the index in the <code>char[]</code>. If
     * <code>char</code> unit at the index is a high-surrogate unit, the next
     * index is less than the length of the sequence and the <code>char</code>
     * unit at the next index is a low surrogate unit, then the code point
     * represented by the pair is returned; otherwise the <code>char</code>
     * unit at the index is returned.
     * </p>
     * 
     * @param seq The sequence of <code>char</code> units.
     * @param index The index into the <code>seq</code> to retrieve and
     *        convert.
     * @return The Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if the <code>index</code> is negative
     *         or greater than or equal to <code>seq.length()</code>.
     * @since 1.5
     */
    public static int codePointAt(char[] seq, int index) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length;
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }

        char high = seq[index++];
        if (index >= len) {
            return high;
        }
        char low = seq[index];
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return high;
    }

    /**
     * <p>
     * Returns the code point at the index in the <code>char[]</code> that's
     * within the limit. If <code>char</code> unit at the index is a
     * high-surrogate unit, the next index is less than the <code>limit</code>
     * and the <code>char</code> unit at the next index is a low surrogate
     * unit, then the code point represented by the pair is returned; otherwise
     * the <code>char</code> unit at the index is returned.
     * </p>
     * 
     * @param seq The sequence of <code>char</code> units.
     * @param index The index into the <code>seq</code> to retrieve and
     *        convert.
     * @param limit The exclusive index into the <code>seq</code> that marks
     *        the end of the units that can be used.
     * @return The Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if the <code>index</code> is
     *         negative, greater than or equal to <code>limit</code>,
     *         <code>limit</code> is negative or <code>limit</code> is
     *         greater than the length of <code>seq</code>.
     * @since 1.5
     */
    public static int codePointAt(char[] seq, int index, int limit) {
        if (index < 0 || index >= limit || limit < 0 || limit > seq.length) {
            throw new IndexOutOfBoundsException();
        }       

        char high = seq[index++];
        if (index >= limit) {
            return high;
        }
        char low = seq[index];
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return high;
    }

    /**
     * <p>
     * Returns the Unicode code point that proceeds the <code>index</code> in
     * the <code>CharSequence</code>. If the <code>char</code> unit at
     * <code>index - 1</code> is within the low surrogate range, the value
     * <code>index - 2</code> isn't negative and the <code>char</code> unit
     * at <code>index - 2</code> is within the high surrogate range, then the
     * supplementary code point made up of the surrogate pair is returned;
     * otherwise, the <code>char</code> value at <code>index - 1</code> is
     * returned.
     * </p>
     * 
     * @param seq The <code>CharSequence</code> to search.
     * @param index The index into the <code>seq</code>.
     * @return A Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>index</code> is less than 1
     *         or greater than <code>seq.length()</code>.
     * @since 1.5
     */
    public static int codePointBefore(CharSequence seq, int index) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length();
        if (index < 1 || index > len) {
            throw new IndexOutOfBoundsException();
        }

        char low = seq.charAt(--index);
        if (--index < 0) {
            return low;
        }
        char high = seq.charAt(index);
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return low;
    }

    /**
     * <p>
     * Returns the Unicode code point that proceeds the <code>index</code> in
     * the <code>char[]</code>. If the <code>char</code> unit at
     * <code>index - 1</code> is within the low surrogate range, the value
     * <code>index - 2</code> isn't negative and the <code>char</code> unit
     * at <code>index - 2</code> is within the high surrogate range, then the
     * supplementary code point made up of the surrogate pair is returned;
     * otherwise, the <code>char</code> value at <code>index - 1</code> is
     * returned.
     * </p>
     * 
     * @param seq The <code>char[]</code> to search.
     * @param index The index into the <code>seq</code>.
     * @return A Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>index</code> is less than 1
     *         or greater than <code>seq.length</code>.
     * @since 1.5
     */
    public static int codePointBefore(char[] seq, int index) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length;
        if (index < 1 || index > len) {
            throw new IndexOutOfBoundsException();
        }

        char low = seq[--index];
        if (--index < 0) {
            return low;
        }
        char high = seq[index];
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return low;
    }

    /**
     * <p>
     * Returns the Unicode code point that proceeds the <code>index</code> in
     * the <code>char[]</code> and isn't less than <code>start</code>. If
     * the <code>char</code> unit at <code>index - 1</code> is within the
     * low surrogate range, the value <code>index - 2</code> isn't less than
     * <code>start</code> and the <code>char</code> unit at
     * <code>index - 2</code> is within the high surrogate range, then the
     * supplementary code point made up of the surrogate pair is returned;
     * otherwise, the <code>char</code> value at <code>index - 1</code> is
     * returned.
     * </p>
     * 
     * @param seq The <code>char[]</code> to search.
     * @param index The index into the <code>seq</code>.
     * @return A Unicode code point.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>index</code> is less than or
     *         equal to <code>start</code>, <code>index</code> is greater
     *         than <code>seq.length</code>, <code>start</code> is not
     *         negative and <code>start</code> is greater than
     *         <code>seq.length</code>.
     * @since 1.5
     */
    public static int codePointBefore(char[] seq, int index, int start) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length;
        if (index <= start || index > len || start < 0 || start >= len) {
            throw new IndexOutOfBoundsException();
        }

        char low = seq[--index];
        if (--index < start) {
            return low;
        }
        char high = seq[index];
        if (isSurrogatePair(high, low)) {
            return toCodePoint(high, low);
        }
        return low;
    }

    /**
     * <p>
     * Converts the Unicode code point, <code>codePoint</code>, into a UTF-16
     * encoded sequence and copies the value(s) into the
     * <code>char[]</code> <code>dst</code>, starting at the index
     * <code>dstIndex</code>.
     * </p>
     * 
     * @param codePoint The Unicode code point to encode.
     * @param dst The <code>char[]</code> to copy the encoded value into.
     * @param dstIndex The index to start copying into <code>dst</code>.
     * @return The number of <code>char</code> value units copied into
     *         <code>dst</code>.
     * @throws IllegalArgumentException if <code>codePoint</code> is not a
     *         valid Unicode code point.
     * @throws NullPointerException if <code>dst</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>dstIndex</code> is negative,
     *         greater than or equal to <code>dst.length</code> or equals
     *         <code>dst.length - 1</code> when <code>codePoint</code> is a
     *         {@link #isSupplementaryCodePoint(int) supplementary code point}.
     * @since 1.5
     */
    public static int toChars(int codePoint, char[] dst, int dstIndex) {
        if (!isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException();
        }
        if (dst == null) {
            throw new NullPointerException();
        }
        if (dstIndex < 0 || dstIndex >= dst.length) {
            throw new IndexOutOfBoundsException();
        }

        if (isSupplementaryCodePoint(codePoint)) {
            if (dstIndex == dst.length - 1) {
                throw new IndexOutOfBoundsException();
            }
            // See RFC 2781, Section 2.1
            // http://www.faqs.org/rfcs/rfc2781.html
            int cpPrime = codePoint - 0x10000;
            int high = 0xD800 | ((cpPrime >> 10) & 0x3FF);
            int low = 0xDC00 | (cpPrime & 0x3FF);
            dst[dstIndex] = (char) high;
            dst[dstIndex + 1] = (char) low;
            return 2;
        }

        dst[dstIndex] = (char) codePoint;
        return 1;
    }

    /**
     * <p>
     * Converts the Unicode code point, <code>codePoint</code>, into a UTF-16
     * encoded sequence that is returned as a <code>char[]</code>.
     * </p>
     * 
     * @param codePoint The Unicode code point to encode.
     * @return The UTF-16 encoded <code>char</code> sequence; if code point is
     *         a {@link #isSupplementaryCodePoint(int) supplementary code point},
     *         then a 2 <code>char</code> array is returned, otherwise a 1
     *         <code>char</code> array is returned.
     * @throws IllegalArgumentException if <code>codePoint</code> is not a
     *         valid Unicode code point.
     * @since 1.5
     */
    public static char[] toChars(int codePoint) {
        if (!isValidCodePoint(codePoint)) {
            throw new IllegalArgumentException();
        }

        if (isSupplementaryCodePoint(codePoint)) {
            int cpPrime = codePoint - 0x10000;
            int high = 0xD800 | ((cpPrime >> 10) & 0x3FF);
            int low = 0xDC00 | (cpPrime & 0x3FF);
            return new char[] { (char) high, (char) low };
        }
        return new char[] { (char) codePoint };
    }

    /**
     * <p>
     * Counts the number of Unicode code points in the subsequence of the
     * <code>CharSequence</code>, as delineated by the
     * <code>beginIndex</code> and <code>endIndex</code>. Any surrogate
     * values with missing pair values will be counted as 1 code point.
     * </p>
     * 
     * @param seq The <code>CharSequence</code> to look through.
     * @param beginIndex The inclusive index to begin counting at.
     * @param endIndex The exclusive index to stop counting at.
     * @return The number of Unicode code points.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>beginIndex</code> is
     *         negative, greater than <code>seq.length()</code> or greater
     *         than <code>endIndex</code>.
     * @since 1.5
     */
    public static int codePointCount(CharSequence seq, int beginIndex,
            int endIndex) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length();
        if (beginIndex < 0 || endIndex > len || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }

        int result = 0;
        for (int i = beginIndex; i < endIndex; i++) {
            char c = seq.charAt(i);
            if (isHighSurrogate(c)) {
                if (++i < endIndex) {
                    c = seq.charAt(i);
                    if (!isLowSurrogate(c)) {
                        result++;
                    }
                }
            }
            result++;
        }
        return result;
    }

    /**
     * <p>
     * Counts the number of Unicode code points in the subsequence of the
     * <code>char[]</code>, as delineated by the <code>offset</code> and
     * <code>count</code>. Any surrogate values with missing pair values will
     * be counted as 1 code point.
     * </p>
     * 
     * @param seq The <code>char[]</code> to look through.
     * @param offset The inclusive index to begin counting at.
     * @param count The number of <code>char</code> values to look through in
     *        <code>seq</code>.
     * @return The number of Unicode code points.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>offset</code> or
     *         <code>count</code> is negative or if <code>endIndex</code> is
     *         greater than <code>seq.length</code>.
     * @since 1.5
     */
    public static int codePointCount(char[] seq, int offset, int count) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length;
        int endIndex = offset + count;
        if (offset < 0 || count < 0 || endIndex > len) {
            throw new IndexOutOfBoundsException();
        }

        int result = 0;
        for (int i = offset; i < endIndex; i++) {
            char c = seq[i];
            if (isHighSurrogate(c)) {
                if (++i < endIndex) {
                    c = seq[i];
                    if (!isLowSurrogate(c)) {
                        result++;
                    }
                }
            }
            result++;
        }
        return result;
    }

    /**
     * <p>
     * Determines the index into the <code>CharSequence</code> that is offset
     * (measured in code points and specified by <code>codePointOffset</code>),
     * from the <code>index</code> argument.
     * </p>
     * 
     * @param seq The <code>CharSequence</code> to find the index within.
     * @param index The index to begin from, within the
     *        <code>CharSequence</code>.
     * @param codePointOffset The number of code points to look back or
     *        forwards; may be a negative or positive value.
     * @return The calculated index that is <code>codePointOffset</code> code
     *         points from <code>index</code>.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>index</code> is negative,
     *         greater than <code>seq.length()</code>, there aren't enough
     *         values in <code>seq</code> after <code>index</code> or before
     *         <code>index</code> if <code>codePointOffset</code> is
     *         negative.
     * @since 1.5
     */
    public static int offsetByCodePoints(CharSequence seq, int index,
            int codePointOffset) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int len = seq.length();
        if (index < 0 || index > len) {
            throw new IndexOutOfBoundsException();
        }

        if (codePointOffset == 0) {
            return index;
        }

        if (codePointOffset > 0) {
            int codePoints = codePointOffset;
            int i = index;
            while (codePoints > 0) {
                codePoints--;
                if (i >= len) {
                    throw new IndexOutOfBoundsException();
                }
                if (isHighSurrogate(seq.charAt(i))) {
                    int next = i + 1;
                    if (next < len && isLowSurrogate(seq.charAt(next))) {
                        i++;
                    }
                }
                i++;
            }
            return i;
        }

//        assert codePointOffset < 0;
        int codePoints = -codePointOffset;
        int i = index;
        while (codePoints > 0) {
            codePoints--;
            i--;
            if (i < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (isLowSurrogate(seq.charAt(i))) {
                int prev = i - 1;
                if (prev >= 0 && isHighSurrogate(seq.charAt(prev))) {
                    i--;
                }
            }
        }
        return i;
    }

    /**
     * <p>
     * Determines the index into the <code>char[]</code> that is offset
     * (measured in code points and specified by <code>codePointOffset</code>),
     * from the <code>index</code> argument and is within the subsequence as
     * delineated by <code>start</code> and <code>count</code>.
     * </p>
     * 
     * @param seq The <code>char[]</code> to find the index within.
     * 
     * @param index The index to begin from, within the <code>char[]</code>.
     * @param codePointOffset The number of code points to look back or
     *        forwards; may be a negative or positive value.
     * @param start The inclusive index that marks the beginning of the
     *        subsequence.
     * @param count The number of <code>char</code> values to include within
     *        the subsequence.
     * @return The calculated index that is <code>codePointOffset</code> code
     *         points from <code>index</code>.
     * @throws NullPointerException if <code>seq</code> is <code>null</code>.
     * @throws IndexOutOfBoundsException if <code>start</code> or
     *         <code>count</code> is negative, <code>start + count</code>
     *         greater than <code>seq.length</code>, <code>index</code> is
     *         less than <code>start</code>, <code>index</code> is greater
     *         than <code>start + count</code> or there aren't enough values
     *         in <code>seq</code> after <code>index</code> or before
     *         <code>index</code> if <code>codePointOffset</code> is
     *         negative.
     * @since 1.5
     */
    public static int offsetByCodePoints(char[] seq, int start, int count,
            int index, int codePointOffset) {
        if (seq == null) {
            throw new NullPointerException();
        }
        int end = start + count;
        if (start < 0 || count < 0 || end > seq.length || index < start
                || index > end) {
            throw new IndexOutOfBoundsException();
        }

        if (codePointOffset == 0) {
            return index;
        }

        if (codePointOffset > 0) {
            int codePoints = codePointOffset;
            int i = index;
            while (codePoints > 0) {
                codePoints--;
                if (i >= end) {
                    throw new IndexOutOfBoundsException();
                }
                if (isHighSurrogate(seq[i])) {
                    int next = i + 1;
                    if (next < end && isLowSurrogate(seq[next])) {
                        i++;
                    }
                }
                i++;
            }
            return i;
        }

//        assert codePointOffset < 0;
        int codePoints = -codePointOffset;
        int i = index;
        while (codePoints > 0) {
            codePoints--;
            i--;
            if (i < start) {
                throw new IndexOutOfBoundsException();
            }
            if (isLowSurrogate(seq[i])) {
                int prev = i - 1;
                if (prev >= start && isHighSurrogate(seq[prev])) {
                    i--;
                }
            }
        }
        return i;
    }

    /**
     * Compares the argument to the receiver, and answers true if they represent
     * the <em>same</em> object using a class specific comparison.
     * <p>
     * In this case, the argument must also be a Character, and the receiver and
     * argument must represent the same char value.
     * 
     * @param object
     *            the object to compare with this object
     * @return <code>true</code> if the object is the same as this object
     *         <code>false</code> if it is different from this object
     * 
     * @see #hashCode
     */
    public boolean equals(Object object) {
        return (object instanceof Character)
                && (value == ((Character) object).value);
    }

    /**
     * Answers the character which represents the value in the specified radix.
     * The radix must be between MIN_RADIX and MAX_RADIX inclusive.
     * 
     * @param digit
     *            the integer value
     * @param radix
     *            the radix
     * @return the character which represents the value in the radix
     */
    public static char forDigit(int digit, int radix) {
        if (MIN_RADIX <= radix && radix <= MAX_RADIX) {
            if (0 <= digit && digit < radix) {
                return (char) (digit < 10 ? digit + '0' : digit + 'a' - 10);
            }
        }
        return 0;
    }

    /**
     * Answers an integer hash code for the receiver. Any two objects which
     * answer <code>true</code> when passed to <code>equals</code> must
     * answer the same value for this method.
     * 
     * @return the receiver's hash
     * 
     * @see #equals
     */
    public int hashCode() {
        return value;
    }

    /**
     * Answers whether the character is a Unicode space character. A member of
     * one of the Unicode categories Space Separator, Line Separator, or
     * Paragraph Separator.
     * 
     * @param c
     *            the character
     * @return true when the character is a Unicode space character, false
     *         otherwise
     */
    public static boolean isSpaceChar(char c) {
        if (c == 0x20 || c == 0xa0 || c == 0x1680) {
            return true;
        }
        if (c < 0x2000) {
            return false;
        }
        return c <= 0x200b || c == 0x2028 || c == 0x2029 || c == 0x202f
                || c == 0x3000;
    }

    /**
     * Answers whether the character is a titlecase character.
     * 
     * @param c
     *            the character
     * @return true when the character is a titlecase character, false
     *         otherwise
     */
    public static boolean isTitleCase(char c) {
        if (c == '\u01c5' || c == '\u01c8' || c == '\u01cb' || c == '\u01f2') {
            return true;
        }
        if (c >= '\u1f88' && c <= '\u1ffc') {
            // 0x1f88 - 0x1f8f, 0x1f98 - 0x1f9f, 0x1fa8 - 0x1faf
            if (c > '\u1faf') {
                return c == '\u1fbc' || c == '\u1fcc' || c == '\u1ffc';
            }
            int last = c & 0xf;
            return last >= 8 && last <= 0xf;
        }
        return false;
    }

    /**
     * Reverse the order of the first and second bytes in character
     * @param c
     *            the character
     * @return    the character with reordered bytes.
     */
    public static char reverseBytes(char c) {
        return (char)((c<<8) | (c>>8));
    }

    /**
     * Answers a string containing a concise, human-readable description of the
     * receiver.
     * 
     * @return a printable representation for the receiver.
     */
    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Converts the specified character to its string representation.
     * 
     * @param value
     *            the character
     * @return the character converted to a string
     */
    public static String toString(char value) {
        return String.valueOf(value);
    }
}
