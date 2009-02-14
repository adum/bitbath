 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dstore_2 extends Ins_dstore {

  public Ins_dstore_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dstore_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
