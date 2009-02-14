 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lload_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lload_2 extends Ins_lload {

  public Ins_lload_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lload_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
