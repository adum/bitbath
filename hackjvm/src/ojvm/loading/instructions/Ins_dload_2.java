 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dload_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dload_2 extends Ins_dload {

  public Ins_dload_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dload_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
