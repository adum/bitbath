 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dstore_0 extends Ins_dstore {

  public Ins_dstore_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dstore_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
