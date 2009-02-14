package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.CPMethodEntry;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.Descriptor;
import ojvm.util.MethodDescriptor;
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an invokestatic instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_invokestatic extends Instruction {
  private Descriptor declaringClassDesc;
  private String methodName;
  private MethodDescriptor methodDesc; 

  public Ins_invokestatic (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_invokestatic);
      int index = classFile.readU2();
      CPMethodEntry me = cp.getMethodEntry(index);
      declaringClassDesc = me.getDeclaringClassDesc();
      methodName = me.getMethodName();
      methodDesc = me.getMethodType();
  }

  public Descriptor getDeclaringClassDesc () { return declaringClassDesc; }
  public String getMethodName () { return methodName; }
  public MethodDescriptor getMethodType () { return methodDesc; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_invokestatic (this);
  }

  public String toString () {
      String res = opcodeName + " " + declaringClassDesc + " :: " + methodName + " : " + methodDesc;
      return res;
  }
}
