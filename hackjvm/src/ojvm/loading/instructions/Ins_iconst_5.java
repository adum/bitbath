package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iconst_5 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iconst_5 extends Ins_bipush {

  public Ins_iconst_5 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iconst_5, (byte)5);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iconst_5 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
