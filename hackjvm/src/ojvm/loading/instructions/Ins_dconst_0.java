package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dconst_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dconst_0 extends Instruction {

  public Ins_dconst_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dconst_0);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dconst_0 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
