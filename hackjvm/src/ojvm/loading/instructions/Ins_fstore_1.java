 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fstore_1 extends Ins_fstore {

  public Ins_fstore_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fstore_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
