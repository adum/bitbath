package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lushr instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lushr extends Instruction {

  public Ins_lushr (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lushr);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lushr (this);
  }

  public String toString () {
    return opcodeName;
  }
}
