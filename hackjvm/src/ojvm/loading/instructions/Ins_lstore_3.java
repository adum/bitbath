 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lstore_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lstore_3 extends Ins_lstore {

  public Ins_lstore_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lstore_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
