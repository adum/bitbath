 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an istore_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_istore_3 extends Ins_istore {

  public Ins_istore_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_istore_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
