 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fload_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fload_3 extends Ins_fload {

  public Ins_fload_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fload_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
