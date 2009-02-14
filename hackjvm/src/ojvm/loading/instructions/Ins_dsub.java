package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dsub instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dsub extends Instruction {

  public Ins_dsub (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dsub);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dsub (this);
  }

  public String toString () {
    return opcodeName;
  }
}
