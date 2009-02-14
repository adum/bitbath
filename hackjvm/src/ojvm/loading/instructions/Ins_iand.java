package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a iand instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iand extends Instruction {

  public Ins_iand (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iand);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iand (this);
  }

  public String toString () {
    return opcodeName;
  }
}
