 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iload_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iload_3 extends Ins_iload {

  public Ins_iload_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iload_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
