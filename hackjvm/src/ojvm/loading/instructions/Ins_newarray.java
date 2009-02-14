package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;
import ojvm.loading.ConstantPoolE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an newarray instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_newarray extends Instruction {
  private Descriptor componentType;

  public Ins_newarray (InstructionInputStream classFile) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_newarray);
      try {
          int atype = classFile.readU1();
          componentType = new Descriptor(atype);
      }
      catch (BadDescriptorE e) {
          throw new ConstantPoolE("Bad newarray descriptor " + e.getMessage());
      }
  }

  public Descriptor getComponentType() { return componentType; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_newarray (this);
  }

  public String toString () {
    return opcodeName + " " + componentType;
  }
}
