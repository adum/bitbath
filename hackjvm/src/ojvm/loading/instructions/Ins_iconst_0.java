package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iconst_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iconst_0 extends Ins_bipush {

  public Ins_iconst_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iconst_0, (byte)0);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iconst_0 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
