 package ojvm.loading.instructions;
                         
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aload_2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aload_2 extends Ins_aload {
  public Ins_aload_2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aload_2, 2);
  }

  public String toString () {
    return opcodeName;
  }
}
