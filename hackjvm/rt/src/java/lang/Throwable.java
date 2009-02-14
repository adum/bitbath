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
 * This class must be implemented by the VM vendor, or the reference
 * implementation can be used if the documented natives are implemented.
 * 
 * This class is the superclass of all classes which can be thrown by the
 * virtual machine. The two direct subclasses represent recoverable exceptions
 * (Exception) and unrecoverable errors (Error). This class provides common
 * methods for accessing a string message which provides extra information about
 * the circumstances in which the throwable was created, and for filling in a
 * walkback (i.e. a record of the call stack at a particular point in time)
 * which can be printed later.
 * 
 * @see Error
 * @see Exception
 * @see RuntimeException
 */
public class Throwable {
    private static final long serialVersionUID = -3042686055658047285L;

    /**
     * The message provided when the exception was created.
     */
    private String detailMessage;

    /**
     * The cause of this Throwable. Null when there is no cause.
     */
    private Throwable cause = this;

    /**
     * Constructs a new instance of this class with its walkback filled in.
     */
    public Throwable() {
        super();
        fillInStackTrace();
    }

    /**
     * Constructs a new instance of this class with its walkback and message
     * filled in.
     * 
     * @param detailMessage String The detail message for the exception.
     */
    public Throwable(String detailMessage) {
        this();
        this.detailMessage = detailMessage;
    }

    /**
     * Constructs a new instance of this class with its walkback, message and
     * cause filled in.
     * 
     * @param detailMessage String The detail message for the exception.
     * @param throwable The cause of this Throwable
     */
    public Throwable(String detailMessage, Throwable throwable) {
        this();
        this.detailMessage = detailMessage;
        cause = throwable;
    }

    /**
     * Constructs a new instance of this class with its walkback and cause
     * filled in.
     * 
     * @param throwable The cause of this Throwable
     */
    public Throwable(Throwable throwable) {
        this();
        this.detailMessage = throwable == null ? null : throwable.toString();
        cause = throwable;
    }

    /**
     * This native must be implemented to use the reference implementation of
     * this class.
     * 
     * Record in the receiver a walkback from the point where this message was
     * sent. The message is public so that code which catches a throwable and
     * then <em>re-throws</em> it can adjust the walkback to represent the
     * location where the exception was re-thrown.
     * 
     * @return the receiver
     */
//    public native Throwable fillInStackTrace();
    public Throwable fillInStackTrace() { return this; }

    /**
     * Answers the extra information message which was provided when the
     * throwable was created. If no message was provided at creation time, then
     * answer null.
     * 
     * @return String The receiver's message.
     */
    public String getMessage() {
        return detailMessage;
    }

    /**
     * Answers the extra information message which was provided when the
     * throwable was created. If no message was provided at creation time, then
     * answer null. Subclasses may override this method to answer localized text
     * for the message.
     * 
     * @return String The receiver's message.
     */
    public String getLocalizedMessage() {
        return getMessage();
    }

    /**
     * Outputs a printable representation of the receiver's walkback on the
     * System.err stream.
     */
    public void printStackTrace() {
//        printStackTrace(System.err);
    }

    /**
     * Answers a string containing a concise, human-readable description of the
     * receiver.
     * 
     * @return String a printable representation for the receiver.
     */
    public String toString() {
//        String msg = getLocalizedMessage();
//        String name = getClass().getName();
//        if (msg == null) {
//            return name;
//        }
//        return new StringBuffer(name.length() + 2 + msg.length()).append(name).append(": ")
//                .append(msg).toString();
        return "err";
    }

    /**
     * Initialize the cause of the receiver. The cause cannot be reassigned.
     * 
     * @param throwable The cause of this Throwable
     * 
     * @exception IllegalArgumentException when the cause is the receiver
     * @exception IllegalStateException when the cause has already been
     *            initialized
     * 
     * @return the receiver.
     */
    public synchronized Throwable initCause(Throwable throwable) {
        if (cause == this) {
            if (throwable != this) {
                cause = throwable;
                return this;
            }
            throw new IllegalArgumentException("Cause cannot be the receiver");
        }
        throw new IllegalStateException("Cause already initialized");
    }

    /**
     * Answers the cause of this Throwable, or null if there is no cause.
     * 
     * @return Throwable The receiver's cause.
     */
    public Throwable getCause() {
        if (cause == this) {
            return null;
        }
        return cause;
    }
}
