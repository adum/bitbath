 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lload_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lload_0 extends Ins_lload {

  public Ins_lload_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lload_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
