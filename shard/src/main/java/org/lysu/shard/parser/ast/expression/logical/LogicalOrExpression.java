/*
 * Copyright 1999-2012 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * (created at 2011-1-12)
 */
package org.lysu.shard.parser.ast.expression.logical;


import org.lysu.shard.parser.ast.expression.Expression;
import org.lysu.shard.parser.ast.expression.PolyadicOperatorExpression;
import org.lysu.shard.parser.ast.expression.primary.literal.LiteralBoolean;
import org.lysu.shard.parser.util.ExprEvalUtils;
import org.lysu.shard.parser.visitor.SQLASTVisitor;

import java.util.Map;


/**
 * @author <a href="mailto:shuo.qius@alibaba-inc.com">QIU Shuo</a>
 */
public class LogicalOrExpression extends PolyadicOperatorExpression {
    public LogicalOrExpression() {
        super(PRECEDENCE_LOGICAL_OR);
    }

    @Override
    public String getOperator() {
        return "OR";
    }

    @Override
    public Object evaluationInternal(Map<? extends Object, ? extends Object> parameters) {
        for (Expression operand : operands) {
            Object val = operand.evaluation(parameters);
            if (val == null)
                return null;
            if (val == UNEVALUATABLE)
                return UNEVALUATABLE;
            if (ExprEvalUtils.obj2bool(val)) {
                return LiteralBoolean.TRUE;
            }
        }
        return LiteralBoolean.FALSE;
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
