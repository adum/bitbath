 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fload_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fload_2 extends Ins_fload {

  public Ins_fload_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fload_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
