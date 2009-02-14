 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fstore_2 extends Ins_fstore {

  public Ins_fstore_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fstore_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
