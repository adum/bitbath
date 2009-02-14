package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lxor instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lxor extends Instruction {

  public Ins_lxor (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lxor);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lxor (this);
  }

  public String toString () {
    return opcodeName;
  }
}
