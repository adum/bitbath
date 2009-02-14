package ojvm.operations;

import java.util.logging.Logger;

import ojvm.data.*;
import ojvm.machine.*;
import ojvm.util.*;
import ojvm.loading.instructions.*;

/**
 * 
 * One method for each instruction. 
 * 
 * File created June 16, 2000
 * @author Amr Sabry
 *  */

public class BytecodeInterpreter implements InstructionVisitor {
    private static Logger logger = Logger.getLogger(BytecodeInterpreter.class.getCanonicalName());

    private ControlUnit cu;

  public BytecodeInterpreter (ControlUnit cu) { 
      this.cu = cu;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_aaload (Ins_aaload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to aaload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.isReference()) {
                  JavaValue value = ja.get(index);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type reference");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) {
          cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage());
          }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_aastore (Ins_aastore instruction) throws JavaException {
      try {
          JavaReferenceValue value = cu.popOperandStack().toReference();
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to aastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.isReference()) {
                  if (value.isAssignmentCompatible(componentClass)) ja.store(index,value);
                  else cu.makeAndThrow("java.lang.ArrayStoreException", "Types not compatible");
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type reference");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_aconst_null (Ins_aconst_null instruction) throws JavaException {
      try {
          cu.pushOperandStack(JavaNullReferenceValue.nullRef); 
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // also the four instructions aload_<n>
  public void visit_aload (Ins_aload instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          JavaReferenceValue objectref = v.toReference();
          cu.pushOperandStack(objectref);
      }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_areturn (Ins_areturn instruction) throws JavaException, ThreadHaltE {
      JavaReferenceValue objectRef = null;
      try {

          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          objectRef = cu.popOperandStack().toReference();
          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          InternalClass returnClass = cu.findClass(returnType);
          if (objectRef.isAssignmentCompatible(returnClass)) {
              cu.popFrame();
              cu.pushOperandStack(objectRef); // of the caller              
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Attempting to return a reference when none expected");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
//      catch (VMStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
      catch (VMStackE e) {
          // cu.makeAndThrow("java.lang.VerifyError", e.getMessage());
          try {
              cu.pushOperandStack(objectRef);
          }
          catch (OperandStackE e1) {
              e1.printStackTrace();
          }
          // of the caller
          throw new ThreadHaltE("");
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_arraylength (Ins_arraylength instruction) throws JavaException {
      try {
          JavaReferenceValue arrayRef = cu.popOperandStack().toReference();
          if (arrayRef.isNull()) cu.makeAndThrow("java.lang.NullPointerException", "Null reference to arraylength");
          JavaArray ja = arrayRef.toNonnullReference().toArray();
          int length = ja.arraylength();
          cu.pushOperandStack(new JavaIntValue(length));
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // also instructions astore_<n>
  public void visit_astore (Ins_astore instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.popOperandStack();
          if (v instanceof JavaReferenceValue || v instanceof ReturnAddress) {
              cu.putLocalVar(index,v);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bad argument " + v + " to astore");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_athrow (Ins_athrow instruction) throws JavaException {
      try {
          JavaReferenceValue objectref = cu.popOperandStack().toReference();
          if (objectref.isNull()) cu.makeAndThrow("java.lang.NullPointerException", "Null reference to athrow");
          InternalClass throwableClass = cu.findClass(CommonDescriptors.javaLangThrowableDesc);
          if (objectref.isAssignmentCompatible(throwableClass)) {
              throw new JavaException(objectref.toNonnullReference());
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Argument to throw not throwable");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_baload (Ins_baload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to baload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              JavaValue value = ja.get(index);
              if (value instanceof JavaByteValue) {
                  byte b = value.toByte(false).getValue();
                  int ib = b;
                  cu.pushOperandStack(new JavaIntValue(ib));
              }
              else if (value instanceof JavaBooleanValue) {
                  boolean z = ((JavaBooleanValue)value).getValue();
                  int iz = z ? 1 : 0;
                  cu.pushOperandStack(new JavaIntValue(iz));
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type byte or boolean");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_bastore (Ins_bastore instruction) throws JavaException {
      try {
          int value = cu.popOperandStack().toInt(false).getValue();
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to bastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              Descriptor componentDesc = ja.getComponentClass().getDesc();
              if (componentDesc.equals(CommonDescriptors.byteDesc)) {
                  byte b = (byte)value; 
                  ja.store(index, new JavaByteValue(b));
              }
              else if (componentDesc.equals(CommonDescriptors.booleanDesc)) {
                  boolean z = value==0 ? false : true;
//                  cu.pushOperandStack(new JavaBooleanValue(z)); //AM: nothing gets pushed onto the stack in bastore
                  ja.store(index, new JavaBooleanValue(z));
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type byte or boolean");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_bipush (Ins_bipush instruction) throws JavaException {
      try {
          JavaIntValue v = new JavaIntValue(instruction.getValue());
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_caload (Ins_caload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to caload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.charDesc)) {
                  char c = ja.get(index).toChar(false).getValue();
                  JavaValue value = new JavaIntValue((int)c);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type char");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_castore (Ins_castore instruction) throws JavaException {
      try {
          int value = cu.popOperandStack().toInt(false).getValue();
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to castore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.charDesc)) {
                  ja.store(index, new JavaCharValue((char)value));
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type char");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_checkcast (Ins_checkcast instruction) throws JavaException {
      try {
          JavaReferenceValue objectref = cu.popOperandStack().toReference();
          Descriptor targetClassDesc = instruction.getClassDesc();
          InternalClass targetClass = cu.findClass(targetClassDesc);
          if (objectref.isAssignmentCompatible(targetClass)) cu.pushOperandStack(objectref);
          else cu.makeAndThrow("java.lang.ClassCastException", "In instruction checkcast");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_d2f (Ins_d2f instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v2 = PrimitiveOperations.d2f(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_d2i (Ins_d2i instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v2 = PrimitiveOperations.d2i(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_d2l (Ins_d2l instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v2 = PrimitiveOperations.d2l(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dadd (Ins_dadd instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v = PrimitiveOperations.dadd(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_daload (Ins_daload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to daload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.doubleDesc)) {
                  JavaDoubleValue value = ja.get(index).toDouble(false);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type double");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dastore (Ins_dastore instruction) throws JavaException {
      try {
          JavaDoubleValue value = cu.popOperandStack().toDouble(false);
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to dastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.doubleDesc)) {
                  ja.store(index,value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type double");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dcmpg (Ins_dcmpg instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.dcmpg(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dcmpl (Ins_dcmpl instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.dcmpl(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dconst_0 (Ins_dconst_0 instruction) throws JavaException {
      try {
          JavaValue v = new JavaDoubleValue(0.0);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dconst_1 (Ins_dconst_1 instruction) throws JavaException {
      try {
          JavaValue v = new JavaDoubleValue(1.0);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_ddiv (Ins_ddiv instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v = PrimitiveOperations.ddiv(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dload (Ins_dload instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          v.toDouble(false); // check v is a double without any conversions
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dmul (Ins_dmul instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v = PrimitiveOperations.dmul(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dneg (Ins_dneg instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v2 = PrimitiveOperations.dneg(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_drem (Ins_drem instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v = PrimitiveOperations.drem(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dreturn (Ins_dreturn instruction) throws JavaException {
      try {

          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          JavaDoubleValue value = cu.popOperandStack().toDouble(false);
          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          if (returnType.equals(CommonDescriptors.doubleDesc)) {
              cu.popFrame();
              cu.pushOperandStack(value); // of the caller              
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Incompatible return type");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (VMStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
  public void visit_dstore (Ins_dstore instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.popOperandStack();
          v.toDouble(false);
          cu.putLocalVar(index,v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
    
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dsub (Ins_dsub instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v = PrimitiveOperations.dsub(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup (Ins_dup instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (v.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup");
          cu.pushOperandStack(v);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup2 (Ins_dup2 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          if (v1.getSize() == 1) {
              JavaValue v2 = cu.popOperandStack();
              if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup2");
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else if (v1.getSize() == 2) {
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v1);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bad dup2");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup2_x1 (Ins_dup2_x1 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          if (v1.getSize() == 1) {
              JavaValue v2 = cu.popOperandStack();
              JavaValue v3 = cu.popOperandStack();
              if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x1");
              if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x1");
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v3);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else if (v1.getSize() == 2) {
              JavaValue v2 = cu.popOperandStack();
              if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x1");
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x1");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup2_x2 (Ins_dup2_x2 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaValue v2 = cu.popOperandStack();
          if (v1.getSize() == 1 && v2.getSize() == 1) { // Forms 1 and 3
              JavaValue v3 = cu.popOperandStack();
              if (v3.getSize() == 1) { // Form 1
                  JavaValue v4 = cu.popOperandStack();
                  if (v4.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x2");
                  cu.pushOperandStack(v2);
                  cu.pushOperandStack(v1);
                  cu.pushOperandStack(v4);
                  cu.pushOperandStack(v3);
                  cu.pushOperandStack(v2);
                  cu.pushOperandStack(v1);
              }
              else if (v3.getSize() == 2) { // Form 3
                  cu.pushOperandStack(v2);
                  cu.pushOperandStack(v1);
                  cu.pushOperandStack(v3);
                  cu.pushOperandStack(v2);
                  cu.pushOperandStack(v1);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x2"); 
          }
          else if (v1.getSize() == 2 && v2.getSize() == 1) { // Form 2
              JavaValue v3 = cu.popOperandStack();
              if (v3.getSize() != 1)  cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x2"); 
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v3);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else if (v1.getSize() == 2 && v2.getSize() == 2) { // Form 4
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bad dup2_x2"); 
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup_x1 (Ins_dup_x1 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaValue v2 = cu.popOperandStack();
          if (v1.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup_x1"); 
          if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup_x1"); 
          cu.pushOperandStack(v1);
          cu.pushOperandStack(v2);
          cu.pushOperandStack(v1);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_dup_x2 (Ins_dup_x2 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaValue v2 = cu.popOperandStack();
          if (v1.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup_x2"); 
          if (v2.getSize() == 1) {
              JavaValue v3 = cu.popOperandStack();
              if (v3.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Bad dup_x2"); 
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v3);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else if (v2.getSize() == 2) {
              cu.pushOperandStack(v1);
              cu.pushOperandStack(v2);
              cu.pushOperandStack(v1);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bad dup_x2"); 
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_f2d (Ins_f2d instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v2 = PrimitiveOperations.f2d(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_f2i (Ins_f2i instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v2 = PrimitiveOperations.f2i(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_f2l (Ins_f2l instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v2 = PrimitiveOperations.f2l(v1);
          cu.pushOperandStack(v2);
      }
       catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
       catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
 }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fadd (Ins_fadd instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v = PrimitiveOperations.fadd(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_faload (Ins_faload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to faload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.floatDesc)) {
                  JavaFloatValue value = ja.get(index).toFloat(false);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type float");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fastore (Ins_fastore instruction) throws JavaException {
      try {
          JavaFloatValue value = cu.popOperandStack().toFloat(false);
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to fastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.floatDesc)) {
                  ja.store(index,value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type float");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fcmpg (Ins_fcmpg instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.fcmpg(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fcmpl (Ins_fcmpl instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.fcmpl(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fconst_0 (Ins_fconst_0 instruction) throws JavaException {
      try {
          JavaValue v = new JavaFloatValue(0.0f);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fconst_1 (Ins_fconst_1 instruction) throws JavaException {
      try {
          JavaValue v = new JavaFloatValue(1.0f);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fconst_2 (Ins_fconst_2 instruction) throws JavaException {
      try {
          JavaValue v = new JavaFloatValue(2.0f);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fdiv (Ins_fdiv instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v = PrimitiveOperations.fdiv(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_filler (Ins_filler instruction) throws JavaException {
      cu.makeAndThrow("java.lang.VerifyError", "Jump to the middle of an instruction");
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fload (Ins_fload instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          v.toFloat(false); // check v is a float without any conversions
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fmul (Ins_fmul instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v = PrimitiveOperations.fmul(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fneg (Ins_fneg instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v2 = PrimitiveOperations.fneg(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_frem (Ins_frem instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v = PrimitiveOperations.frem(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_freturn (Ins_freturn instruction) throws JavaException {
       try {

          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          JavaFloatValue value = cu.popOperandStack().toFloat(false);
          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          if (returnType.equals(CommonDescriptors.floatDesc)) {
              cu.popFrame();
              cu.pushOperandStack(value); // of the caller              
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Incompatible return type");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (VMStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fstore (Ins_fstore instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.popOperandStack();
          v.toFloat(false);
          cu.putLocalVar(index,v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }
  
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_fsub (Ins_fsub instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v = PrimitiveOperations.fsub(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
 }
 
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_getfield (Ins_getfield instruction) throws JavaException {
      try {
          Descriptor startClassDesc = instruction.getDeclaringClassDesc();
          String fieldName = instruction.getFieldName();
          Descriptor fieldDesc = instruction.getFieldType();
      
          JavaReferenceValue objectref = cu.popOperandStack().toReference();
          if (objectref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to getfield");
          else {
              JavaInstance ji = objectref.toNonnullReference().toClassInstance();
              InternalClass startClass = cu.findClass(startClassDesc);
              NameAndDescriptor key = new NameAndDescriptor(fieldName,fieldDesc.toString());
              int index = startClass.findField(key);
              JavaValue fv = ji.getfield(index);
              cu.pushOperandStack(fv);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (FieldNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchFieldError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
 }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_getstatic (Ins_getstatic instruction) throws JavaException {
      try {
          Descriptor declaringClassDesc = instruction.getDeclaringClassDesc();
          String name = instruction.getFieldName();
          Descriptor desc = instruction.getFieldType();
          InternalClass declaringClass = cu.findClass(declaringClassDesc);
          cu.initializeClass(declaringClass);
          NameAndDescriptor key = new NameAndDescriptor(name,desc.toString());
          JavaValue fieldValue = declaringClass.getstatic(key);
          cu.pushOperandStack(fieldValue);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (FieldNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchFieldError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
     }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_goto (Ins_goto instruction) throws JavaException {
      cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
      int offset = instruction.getOffset();
      cu.incrementPC(offset);
  }

  public void visit_goto_w (Ins_goto_w instruction) throws JavaException {
      cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
      int offset = instruction.getOffset();
      cu.incrementPC(offset);
  }

  public void visit_i2b (Ins_i2b instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaByteValue v2 = PrimitiveOperations.i2b(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  public void visit_i2c (Ins_i2c instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaCharValue v2 = PrimitiveOperations.i2c(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
   }

  public void visit_i2d (Ins_i2d instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v2 = PrimitiveOperations.i2d(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_i2f (Ins_i2f instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v2 = PrimitiveOperations.i2f(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_i2l (Ins_i2l instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v2 = PrimitiveOperations.i2l(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_i2s (Ins_i2s instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaShortValue v2 = PrimitiveOperations.i2s(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iadd (Ins_iadd instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.iadd(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_iaload (Ins_iaload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to iaload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.intDesc)) {
                  JavaIntValue value = ja.get(index).toInt(false);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type int");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_iand (Ins_iand instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.iand(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iastore (Ins_iastore instruction) throws JavaException {
      try {
          JavaIntValue value = cu.popOperandStack().toInt(false);
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to iastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.intDesc)) {
                  ja.store(index,value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type int");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  public void visit_iconst_m1 (Ins_iconst_m1 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(-1);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iconst_0 (Ins_iconst_0 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(0);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iconst_1 (Ins_iconst_1 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(1);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iconst_2 (Ins_iconst_2 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iconst_3 (Ins_iconst_3 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(3);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_iconst_4 (Ins_iconst_4 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(4);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iconst_5 (Ins_iconst_5 instruction) throws JavaException {
      try {
          JavaValue v = new JavaIntValue(5);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_idiv (Ins_idiv instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.idiv(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (DivisionE e) { cu.makeAndThrow("java.lang.ArithmeticException", e.getMessage()); }
  }

  public void visit_if_acmpeq (Ins_if_acmpeq instruction) throws JavaException {
      try {
          JavaReferenceValue v2 = cu.popOperandStack().toReference();
          JavaReferenceValue v1 = cu.popOperandStack().toReference(); 
          if (v1 == v2) {
              // success
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
          else {
              return;
          }
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_if_acmpne (Ins_if_acmpne instruction) throws JavaException {
      try {
          JavaReferenceValue v2 = cu.popOperandStack().toReference();
          JavaReferenceValue v1 = cu.popOperandStack().toReference();
          if (v1 == v2) {
              return;
          }
          else {
              // success
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_if_icmpeq (Ins_if_icmpeq instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmpeq(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_if_icmpge (Ins_if_icmpge instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmpge(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }


  public void visit_if_icmpgt (Ins_if_icmpgt instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmpgt(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_if_icmple (Ins_if_icmple instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmple(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_if_icmplt (Ins_if_icmplt instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmplt(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_if_icmpne (Ins_if_icmpne instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          if (PrimitiveOperations.icmpne(v1,v2)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifeq (Ins_ifeq instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.eq(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifge (Ins_ifge instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.ge(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifgt (Ins_ifgt instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.gt(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifle (Ins_ifle instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.le(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iflt (Ins_iflt instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.lt(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifne (Ins_ifne instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (PrimitiveOperations.ne(v)) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { 
          cu.makeAndThrow("java.lang.VerifyError", e.getMessage());
          }
  }

 public void visit_ifnonnull (Ins_ifnonnull instruction) throws JavaException {
     try {
         JavaValue v = cu.popOperandStack();
         JavaReferenceValue av = v.toReference();
         if (! av.isNull()) {
             cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
             int offset = instruction.getOffset();
             cu.incrementPC(offset);
         }
     }
     catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ifnull (Ins_ifnull instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          JavaReferenceValue av = v.toReference();
          if (av.isNull()) {
              cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
              int offset = instruction.getOffset();
              cu.incrementPC(offset);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

 //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_iinc (Ins_iinc instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          int increment = instruction.getIncrement();
          JavaValue v1 = cu.getLocalVar(index);
          JavaValue v2 = PrimitiveOperations.iinc(v1,increment);
          cu.putLocalVar(index,v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iload (Ins_iload instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          v.toInt(false); // check v is an int without any conversions
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { 
          System.err.println("didn't find a local variable: are you sure you called your function with correct args?");
          cu.makeAndThrow("java.lang.VerifyError", e.getMessage());
          }
  }

  public void visit_imul (Ins_imul instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.imul(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ineg (Ins_ineg instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v2 = PrimitiveOperations.ineg(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
 
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   public void visit_instanceof (Ins_instanceof instruction) throws JavaException {
       try {
           JavaReferenceValue objectref = cu.popOperandStack().toReference();
           Descriptor targetClassDesc = instruction.getClassDesc();
           InternalClass targetClass = cu.findClass(targetClassDesc);
           if (!objectref.isNull() && objectref.isAssignmentCompatible(targetClass)) 
               cu.pushOperandStack(new JavaIntValue(1));
           else cu.pushOperandStack(new JavaIntValue(0)); 
       }
       catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
       catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
       catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_invokeinterface (Ins_invokeinterface instruction) throws JavaException {
      throw new Error("invokeinterface not implemented");
  }

  public void visit_invokespecial (Ins_invokespecial instruction) throws JavaException {
      try {
          Descriptor methodClassDesc = instruction.getDeclaringClassDesc();
          String methodName = instruction.getMethodName();
          MethodDescriptor md = instruction.getMethodType();
          String stringDescriptor = md.toString();

          InternalClass hostClass = cu.findClass(methodClassDesc);
          NameAndDescriptor key = new NameAndDescriptor(methodName,stringDescriptor);   
          InternalMethod im = hostClass.findMethod(key);

          int expectedNumArgs = md.numArgs() + 1; // first implicit argument is "this"
          JavaValue[] args = new JavaValue[expectedNumArgs];
          for (int i=expectedNumArgs-1; i>=0; i--) args[i] = cu.popOperandStack();
          if (im.isNative()) cu.callNative(im,args);
          else cu.pushFrame(im,args);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (MethodNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchMethodError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  public void visit_invokestatic (Ins_invokestatic instruction) throws JavaException {
      try {
          Descriptor methodClassDesc = instruction.getDeclaringClassDesc();
          String methodName = instruction.getMethodName();
          MethodDescriptor md = instruction.getMethodType();
          String stringDescriptor = md.toString();

          InternalClass hostClass = cu.findClass(methodClassDesc);
          NameAndDescriptor key = new NameAndDescriptor(methodName,stringDescriptor);   
          InternalMethod im = hostClass.findMethod(key);

          int expectedNumArgs = md.numArgs();
          JavaValue[] args = new JavaValue[expectedNumArgs];
          for (int i=expectedNumArgs-1; i>=0; i--) args[i] = cu.popOperandStack();
          if (im.isNative()) cu.callNative(im,args);
          else cu.pushFrame(im,args);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (MethodNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchMethodError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
   }
  
  public void visit_invokevirtual (Ins_invokevirtual instruction) throws JavaException {
      try {
//          Descriptor methodClassDesc = instruction.getDeclaringClassDesc();
          String methodName = instruction.getMethodName();
          MethodDescriptor md = instruction.getMethodType();
          String stringDescriptor = md.toString();

          NameAndDescriptor key = new NameAndDescriptor(methodName,stringDescriptor);   
          int expectedNumArgs = md.numArgs() + 1; // first implicit argument is "this"
          JavaValue[] args = new JavaValue[expectedNumArgs];
          for (int i=expectedNumArgs-1; i>=0; i--) {
              args[i] = cu.popOperandStack();
          }
          JavaReferenceValue theRef = args[0].toReference();
          JavaNonnullReferenceValue thisref = theRef.toNonnullReference();
          InternalClass hostClass = thisref.getObjectClass();
          InternalMethod im = hostClass.findMethod(key);
          
          if (im.isNative()) {
              cu.callNative(im,args);
          }
          else { 
              cu.pushFrame(im,args);
          }
      }
      catch (BadConversionE e) { 
          System.err.println("bad conversion: " + e.getMessage());
          cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); 
          }
      catch (OperandStackE e) { 
          System.err.println("operand stack error");
          cu.makeAndThrow("java.lang.VerifyError", e.getMessage());
          }
      catch (LocalVarsE e) { 
          System.err.println("local vars error");
          cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); 
          }
      catch (MethodNotFoundE e) { 
          System.err.println("no such method error");
          cu.makeAndThrow("java.lang.NoSuchMethodError", e.getMessage()); 
          }
      catch (LinkE e) { 
          System.err.println("link error");
          cu.makeAndThrow("java.lang.LinkageError", e.getMessage());
          }
   }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public void visit_ior (Ins_ior instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.ior(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_irem (Ins_irem instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.irem(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (DivisionE e) { cu.makeAndThrow("java.lang.ArithmeticException", e.getMessage()); }
  }

  public void visit_ishl (Ins_ishl instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.ishl(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ishr (Ins_ishr instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.ishr(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_istore (Ins_istore instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.popOperandStack();
          v.toInt(false);
          cu.putLocalVar(index,v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_isub (Ins_isub instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.isub(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_iushr (Ins_iushr instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.iushr(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ixor (Ins_ixor instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.ixor(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_jsr (Ins_jsr instruction) throws JavaException {
      try {
          ReturnAddress nextPC = cu.getCurrentPC(); // address of next instruction
          cu.pushOperandStack(nextPC);
          cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
          int offset = instruction.getOffset();
          cu.incrementPC(offset);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_jsr_w (Ins_jsr_w instruction) throws JavaException {
      try {
          ReturnAddress nextPC = cu.getCurrentPC(); // address of next instruction
          cu.pushOperandStack(nextPC);
          cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
          int offset = instruction.getOffset();
          cu.incrementPC(offset);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_l2d (Ins_l2d instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaDoubleValue v2 = PrimitiveOperations.l2d(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_l2f (Ins_l2f instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaFloatValue v2 = PrimitiveOperations.l2f(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_l2i (Ins_l2i instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v2 = PrimitiveOperations.l2i(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ladd (Ins_ladd instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.ladd(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_laload (Ins_laload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to laload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.longDesc)) {
                  JavaLongValue value = ja.get(index).toLong(false);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type long");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
   }

  public void visit_land (Ins_land instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.land(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lastore (Ins_lastore instruction) throws JavaException {
      try {
          JavaLongValue value = cu.popOperandStack().toLong(false);
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to lastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.longDesc)) {
                  ja.store(index,value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type long");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  public void visit_lcmp (Ins_lcmp instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaIntValue v = PrimitiveOperations.lcmp(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lconst_0 (Ins_lconst_0 instruction) throws JavaException {
      try {
          JavaValue v = new JavaLongValue(0L);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lconst_1 (Ins_lconst_1 instruction) throws JavaException {
      try {
          JavaValue v = new JavaLongValue(1L);
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ldc (Ins_ldc instruction) throws JavaException {
      try {
          JavaValue v = null;
          if (instruction.ctype == instruction.INT_TYPE) v = new JavaIntValue(instruction.getIntValue());
          else if (instruction.ctype == instruction.FLOAT_TYPE) v = new JavaFloatValue(instruction.getFloatValue());
          else if (instruction.ctype == instruction.STRING_TYPE) {
              String chars = instruction.getStringValue();
              v = cu.makeStringInstanceFromLiteral(chars);
         }
          else cu.makeAndThrow("java.lang.VerifyError", "Bug in ldc");
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  public void visit_ldc2_w (Ins_ldc2_w instruction) throws JavaException {
      try {
          JavaValue v = null;
          if (instruction.ctype == instruction.LONG_TYPE) v = new JavaLongValue(instruction.getLongValue());
          else if (instruction.ctype == instruction.DOUBLE_TYPE) v = new JavaDoubleValue(instruction.getDoubleValue());
          else cu.makeAndThrow("java.lang.VerifyError", "Bug in ldc2_w");
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_ldc_w (Ins_ldc_w instruction) throws JavaException {
      try {
          JavaValue v = null;
          if (instruction.ctype == instruction.INT_TYPE) v = new JavaIntValue(instruction.getIntValue());
          else if (instruction.ctype == instruction.FLOAT_TYPE) v = new JavaFloatValue(instruction.getFloatValue());
          else if (instruction.ctype == instruction.STRING_TYPE) {
              String chars = instruction.getStringValue();
              v = cu.makeStringInstanceFromLiteral(chars);
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Bug in ldc_w");
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  public void visit_ldiv (Ins_ldiv instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.ldiv(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (DivisionE e) { cu.makeAndThrow("java.lang.ArithmeticException", e.getMessage()); }
  }

  public void visit_lload (Ins_lload instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          v.toLong(false); // check v is a long without any conversions
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lmul (Ins_lmul instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lmul(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lneg (Ins_lneg instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v2 = PrimitiveOperations.lneg(v1);
          cu.pushOperandStack(v2);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lookupswitch (Ins_lookupswitch instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          JavaIntValue av = v.toInt(false); // must be an int
          int key = av.getValue();
          cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
          int offset = instruction.lookup(key);
          cu.incrementPC(offset);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lor (Ins_lor instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lor(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lrem (Ins_lrem instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lrem(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (DivisionE e) { cu.makeAndThrow("java.lang.ArithmeticException", e.getMessage()); }
  }

  public void visit_lreturn (Ins_lreturn instruction) throws JavaException {
       try {

          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          JavaLongValue value = cu.popOperandStack().toLong(false);
          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          if (returnType.equals(CommonDescriptors.longDesc)) {
              cu.popFrame();
              cu.pushOperandStack(value); // of the caller              
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Incompatible return type");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (VMStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lshl (Ins_lshl instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lshl(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lshr (Ins_lshr instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lshr(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lstore (Ins_lstore instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.popOperandStack();
          v.toLong(false);
          cu.putLocalVar(index,v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lsub (Ins_lsub instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lsub(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lushr (Ins_lushr instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lushr(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_lxor (Ins_lxor instruction) throws JavaException {
      try {
          JavaValue v2 = cu.popOperandStack();
          JavaValue v1 = cu.popOperandStack();
          JavaLongValue v = PrimitiveOperations.lxor(v1,v2);
          cu.pushOperandStack(v);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_monitorenter (Ins_monitorenter instruction) throws JavaException {
      try {
          cu.popOperandStack(); // TODO
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }      
  }

  public void visit_monitorexit (Ins_monitorexit instruction) throws JavaException {
      try {
          cu.popOperandStack(); // TODO
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }      
  }

  public void visit_new (Ins_new instruction) throws JavaException {
      try {
          Descriptor classDesc = instruction.getClassDesc();
          InternalClass objectClass = cu.findClass(classDesc);
          cu.initializeClass(objectClass);
          JavaObject jo = new JavaInstance(objectClass);
          JavaReferenceValue rv = new JavaNonnullReferenceValue(jo);
          cu.pushOperandStack(rv);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }
  
  public void visit_multianewarray(Ins_multianewarray instruction) throws JavaException {
        // throw new Error("multianewarray not implemented");
        try {
            int dims = instruction.getDimensions();
            Descriptor componentType = instruction.getComponentType();
            componentType = new Descriptor(componentType.getInternalForm());
            int[] dimSizes = new int[dims];
//            logger.info("multianewarray: ");
            for (int i = 0; i < dims; i++) {
                int count = cu.popOperandStack().toInt(false).getValue();
                if (count < 0)
                    cu.makeAndThrow("java.lang.NegativeArraySizeException", "Invalid array size " + count);
//                logger.info("sz: " + count);
                dimSizes[i] = count;
            }
            JavaArray ja = cu.createMultiArray(dims, dimSizes, componentType);

            JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
            cu.pushOperandStack(arrayref);
        }
      catch (BadDescriptorE e) { throw new Error("Bug"); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

public void visit_newarray (Ins_newarray instruction) throws JavaException {
      try {
          int count = cu.popOperandStack().toInt(false).getValue();
          if (count < 0) cu.makeAndThrow("java.lang.NegativeArraySizeException", "Invalid array size " + count);
//          logger.info("newarray: " + count);
          Descriptor componentType = instruction.getComponentType();
          InternalClass componentClass = cu.findClass(componentType);
          Descriptor arrayClassDesc = new Descriptor("[" + componentType.getStringDescriptor());
          InternalClass arrayClass = cu.findClass(arrayClassDesc);
          JavaArray ja = new JavaArray(arrayClass,componentClass,count);
          JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
          cu.pushOperandStack(arrayref);
      }
      catch (BadDescriptorE e) { throw new Error("Bug"); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  public void visit_anewarray (Ins_anewarray instruction) throws JavaException {
      try {
          int count = cu.popOperandStack().toInt(false).getValue();
          if (count < 0) cu.makeAndThrow("java.lang.NegativeArraySizeException", "Invalid array size " + count);
//          logger.info("anewarray: " + count);
          Descriptor componentType = instruction.getComponentType();
          String internalForm = componentType.getInternalForm();
          
          // WARNING: gross hack
          // sometimes (wormageddon) the class loader has descriptors that aren't arrays but should be (?)
          if (internalForm.startsWith("[") && !componentType.isArray())
                  componentType = new Descriptor(internalForm); // AM -- seems to work at least in the one case i have

          InternalClass componentClass = cu.findClass(componentType);
          Descriptor arrayClassDesc = new Descriptor("[" + componentType.getStringDescriptor());
          InternalClass arrayClass = cu.findClass(arrayClassDesc);
          JavaArray ja = new JavaArray(arrayClass,componentClass,count);
          JavaReferenceValue arrayref = new JavaNonnullReferenceValue(ja);
          cu.pushOperandStack(arrayref);
      }
      catch (BadDescriptorE e) { 
          e.printStackTrace();
          throw new Error("Bug");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void visit_nop (Ins_nop instruction) throws JavaException {
      return;
  }

  public void visit_pop (Ins_pop instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          if (v.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Error in pop");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_pop2 (Ins_pop2 instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          if (v1.getSize() == 1) { 
              JavaValue v2 = cu.popOperandStack();
              if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Error in pop2");
          }
          else if (v1.getSize() == 2) {}
          else cu.makeAndThrow("java.lang.VerifyError", "Error in pop2");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_putfield (Ins_putfield instruction) throws JavaException {
      try {
          Descriptor startClassDesc = instruction.getDeclaringClassDesc();
          String fieldName = instruction.getFieldName();
          Descriptor fieldDesc = instruction.getFieldType();
      
          JavaValue fv = cu.popOperandStack();
          JavaReferenceValue objectref = cu.popOperandStack().toReference();
          if (objectref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to putfield");
          else {
              JavaInstance ji = objectref.toNonnullReference().toClassInstance();
              InternalClass startClass = cu.findClass(startClassDesc);
              NameAndDescriptor key = new NameAndDescriptor(fieldName,fieldDesc.toString());
              int index = startClass.findField(key);
              ji.putfield(index,fv);
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (FieldNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchFieldError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
 }


  public void visit_putstatic (Ins_putstatic instruction) throws JavaException {
      try {
          Descriptor declaringClassDesc = instruction.getDeclaringClassDesc();
          String name = instruction.getFieldName();
          Descriptor desc = instruction.getFieldType();
          InternalClass declaringClass = cu.findClass(declaringClassDesc);
          cu.initializeClass(declaringClass);
          NameAndDescriptor key = new NameAndDescriptor(name,desc.toString());
          JavaValue v = cu.popOperandStack();
          declaringClass.putstatic(key,v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (FieldNotFoundE e) { cu.makeAndThrow("java.lang.NoSuchFieldError", e.getMessage()); }
      catch (LinkE e) { cu.makeAndThrow("java.lang.LinkageError", e.getMessage()); }
  }

  public void visit_ret (Ins_ret instruction) throws JavaException {
      try {
          int index = instruction.getIndex();
          JavaValue v = cu.getLocalVar(index);
          ReturnAddress newPC = v.toReturnAddress();
          cu.setPC(newPC);
      }
      catch (LocalVarsE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_return (Ins_return instruction) throws JavaException, ThreadHaltE {
       try {

          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          if (returnType.equals(CommonDescriptors.voidDesc)) {
              cu.popFrame();
          }
          else cu.makeAndThrow("java.lang.VerifyError", "Incompatible return type");
      }
     catch (VMStackE e) { throw new ThreadHaltE(""); }
 }

  public void visit_ireturn (Ins_ireturn instruction) throws JavaException, ThreadHaltE {
      JavaIntValue value = null;
       try {
          InternalMethod m = cu.getCurrentMethod(); 
          if (m.isSynchronized()) {

          // TODO: synchronized methods

          }

          value = cu.popOperandStack().toInt(false);
          MethodDescriptor md = m.getType();
          Descriptor returnType = md.getReturnType();
          //if (returnType.equals(CommonDescriptors.intDesc)) { 
          cu.popFrame();
          cu.pushOperandStack(value); // of the caller
          //}
          //else cu.makeAndThrow("java.lang.VerifyError", "Incompatible return type");
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (VMStackE e) {
            // cu.makeAndThrow("java.lang.VerifyError", e.getMessage());
            try {
                cu.pushOperandStack(value);
            }
            catch (OperandStackE e1) {
                e1.printStackTrace();
            }
            // of the caller
            throw new ThreadHaltE("");
      }
    }
  
  public void visit_saload (Ins_saload instruction) throws JavaException {
      try {
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to saload");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.shortDesc)) {
                  JavaShortValue value = ja.get(index).toShort(false);
                  cu.pushOperandStack(value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type short");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  public void visit_sastore (Ins_sastore instruction) throws JavaException {
      try {
          JavaShortValue value = cu.popOperandStack().toShort(false);
          int index = cu.popOperandStack().toInt(false).getValue();
          JavaReferenceValue arrayref = cu.popOperandStack().toReference();
          if (arrayref.isNull()) 
              cu.makeAndThrow("java.lang.NullPointerException", "Null reference to sastore");
          else {
              JavaArray ja = arrayref.toNonnullReference().toArray();
              InternalClass componentClass = ja.getComponentClass();
              if (componentClass.getDesc().equals(CommonDescriptors.shortDesc)) {
                  ja.store(index,value);
              }
              else cu.makeAndThrow("java.lang.VerifyError", "Component type not of type short");
          }
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (JavaArrayOutOfBoundsE e) { cu.makeAndThrow("java.lang.ArrayIndexOutOfBoundsException", e.getMessage()); }
  }

  public void visit_sipush (Ins_sipush instruction) throws JavaException {
      try {
          JavaIntValue v = new JavaIntValue(instruction.getValue());
          cu.pushOperandStack(v);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_swap (Ins_swap instruction) throws JavaException {
      try {
          JavaValue v1 = cu.popOperandStack();
          JavaValue v2 = cu.popOperandStack();
          if (v1.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Error in swap");
          if (v2.getSize() != 1) cu.makeAndThrow("java.lang.VerifyError", "Error in swap");
          cu.pushOperandStack(v1);
          cu.pushOperandStack(v2);
      }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }

  public void visit_tableswitch (Ins_tableswitch instruction) throws JavaException {
      try {
          JavaValue v = cu.popOperandStack();
          int index = v.toInt(false).getValue();
          cu.incrementPC(-instruction.getLength()); // reset PC to address of current instruction
          int offset = instruction.getOffset(index);
          cu.incrementPC(offset);
      }
      catch (BadConversionE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
      catch (OperandStackE e) { cu.makeAndThrow("java.lang.VerifyError", e.getMessage()); }
  }
}
