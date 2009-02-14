 package ojvm.loading.instructions;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an istore_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_istore_0 extends Ins_istore {

  public Ins_istore_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_istore_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
