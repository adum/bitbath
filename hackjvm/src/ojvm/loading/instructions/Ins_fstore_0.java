 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fstore_0 extends Ins_fstore {

  public Ins_fstore_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fstore_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
