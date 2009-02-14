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
 * The encapsulation of an invokevirtual instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_invokevirtual extends Instruction {
  private Descriptor declaringClassDesc;
  private String methodName;
  private MethodDescriptor methodDesc;

  public Ins_invokevirtual (InstructionInputStream classFile, ConstantPool cp) 
      throws ClassFileInputStreamE, ConstantPoolE {

      super(RuntimeConstants.opc_invokevirtual);
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
    iv.visit_invokevirtual (this);
  }

  public String toString () {
      String res = opcodeName + " ";
      res += declaringClassDesc + " :: " + methodName + " : " + methodDesc;
      return res;
  }
}
