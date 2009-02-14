 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fload_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fload_1 extends Ins_fload {

  public Ins_fload_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fload_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
