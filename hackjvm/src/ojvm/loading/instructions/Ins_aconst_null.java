package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aconst_null instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aconst_null extends Instruction {

  public Ins_aconst_null (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aconst_null);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_aconst_null(this);
  }

  public String toString () {
    return opcodeName;
  }
}
