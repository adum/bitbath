package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lconst_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lconst_1 extends Instruction {

  public Ins_lconst_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lconst_1);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lconst_1 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
