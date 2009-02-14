package ojvm.operations;

import ojvm.data.JavaException;
import ojvm.machine.ControlUnit;
import ojvm.loading.instructions.*;

/**
 * 
 * One method for each instruction. 
 * 
 * File created June 16, 2000
 * @author Amr Sabry
 *  */

public class BytecodeVerifier implements InstructionVisitor {
    private ControlUnit cu;

    public BytecodeVerifier (ControlUnit cu) {
        this.cu = cu;
    }

  public void visit_aaload (Ins_aaload instruction) throws JavaException {
  }

  public void visit_aastore (Ins_aastore instruction) throws JavaException {
  }

  public void visit_aconst_null (Ins_aconst_null instruction) throws JavaException {
  }

  public void visit_aload (Ins_aload instruction) throws JavaException {
  }

  public void visit_anewarray (Ins_anewarray instruction) throws JavaException {
  }

  public void visit_areturn (Ins_areturn instruction) throws JavaException {
  }

  public void visit_arraylength (Ins_arraylength instruction) throws JavaException {
  }

  public void visit_astore (Ins_astore instruction) throws JavaException {
  }

  public void visit_athrow (Ins_athrow instruction) throws JavaException {
  }

  public void visit_baload (Ins_baload instruction) throws JavaException {
  }

  public void visit_bastore (Ins_bastore instruction) throws JavaException {
  }

  public void visit_bipush (Ins_bipush instruction) throws JavaException {
  }

  public void visit_caload (Ins_caload instruction) throws JavaException {
  }

  public void visit_castore (Ins_castore instruction) throws JavaException {
  }

  public void visit_checkcast (Ins_checkcast instruction) throws JavaException {
  }

  public void visit_d2f (Ins_d2f instruction) throws JavaException {
  }

  public void visit_d2i (Ins_d2i instruction) throws JavaException {
  }

  public void visit_d2l (Ins_d2l instruction) throws JavaException {
  }

  public void visit_dadd (Ins_dadd instruction) throws JavaException {
  }

  public void visit_daload (Ins_daload instruction) throws JavaException {
  }

  public void visit_dastore (Ins_dastore instruction) throws JavaException {
  }

  public void visit_dcmpg (Ins_dcmpg instruction) throws JavaException {
  }

  public void visit_dcmpl (Ins_dcmpl instruction) throws JavaException {
  }

  public void visit_dconst_0 (Ins_dconst_0 instruction) throws JavaException {
  }

  public void visit_dconst_1 (Ins_dconst_1 instruction) throws JavaException {
  }

  public void visit_ddiv (Ins_ddiv instruction) throws JavaException {
  }

  public void visit_dload (Ins_dload instruction) throws JavaException {
  }

  public void visit_dmul (Ins_dmul instruction) throws JavaException {
  }

  public void visit_dneg (Ins_dneg instruction) throws JavaException {
  }

  public void visit_drem (Ins_drem instruction) throws JavaException {
  }

  public void visit_dreturn (Ins_dreturn instruction) throws JavaException {
  }

  public void visit_dstore (Ins_dstore instruction) throws JavaException {
  }

  public void visit_dsub (Ins_dsub instruction) throws JavaException {
  }

  public void visit_dup (Ins_dup instruction) throws JavaException {
  }

  public void visit_dup2 (Ins_dup2 instruction) throws JavaException {
  }

  public void visit_dup2_x1 (Ins_dup2_x1 instruction) throws JavaException {
  }

  public void visit_dup2_x2 (Ins_dup2_x2 instruction) throws JavaException {
  }

  public void visit_dup_x1 (Ins_dup_x1 instruction) throws JavaException {
  }

  public void visit_dup_x2 (Ins_dup_x2 instruction) throws JavaException {
  }

  public void visit_f2d (Ins_f2d instruction) throws JavaException {
  }

  public void visit_f2i (Ins_f2i instruction) throws JavaException {
  }

  public void visit_f2l (Ins_f2l instruction) throws JavaException {
  }

  public void visit_fadd (Ins_fadd instruction) throws JavaException {
  }

  public void visit_faload (Ins_faload instruction) throws JavaException {
  }
  
  public void visit_fastore (Ins_fastore instruction) throws JavaException {
  }
  
  public void visit_fcmpg (Ins_fcmpg instruction) throws JavaException {
  }
  
  public void visit_fcmpl (Ins_fcmpl instruction) throws JavaException {
  }
  
  public void visit_fconst_0 (Ins_fconst_0 instruction) throws JavaException {
  }
  
  public void visit_fconst_1 (Ins_fconst_1 instruction) throws JavaException {
  }
  
  public void visit_fconst_2 (Ins_fconst_2 instruction) throws JavaException {
  }
  
  public void visit_fdiv (Ins_fdiv instruction) throws JavaException {
  }

  public void visit_filler (Ins_filler instruction) throws JavaException {
  }

  public void visit_fload (Ins_fload instruction) throws JavaException {
  }
  
  public void visit_fmul (Ins_fmul instruction) throws JavaException {
  }

  public void visit_fneg (Ins_fneg instruction) throws JavaException {
  }

  public void visit_frem (Ins_frem instruction) throws JavaException {
  }

  public void visit_freturn (Ins_freturn instruction) throws JavaException {
  }
  
  public void visit_fstore (Ins_fstore instruction) throws JavaException {
  }
  
  public void visit_fsub (Ins_fsub instruction) throws JavaException {
  }

  public void visit_getfield (Ins_getfield instruction) throws JavaException {
  }

  public void visit_getstatic (Ins_getstatic instruction) throws JavaException {
  }

  public void visit_goto (Ins_goto instruction) throws JavaException {
  }

  public void visit_goto_w (Ins_goto_w instruction) throws JavaException {
  }

  public void visit_i2b (Ins_i2b instruction) throws JavaException {
  }

  public void visit_i2c (Ins_i2c instruction) throws JavaException {
  }

  public void visit_i2d (Ins_i2d instruction) throws JavaException {
  }

  public void visit_i2f (Ins_i2f instruction) throws JavaException {
  }

  public void visit_i2l (Ins_i2l instruction) throws JavaException {
  }

  public void visit_i2s (Ins_i2s instruction) throws JavaException {
  }

  public void visit_iadd (Ins_iadd instruction) throws JavaException {
  }

  public void visit_iaload (Ins_iaload instruction) throws JavaException {
  }

  public void visit_iand (Ins_iand instruction) throws JavaException {
  }

  public void visit_iastore (Ins_iastore instruction) throws JavaException {
  }

  public void visit_iconst_m1 (Ins_iconst_m1 instruction) throws JavaException {
  }

  public void visit_iconst_0 (Ins_iconst_0 instruction) throws JavaException {
  }

  public void visit_iconst_1 (Ins_iconst_1 instruction) throws JavaException {
  }

  public void visit_iconst_2 (Ins_iconst_2 instruction) throws JavaException {
  }

  public void visit_iconst_3 (Ins_iconst_3 instruction) throws JavaException {
  }

  public void visit_iconst_4 (Ins_iconst_4 instruction) throws JavaException {
  }

  public void visit_iconst_5 (Ins_iconst_5 instruction) throws JavaException {
  }

  public void visit_idiv (Ins_idiv instruction) throws JavaException {
  }

  public void visit_if_acmpeq (Ins_if_acmpeq instruction) throws JavaException {
  }

  public void visit_if_acmpne (Ins_if_acmpne instruction) throws JavaException {
  }

  public void visit_if_icmpeq (Ins_if_icmpeq instruction) throws JavaException {
  }

  public void visit_if_icmpge (Ins_if_icmpge instruction) throws JavaException {
  }

  public void visit_if_icmpgt (Ins_if_icmpgt instruction) throws JavaException {
  }

  public void visit_if_icmple (Ins_if_icmple instruction) throws JavaException {
  }

  public void visit_if_icmplt (Ins_if_icmplt instruction) throws JavaException {
  }

  public void visit_if_icmpne (Ins_if_icmpne instruction) throws JavaException {
  }

  public void visit_ifeq (Ins_ifeq instruction) throws JavaException {
  }

  public void visit_ifge (Ins_ifge instruction) throws JavaException {
  }

  public void visit_ifgt (Ins_ifgt instruction) throws JavaException {
  }

  public void visit_ifle (Ins_ifle instruction) throws JavaException {
  }

  public void visit_iflt (Ins_iflt instruction) throws JavaException {
  }

  public void visit_ifne (Ins_ifne instruction) throws JavaException {
  }

  public void visit_ifnonnull (Ins_ifnonnull instruction) throws JavaException {
  }

  public void visit_ifnull (Ins_ifnull instruction) throws JavaException {
  }

  public void visit_iinc (Ins_iinc instruction) throws JavaException {
  }

  public void visit_iload (Ins_iload instruction) throws JavaException {
  }

  public void visit_imul (Ins_imul instruction) throws JavaException {
  }

  public void visit_ineg (Ins_ineg instruction) throws JavaException {
  }

  public void visit_instanceof (Ins_instanceof instruction) throws JavaException {
  }

  public void visit_invokeinterface (Ins_invokeinterface instruction) throws JavaException {
  }

  public void visit_invokespecial (Ins_invokespecial instruction) throws JavaException {
  }

  public void visit_invokestatic (Ins_invokestatic instruction) throws JavaException {
  }

  public void visit_invokevirtual (Ins_invokevirtual instruction) throws JavaException {
  }

  public void visit_ior (Ins_ior instruction) throws JavaException {
  }

  public void visit_irem (Ins_irem instruction) throws JavaException {
  }

  public void visit_ireturn (Ins_ireturn instruction) throws JavaException {
  }

  public void visit_ishl (Ins_ishl instruction) throws JavaException {
  }

  public void visit_ishr (Ins_ishr instruction) throws JavaException {
  }

  public void visit_istore (Ins_istore instruction) throws JavaException {
  }

  public void visit_isub (Ins_isub instruction) throws JavaException {
  }

  public void visit_iushr (Ins_iushr instruction) throws JavaException {
  }

  public void visit_ixor (Ins_ixor instruction) throws JavaException {
  }

  public void visit_jsr (Ins_jsr instruction) throws JavaException {
  }

  public void visit_jsr_w (Ins_jsr_w instruction) throws JavaException {
  }

  public void visit_l2d (Ins_l2d instruction) throws JavaException {
  }

  public void visit_l2f (Ins_l2f instruction) throws JavaException {
  }

  public void visit_l2i (Ins_l2i instruction) throws JavaException {
  }

  public void visit_ladd (Ins_ladd instruction) throws JavaException {
  }

  public void visit_laload (Ins_laload instruction) throws JavaException {
  }

  public void visit_land (Ins_land instruction) throws JavaException {
  }

  public void visit_lastore (Ins_lastore instruction) throws JavaException {
  }

  public void visit_lcmp (Ins_lcmp instruction) throws JavaException {
  }

  public void visit_lconst_0 (Ins_lconst_0 instruction) throws JavaException {
  }

  public void visit_lconst_1 (Ins_lconst_1 instruction) throws JavaException {
  }

  public void visit_ldc (Ins_ldc instruction) throws JavaException {
  }

  public void visit_ldc2_w (Ins_ldc2_w instruction) throws JavaException {
  }

  public void visit_ldc_w (Ins_ldc_w instruction) throws JavaException {
  }

  public void visit_ldiv (Ins_ldiv instruction) throws JavaException {
  }

  public void visit_lload (Ins_lload instruction) throws JavaException {
  }

  public void visit_lmul (Ins_lmul instruction) throws JavaException {
  }

  public void visit_lneg (Ins_lneg instruction) throws JavaException {
  }

  public void visit_lookupswitch (Ins_lookupswitch instruction) throws JavaException {
  }

  public void visit_lor (Ins_lor instruction) throws JavaException {
  }

  public void visit_lrem (Ins_lrem instruction) throws JavaException {
  }

  public void visit_lreturn (Ins_lreturn instruction) throws JavaException {
  }

  public void visit_lshl (Ins_lshl instruction) throws JavaException {
  }

  public void visit_lshr (Ins_lshr instruction) throws JavaException {
  }

  public void visit_lstore (Ins_lstore instruction) throws JavaException {
  }

  public void visit_lsub (Ins_lsub instruction) throws JavaException {
  }

  public void visit_lushr (Ins_lushr instruction) throws JavaException {
  }

  public void visit_lxor (Ins_lxor instruction) throws JavaException {
  }

  public void visit_monitorenter (Ins_monitorenter instruction) throws JavaException {
  }

  public void visit_monitorexit (Ins_monitorexit instruction) throws JavaException {
  }

  public void visit_multianewarray (Ins_multianewarray instruction) throws JavaException {
  }

  public void visit_new (Ins_new instruction) throws JavaException {
  }

  public void visit_newarray (Ins_newarray instruction) throws JavaException {
  }

  public void visit_nop (Ins_nop instruction) throws JavaException {
  }

  public void visit_pop (Ins_pop instruction) throws JavaException {
  }

  public void visit_pop2 (Ins_pop2 instruction) throws JavaException {
  }

  public void visit_putfield (Ins_putfield instruction) throws JavaException {
  }

  public void visit_putstatic (Ins_putstatic instruction) throws JavaException {
  }

  public void visit_ret (Ins_ret instruction) throws JavaException {
  }

  public void visit_return (Ins_return instruction) throws JavaException {
  }

  public void visit_saload (Ins_saload instruction) throws JavaException {
  }

  public void visit_sastore (Ins_sastore instruction) throws JavaException {
  }

  public void visit_sipush (Ins_sipush instruction) throws JavaException {
  }

  public void visit_swap (Ins_swap instruction) throws JavaException {
  }

  public void visit_tableswitch (Ins_tableswitch instruction) throws JavaException {
  }
}
