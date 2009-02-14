 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fload_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fload_0 extends Ins_fload {

  public Ins_fload_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fload_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
