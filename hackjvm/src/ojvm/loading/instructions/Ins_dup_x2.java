package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dup_x2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dup_x2 extends Instruction {

  public Ins_dup_x2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dup_x2);
  }
  
  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dup_x2 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
