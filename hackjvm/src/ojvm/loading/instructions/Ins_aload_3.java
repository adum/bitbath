 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aload_3 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aload_3 extends Ins_aload {
  public Ins_aload_3 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aload_3, 3);
  }

  public String toString () {
    return opcodeName;
  }
}
