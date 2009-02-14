 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lstore_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lstore_2 extends Ins_lstore {

  public Ins_lstore_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lstore_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
