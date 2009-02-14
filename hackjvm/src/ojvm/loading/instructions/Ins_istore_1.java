 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an istore_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_istore_1 extends Ins_istore {

  public Ins_istore_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_istore_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
