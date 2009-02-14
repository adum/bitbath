package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;
import ojvm.machine.ThreadHaltE;

import ojvm.util.RuntimeConstants;

/**
 * Root of the instruction hierarchy...
 * @author Amr Sabry
 * @version jdk-1.1 
*/

public abstract class Instruction {
  protected final int OPCODE;
  protected final String opcodeName;
  protected int length;

  public Instruction (int opcode) {
    OPCODE = opcode;
    opcodeName = RuntimeConstants.opcNames[OPCODE];
    length = RuntimeConstants.opcLengths[OPCODE];
  }

  public int getLength ( ) {
    return length;
  }

  public boolean isActualInstruction () { return true; } // except for Filler

  public abstract void accept (InstructionVisitor iv) throws JavaException, ThreadHaltE;

}
