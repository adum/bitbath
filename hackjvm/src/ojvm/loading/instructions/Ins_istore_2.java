 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an istore_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_istore_2 extends Ins_istore {

  public Ins_istore_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_istore_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
