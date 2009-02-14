/* 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package java.lang;

import java.io.PrintStream;

/**
 * Class System provides a standard place for programs to find system related
 * information. All System API is static.
 * 
 */
public final class System {

    // The standard input, output, and error streams.
    // Typically, these are connected to the shell which
    // ran the Java program.
    /**
     * Default output stream
     */
    public static final PrintStream out = new PrintStream();
//
//    /**
//     * Default error output stream
//     */
//    public static final PrintStream err;
//
//    // Indicates whether the classes needed for
//    // permission checks was initialized or not
//    private static boolean security_initialized;
//
//    // Initialize all the slots in System on first use.
//    static {
//        // Fill in the properties from the VM information.
//        // Set up standard in, out, and err.
//        err = new String.ConsolePrintStream(new BufferedOutputStream(new FileOutputStream(
//                FileDescriptor.err)));
//        out = new String.ConsolePrintStream(new BufferedOutputStream(new FileOutputStream(
//                FileDescriptor.out)));
//    }

    /**
     * Prevents this class from being instantiated.
     */
    private System() {
    }

    public static native void arraycopy(Object array1, int start1, Object array2, int start2,
            int length);
    
    /**
     * Answers the current time expressed as milliseconds since the time
     * 00:00:00 UTC on January 1, 1970.
     * 
     * @return the time in milliseconds.
     */
    public static native long currentTimeMillis();

    /**
     * Indicate to the virtual machine that it would be a good time to collect
     * available memory. Note that, this is a hint only.
     */
    public static void gc() {
    }

//    public static void initializeSystemClass() {
//    }

}
