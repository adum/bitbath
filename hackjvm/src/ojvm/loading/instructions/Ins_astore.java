package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an astore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_astore extends Instruction {
  private int index;

  public Ins_astore (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_astore);
      this.index = classFile.readU1();
  }

  protected Ins_astore (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_astore (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
