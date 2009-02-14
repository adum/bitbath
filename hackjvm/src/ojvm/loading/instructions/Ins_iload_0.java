 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iload_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iload_0 extends Ins_iload {

  public Ins_iload_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iload_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
