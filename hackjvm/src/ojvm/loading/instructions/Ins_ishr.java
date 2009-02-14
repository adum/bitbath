package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ishr instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ishr extends Instruction {

  public Ins_ishr (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ishr);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ishr (this);
  }

  public String toString () {
    return opcodeName;
  }
}
