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
 * This class must be implemented by the vm vendor. Object is the root of the
 * java class hierarchy. All non-base types respond to the messages defined in
 * this class.
 * 
 */
public class Object {

    /**
     * Constructs a new instance of this class.
     * 
     */
    public Object() {
    }

    /**
     * Compares the argument to the receiver, and answers true if they represent
     * the <em>same</em> object using a class specific comparison. The
     * implementation in Object answers true only if the argument is the exact
     * same object as the receiver (==).
     * 
     * @param o Object the object to compare with this object.
     * @return boolean <code>true</code> if the object is the same as this
     *         object <code>false</code> if it is different from this object.
     * @see #hashCode
     */
    public boolean equals(Object o) {
        return false;
    }

    /**
     * Called by the virtual machine when there are no longer any (non-weak)
     * references to the receiver. Subclasses can use this facility to guarantee
     * that any associated resources are cleaned up before the receiver is
     * garbage collected. Uncaught exceptions which are thrown during the
     * running of the method cause it to terminate immediately, but are
     * otherwise ignored.
     * <p>
     * Note: The virtual machine assumes that the implementation in class Object
     * is empty.
     * 
     * @throws Throwable The virtual machine ignores any exceptions which are
     *         thrown during finalization.
     */
    public final Class getClass() {
        return null;
    }

    /**
     * Answers an integer hash code for the receiver. Any two objects which
     * answer <code>true</code> when passed to <code>.equals</code> must
     * answer the same value for this method.
     * 
     * @return int the receiver's hash.
     * @see #equals
     */
    public int hashCode() {
        return 0;
    }

    /**
     * Causes one thread which is <code>wait</code> ing on the receiver to be
     * made ready to run. This does not guarantee that the thread will
     * immediately run. The method can only be invoked by a thread which owns
     * the receiver's monitor.
     * 
     * @see #notifyAll
     * @see #wait()
     * @see #wait(long)
     * @see #wait(long,int)
     * @see java.lang.Thread
     */
    public final void notify() {
        return;
    }

    /**
     * Causes all threads which are <code>wait</code> ing on the receiver to
     * be made ready to run. The threads are scheduled according to their
     * priorities as specified in class Thread. Between any two threads of the
     * same priority the one which waited first will be the first thread that
     * runs after being notified. The method can only be invoked by a thread
     * which owns the receiver's monitor.
     * 
     * @see #notify
     * @see #wait()
     * @see #wait(long)
     * @see #wait(long,int)
     * @see java.lang.Thread
     */
    public final void notifyAll() {
        return;
    }

    /**
     * Answers a string containing a concise, human-readable description of the
     * receiver.
     * 
     * @return String a printable representation for the receiver.
     */
    public String toString() {
        return null;
    }
}
