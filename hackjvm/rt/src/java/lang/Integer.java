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

/**
 * <p>
 * Integer is the wrapper for the primitive type <code>int</code>.
 * </p>
 * 
 * <p>
 * As with the specification, this implementation relied on code laid out in <a
 * href="http://www.hackersdelight.org/">Henry S. Warren, Jr.'s Hacker's
 * Delight, (Addison Wesley, 2002)</a> as well as <a
 * href="http://aggregate.org/MAGIC/">The Aggregate's Magic Algorithms</a>.
 * </p>
 * 
 * @see java.lang.Number
 * @since 1.1
 */
public final class Integer extends Number /*implements Comparable<Integer>*/ {

    private static final long serialVersionUID = 1360826667806852920L;

    /**
     * The value which the receiver represents.
     */
    private final int value;

    /**
     * <p>
     * Constant for the maximum <code>int</code> value, 2<sup>31</sup>-1.
     * </p>
     */
    public static final int MAX_VALUE = 0x7FFFFFFF;

    /**
     * <p>
     * Constant for the minimum <code>int</code> value, -2<sup>31</sup>.
     * </p>
     */
    public static final int MIN_VALUE = 0x80000000;

    /**
     * <p>
     * Constant for the number of bits to represent an <code>int</code> in
     * two's compliment form.
     * </p>
     * 
     * @since 1.5
     */
    public static final int SIZE = 32;

//    /**
//     * The java.lang.Class that represents this class.
//     */
//    @SuppressWarnings("unchecked")
//    public static final Class<Integer> TYPE = (Class<Integer>) new int[0]
//            .getClass().getComponentType();

    // Note: This can't be set to "int.class", since *that* is
    // defined to be "java.lang.Integer.TYPE";

    /**
     * Constructs a new instance of the receiver which represents the int valued
     * argument.
     * 
     * @param value
     *            the int to store in the new instance.
     */
    public Integer(int value) {
        this.value = value;
    }

    /**
     * Answers the byte value which the receiver represents
     * 
     * @return byte the value of the receiver.
     */
    public byte byteValue() {
        return (byte) value;
    }

    /**
     * <p>
     * Compares this <code>Integer</code> to the <code>Integer</code>
     * passed. If this instance's value is equal to the value of the instance
     * passed, then 0 is returned. If this instance's value is less than the
     * value of the instance passed, then a negative value is returned. If this
     * instance's value is greater than the value of the instance passed, then a
     * positive value is returned.
     * </p>
     * 
     * @param object
     *            The instance to compare to.
     * @throws NullPointerException
     *             if <code>object</code> is <code>null</code>.
     * @since 1.2
     */
    public int compareTo(Integer object) {
        return value > object.value ? 1 : (value < object.value ? -1 : 0);
    }

    /**
     * Answers the double value which the receiver represents
     * 
     * @return double the value of the receiver.
     */
    public double doubleValue() {
        return value;
    }

    /**
     * Compares the argument to the receiver, and answers true if they represent
     * the <em>same</em> object using a class specific comparison.
     * <p>
     * In this case, the argument must also be an Integer, and the receiver and
     * argument must represent the same int value.
     * 
     * @param o
     *            the object to compare with this object
     * @return <code>true</code> if the object is the same as this object
     *         <code>false</code> if it is different from this object
     * @see #hashCode
     */
    public boolean equals(Object o) {
        return (o instanceof Integer)
                && (value == ((Integer) o).value);
    }

    /**
     * Answers the float value which the receiver represents
     * 
     * @return float the value of the receiver.
     */
    public float floatValue() {
        return value;
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
     * Answers the int value which the receiver represents
     * 
     * @return int the value of the receiver.
     */
    public int intValue() {
        return value;
    }

    /**
     * Answers the long value which the receiver represents
     * 
     * @return long the value of the receiver.
     */
    public long longValue() {
        return value;
    }

    /**
     * Answers the short value which the receiver represents
     * 
     * @return short the value of the receiver.
     */
    public short shortValue() {
        return (short) value;
    }

    /**
     * Answers a string containing '0' and '1' characters which describe the
     * binary representation of the argument.
     * 
     * @param i
     *            an int to get the binary representation of
     * @return String the binary representation of the argument
     */
    public static String toBinaryString(int i) {
        int count = 1, j = i;

        if (i < 0) {
            count = 32;
        } else {
            while ((j >>>= 1) != 0) {
                count++;
            }
        }

        char[] buffer = new char[count];
        do {
            buffer[--count] = (char) ((i & 1) + '0');
            i >>>= 1;
        } while (count > 0);
        return new String(0, buffer.length, buffer);
    }

    /**
     * Answers a string containing characters in the range 0..9, a..f which
     * describe the hexadecimal representation of the argument.
     * 
     * @param i
     *            an int to get the hex representation of
     * @return String the hex representation of the argument
     */
    public static String toHexString(int i) {
        int count = 1, j = i;

        if (i < 0) {
            count = 8;
        } else {
            while ((j >>>= 4) != 0) {
                count++;
            }
        }

        char[] buffer = new char[count];
        do {
            int t = i & 15;
            if (t > 9) {
                t = t - 10 + 'a';
            } else {
                t += '0';
            }
            buffer[--count] = (char) t;
            i >>>= 4;
        } while (count > 0);
        return new String(0, buffer.length, buffer);
    }

    /**
     * Answers a string containing characters in the range 0..7 which describe
     * the octal representation of the argument.
     * 
     * @param i
     *            an int to get the octal representation of
     * @return String the hex representation of the argument
     */
    public static String toOctalString(int i) {
        int count = 1, j = i;

        if (i < 0) {
            count = 11;
        } else {
            while ((j >>>= 3) != 0) {
                count++;
            }
        }

        char[] buffer = new char[count];
        do {
            buffer[--count] = (char) ((i & 7) + '0');
            i >>>= 3;
        } while (count > 0);
        return new String(0, buffer.length, buffer);
    }

    /**
     * Answers a string containing a concise, human-readable description of the
     * receiver.
     * 
     * @return a printable representation for the receiver.
     */
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Answers a string containing characters in the range 0..9 which describe
     * the decimal representation of the argument.
     * 
     * @param i
     *            an int to get the representation of
     * @return String the representation of the argument
     */
    public static String toString(int i) {
        return toString(i, 10);
    }

    /**
     * Answers a string containing characters in the range 0..9, a..z (depending
     * on the radix) which describe the representation of the argument in that
     * radix.
     * 
     * @param i
     *            an int to get the representation of
     * @param radix
     *            the base to use for conversion.
     * @return String the representation of the argument
     */
    public static String toString(int i, int radix) {
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            radix = 10;
        }
        if (i == 0) {
            return "0"; //$NON-NLS-1$
        }

        int count = 2, j = i;
        boolean negative = i < 0;
        if (!negative) {
            count = 1;
            j = -i;
        }
        while ((i /= radix) != 0) {
            count++;
        }

        char[] buffer = new char[count];
        do {
            int ch = 0 - (j % radix);
            if (ch > 9) {
                ch = ch - 10 + 'a';
            } else {
                ch += '0';
            }
            buffer[--count] = (char) ch;
        } while ((j /= radix) != 0);
        if (negative) {
            buffer[0] = '-';
        }
        return new String(0, buffer.length, buffer);
    }

    /**
     * <p>
     * Determines the highest (leftmost) bit that is 1 and returns the value
     * that is the bit mask for that bit. This is sometimes referred to as the
     * Most Significant 1 Bit.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to interrogate.
     * @return The bit mask indicating the highest 1 bit.
     * @since 1.5
     */
    public static int highestOneBit(int i) {
        i |= (i >> 1);
        i |= (i >> 2);
        i |= (i >> 4);
        i |= (i >> 8);
        i |= (i >> 16);
        return (i & ~(i >>> 1));
    }

    /**
     * <p>
     * Determines the lowest (rightmost) bit that is 1 and returns the value
     * that is the bit mask for that bit. This is sometimes referred to as the
     * Least Significant 1 Bit.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to interrogate.
     * @return The bit mask indicating the lowest 1 bit.
     * @since 1.5
     */
    public static int lowestOneBit(int i) {
        return (i & (-i));
    }

    /**
     * <p>
     * Determines the number of leading zeros in the <code>int</code> passed
     * prior to the {@link #highestOneBit(int) highest one bit}.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to process.
     * @return The number of leading zeros.
     * @since 1.5
     */
    public static int numberOfLeadingZeros(int i) {
        i |= i >> 1;
        i |= i >> 2;
        i |= i >> 4;
        i |= i >> 8;
        i |= i >> 16;
        return bitCount(~i);
    }

    /**
     * <p>
     * Determines the number of trailing zeros in the <code>int</code> passed
     * after the {@link #lowestOneBit(int) lowest one bit}.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to process.
     * @return The number of trailing zeros.
     * @since 1.5
     */
    public static int numberOfTrailingZeros(int i) {
        return bitCount((i & -i) - 1);
    }

    /**
     * <p>
     * Counts the number of 1 bits in the <code>int</code> value passed; this
     * is sometimes referred to as a population count.
     * </p>
     * 
     * @param i
     *            The <code>int</code> value to process.
     * @return The number of 1 bits.
     * @since 1.5
     */
    public static int bitCount(int i) {
        i -= ((i >> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >> 2) & 0x33333333);
        i = (((i >> 4) + i) & 0x0F0F0F0F);
        i += (i >> 8);
        i += (i >> 16);
        return (i & 0x0000003F);
    }

    /**
     * <p>
     * Rotates the bits of <code>i</code> to the left by the
     * <code>distance</code> bits.
     * </p>
     * 
     * @param i
     *            The <code>int</code> value to rotate left.
     * @param distance
     *            The number of bits to rotate.
     * @return The rotated value.
     * @since 1.5
     */
    public static int rotateLeft(int i, int distance) {
        if (distance == 0) {
            return i;
        }
        /*
         * According to JLS3, 15.19, the right operand of a shift is always
         * implicitly masked with 0x1F, which the negation of 'distance' is
         * taking advantage of.
         */
        return ((i << distance) | (i >>> (-distance)));
    }

    /**
     * <p>
     * Rotates the bits of <code>i</code> to the right by the
     * <code>distance</code> bits.
     * </p>
     * 
     * @param i
     *            The <code>int</code> value to rotate right.
     * @param distance
     *            The number of bits to rotate.
     * @return The rotated value.
     * @since 1.5
     */
    public static int rotateRight(int i, int distance) {
        if (distance == 0) {
            return i;
        }
        /*
         * According to JLS3, 15.19, the right operand of a shift is always
         * implicitly masked with 0x1F, which the negation of 'distance' is
         * taking advantage of.
         */
        return ((i >>> distance) | (i << (-distance)));
    }

    /**
     * <p>
     * Reverses the bytes of a <code>int</code>.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to reverse.
     * @return The reversed value.
     * @since 1.5
     */
    public static int reverseBytes(int i) {
        int b3 = i >>> 24;
        int b2 = (i >>> 8) & 0xFF00;
        int b1 = (i & 0xFF00) << 8;
        int b0 = i << 24;
        return (b0 | b1 | b2 | b3);
    }

    /**
     * <p>
     * Reverses the bytes of a <code>int</code>.
     * </p>
     * 
     * @param i
     *            The <code>int</code> to reverse.
     * @return The reversed value.
     * @since 1.5
     */
    public static int reverse(int i) {
        // From Hacker's Delight, 7-1, Figure 7-1
        i = (i & 0x55555555) << 1 | (i >> 1) & 0x55555555;
        i = (i & 0x33333333) << 2 | (i >> 2) & 0x33333333;
        i = (i & 0x0F0F0F0F) << 4 | (i >> 4) & 0x0F0F0F0F;
        return reverseBytes(i);
    }

    /**
     * <p>
     * The <code>signum</code> function for <code>int</code> values. This
     * method returns -1 for negative values, 1 for positive values and 0 for
     * the value 0.
     * </p>
     * 
     * @param i
     *            The <code>int</code> value.
     * @return -1 if negative, 1 if positive otherwise 0.
     * @since 1.5
     */
    public static int signum(int i) {
        return (i == 0 ? 0 : (i < 0 ? -1 : 1));
    }

    /**
     * <p>
     * Returns a <code>Integer</code> instance for the <code>int</code>
     * value passed. This method is preferred over the constructor, as this
     * method may maintain a cache of instances.
     * </p>
     * 
     * @param i
     *            The int value.
     * @return A <code>Integer</code> instance.
     * @since 1.5
     */
    public static Integer valueOf(int i) {
        if (i < -128 || i > 127) {
            return new Integer(i);
        }
        return valueOfCache.CACHE [i+128];

    }

   static class valueOfCache {
        /**
         * <p>
         * A cache of instances used by {@link Integer#valueOf(int)} and auto-boxing.
         * </p>
         */
        static final Integer[] CACHE = new Integer[256];

        static {
            for(int i=-128; i<=127; i++) {
                CACHE[i+128] = new Integer(i);
            }
        }
    }
}
