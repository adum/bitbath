package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fconst_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fconst_2 extends Instruction {

  public Ins_fconst_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fconst_2);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fconst_2 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
