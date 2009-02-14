 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dload_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dload_1 extends Ins_dload {

  public Ins_dload_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dload_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
