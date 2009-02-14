package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a castore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_castore extends Instruction {

  public Ins_castore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_castore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_castore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
