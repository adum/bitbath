 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lload_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lload_1 extends Ins_lload {

  public Ins_lload_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lload_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
