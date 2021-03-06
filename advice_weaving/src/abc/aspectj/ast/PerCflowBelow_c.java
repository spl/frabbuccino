/* abc - The AspectBench Compiler
 * Copyright (C) 2004 Oege de Moor
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

package abc.aspectj.ast;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import java.util.*;

import abc.aspectj.types.AspectType;

/**
 * 
 * @author Oege de Moor
 *
 */
public class PerCflowBelow_c extends PerClause_c implements PerCflowBelow
{

    Pointcut pc;

    public PerCflowBelow_c(Position pos, Pointcut pc)
    {
	super(pos);
        this.pc = pc;
    }

	protected PerCflowBelow_c reconstruct(Pointcut pc) {
		if (pc != this.pc ) {
			PerCflowBelow_c n = (PerCflowBelow_c) copy();
			n.pc = pc;
			return n;
		}
		return this;
	}

	public int kind() {
		return AspectType.PER_CFLOWBELOW;
	}

	public Node visitChildren(NodeVisitor v) {
		Pointcut pc = (Pointcut) visitChild(this.pc, v);
		return reconstruct(pc);
	}
	
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("percflowbelow (");
        print(pc, w, tr);
        w.write(")");
    }

    public abc.weaving.aspectinfo.Per makeAIPer() {
	return new abc.weaving.aspectinfo.PerCflowBelow(pc.makeAIPointcut(),position());
    }
}
