package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a bipush instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_bipush extends Instruction {
  private byte value;

  public Ins_bipush (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_bipush);
      this.value = classFile.readByte();
  }

  protected Ins_bipush (int opcode, byte value) {
    super(opcode);
    this.value = value; 
  }

  public byte getValue () { return value; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_bipush (this);
  }

  public String toString () {
    return opcodeName + " " + value;
  }
}
