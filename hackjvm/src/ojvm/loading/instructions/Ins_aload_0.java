 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aload_0 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aload_0 extends Ins_aload {
  public Ins_aload_0 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aload_0, 0);
  }

  public String toString () {
    return opcodeName;
  }
}
