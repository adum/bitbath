 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fstore_3 extends Ins_fstore {

  public Ins_fstore_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fstore_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
