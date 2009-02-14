 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lstore_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lstore_0 extends Ins_lstore {

  public Ins_lstore_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lstore_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
