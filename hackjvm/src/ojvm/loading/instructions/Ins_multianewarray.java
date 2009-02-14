package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;
import ojvm.util.Descriptor;

/**
 * The encapsulation of an multianewarray instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_multianewarray extends Instruction {
  private Descriptor componentType;
  private int dimensions;

  public Ins_multianewarray (InstructionInputStream classFile, ConstantPool cp) 
      throws ClassFileInputStreamE, ConstantPoolE {

      super(RuntimeConstants.opc_multianewarray);
      int index = classFile.readU2();
      componentType = cp.getClassEntry(index).getDesc();
      dimensions = classFile.readU1();
      if (dimensions < 1) throw new ConstantPoolE("Bad dimensions for multianewarray");
  }

  public Descriptor getComponentType () { return componentType; }
  public int getDimensions () { return dimensions; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_multianewarray (this);
  }

  public String toString () {
    return opcodeName + " " + componentType + "[" + dimensions + "]";
  }
}
