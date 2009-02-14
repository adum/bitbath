 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lload_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lload_3 extends Ins_lload {

  public Ins_lload_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lload_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
