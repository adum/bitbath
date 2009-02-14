 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iload_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iload_1 extends Ins_iload {

  public Ins_iload_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iload_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
