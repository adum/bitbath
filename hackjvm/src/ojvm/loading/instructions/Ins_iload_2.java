 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iload_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iload_2 extends Ins_iload {

  public Ins_iload_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iload_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
