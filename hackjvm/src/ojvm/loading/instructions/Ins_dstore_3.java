 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dstore_3 extends Ins_dstore {

  public Ins_dstore_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dstore_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
