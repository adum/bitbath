 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dstore_1 extends Ins_dstore {

  public Ins_dstore_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dstore_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
