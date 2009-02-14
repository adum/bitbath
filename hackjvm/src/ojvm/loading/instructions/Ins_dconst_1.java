package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dconst_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dconst_1 extends Instruction {

  public Ins_dconst_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dconst_1);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dconst_1 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
