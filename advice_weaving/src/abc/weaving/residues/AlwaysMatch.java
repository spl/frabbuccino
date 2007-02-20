/* abc - The AspectBench Compiler
 * Copyright (C) 2004 Ganesh Sittampalam
 * Copyright (C) 2004 Ondrej Lhotak
 *
 * This compiler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this compiler, in the file LESSER-GPL;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package abc.weaving.residues;

import soot.SootMethod;
import soot.util.Chain;
import soot.jimple.*;
import abc.soot.util.LocalGeneratorEx;
import abc.weaving.tagkit.InstructionKindTag;
import abc.weaving.tagkit.Tagger;
import abc.weaving.weaver.*;

/** A "dynamic" residue that always matches.
 *  Intended for convenience during generation and residue analysis process.
 *  @author Ganesh Sittampalam
 *  @author Ondrej Lhotak
 */

public class AlwaysMatch extends Residue {
    private final static AlwaysMatch v=new AlwaysMatch();
    public Residue optimize() { return this; }
    public Residue inline(ConstructorInliningMap cim) { return this; }
    public static AlwaysMatch v() {
        return v;
    }

    public String toString() {
        return "always";
    }

    public Stmt codeGen(SootMethod method,LocalGeneratorEx localgen,
                        Chain units,Stmt begin,Stmt fail,boolean sense,
                        WeavingContext wc) {

        if(sense) return begin;

        Stmt abort=Jimple.v().newGotoStmt(fail);
        if(wc.getKindTag() == null) {
            wc.setKindTag(InstructionKindTag.ADVICE_ARG_SETUP);
        }
        Tagger.tagStmt(abort, wc);
        units.insertAfter(abort,begin);
        return abort;
    }

}
