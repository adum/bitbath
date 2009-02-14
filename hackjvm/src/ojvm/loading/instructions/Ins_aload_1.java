 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aload_1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aload_1 extends Ins_aload {
  public Ins_aload_1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aload_1, 1);
  }

  public String toString () {
    return opcodeName;
  }
}
