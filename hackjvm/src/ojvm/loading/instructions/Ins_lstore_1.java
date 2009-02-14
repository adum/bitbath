 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lstore_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lstore_1 extends Ins_lstore {

  public Ins_lstore_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lstore_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
