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

/**
 * This class must be implemented by the VM vendor. The documented natives must
 * be implemented to support other provided class implementations in this
 * package. An instance of class Class is the in-image representation of a Java
 * class. There are three basic types of Classes
 * <dl>
 * <dt><em>Classes representing object types (classes or interfaces)</em>
 * </dt>
 * <dd>These are Classes which represent the class of a simple instance as
 * found in the class hierarchy. The name of one of these Classes is simply the
 * fully qualified class name of the class or interface that it represents. Its
 * <em>signature</em> is the letter "L", followed by its name, followed by a
 * semi-colon (";").</dd>
 * <dt><em>Classes representing base types</em></dt>
 * <dd>These Classes represent the standard Java base types. Although it is not
 * possible to create new instances of these Classes, they are still useful for
 * providing reflection information, and as the component type of array classes.
 * There is one of these Classes for each base type, and their signatures are:
 * <ul>
 * <li><code>B</code> representing the <code>byte</code> base type</li>
 * <li><code>S</code> representing the <code>short</code> base type</li>
 * <li><code>I</code> representing the <code>int</code> base type</li>
 * <li><code>J</code> representing the <code>long</code> base type</li>
 * <li><code>F</code> representing the <code>float</code> base type</li>
 * <li><code>D</code> representing the <code>double</code> base type</li>
 * <li><code>C</code> representing the <code>char</code> base type</li>
 * <li><code>Z</code> representing the <code>boolean</code> base type</li>
 * <li><code>V</code> representing void function return values</li>
 * </ul>
 * The name of a Class representing a base type is the keyword which is used to
 * represent the type in Java source code (i.e. "int" for the <code>int</code>
 * base type.</dd>
 * <dt><em>Classes representing array classes</em></dt>
 * <dd>These are Classes which represent the classes of Java arrays. There is
 * one such Class for all array instances of a given arity (number of
 * dimensions) and leaf component type. In this case, the name of the class is
 * one or more left square brackets (one per dimension in the array) followed by
 * the signature ofP the class representing the leaf component type, which can
 * be either an object type or a base type. The signature of a Class
 * representing an array type is the same as its name.</dd>
 * </dl>
 * 
 * @since 1.0
 */
public final class Class {

    private static final long serialVersionUID = 3206093459760846163L;
    
    private Class(){
        //prevent this class to be instantiated, instance should be created by JVM only
    }

    /**
     * Verify the specified Class using the VM byte code verifier.
     * 
     * @throws VerifyError if the Class cannot be verified
     */
    void verify() {
        return;
    }

    /**
     * Answers the canonical name of the receiver. If the receiver does not have
     * a canonical name, as defined in the Java Language Specification, then the
     * method returns <code>null</code>.
     * 
     * @return the receiver canonical name, or <code>null</code>.
     */
    public String getCanonicalName() {
        return null;
    }

    /**
     * Answers the name of the class which the receiver represents. For a
     * description of the format which is used, see the class definition of
     * {@link Class}.
     * 
     * @return the receiver's name.
     * @see {@link Class}
     */
    public String getName() {
        return null;
    }

    /**
     * Answers a string containing a concise, human-readable description of the
     * receiver.
     * 
     * @return a printable representation for the receiver.
     */
    public String toString() {
        return null;
    }

}
