package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lsub instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lsub extends Instruction {

  public Ins_lsub (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lsub);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lsub (this);
  }

  public String toString () {
    return opcodeName;
  }
}
