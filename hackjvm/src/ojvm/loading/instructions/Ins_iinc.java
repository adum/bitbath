package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iinc instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iinc extends Instruction {
  private int index;
  private short increment;

  public Ins_iinc (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_iinc);
      this.index = classFile.readU1();
      this.increment = (short) classFile.readByte();
  }

  public Ins_iinc (int opcode, int index, short increment) {
    super(opcode);
    this.index = index;
    this.increment = increment;
  }

  public int getIndex () { return index; }

  public short getIncrement () { return increment; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_iinc (this);
  }

  public String toString () {
    return opcodeName + " " + index + " " + increment;
  }
}
