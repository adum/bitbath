package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.CPInterfaceMethodEntry;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.Descriptor;
import ojvm.util.MethodDescriptor;
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an invokeinterface instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_invokeinterface extends Instruction {
  private Descriptor declaringInterfaceDesc;
  private String methodName;
  private MethodDescriptor methodDesc;
  private int nargs;

  public Ins_invokeinterface (InstructionInputStream classFile, ConstantPool cp) 
      throws ClassFileInputStreamE, ConstantPoolE {

      super(RuntimeConstants.opc_invokeinterface);

      int index = classFile.readU2();
      CPInterfaceMethodEntry ime = cp.getInterfaceMethodEntry(index);
      declaringInterfaceDesc = ime.getDeclaringClassDesc();
      methodName = ime.getMethodName();
      methodDesc = ime.getMethodType();

      nargs = classFile.readU1();
      if (this.nargs == 0) throw new ConstantPoolE("nargs to invokeinterface cannot be zero");
      if (classFile.readU1() != '\u0000') 
          throw new ConstantPoolE("Illegal parameter to invokeinterface instruction");
  }

  public Descriptor getDeclaringInterfaceDesc () { return declaringInterfaceDesc; }
  public String getMethodName () { return methodName; }
  public MethodDescriptor getMethodType () { return methodDesc; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_invokeinterface (this);
  }

  public String toString () {
      String res = opcodeName + " ";
      res += declaringInterfaceDesc + " :: " + methodName + "(" + nargs + ") : " + methodDesc;
      return res;
  }
}
