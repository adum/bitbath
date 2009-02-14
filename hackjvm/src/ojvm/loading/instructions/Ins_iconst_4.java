package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iconst_4 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iconst_4 extends Ins_bipush {

  public Ins_iconst_4 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iconst_4, (byte)4);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iconst_4 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
