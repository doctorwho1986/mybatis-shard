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
 * (created at 2011-1-20)
 */
package org.lysu.shard.parser.ast.expression.arithmeic;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.lysu.shard.parser.ast.expression.Expression;
import org.lysu.shard.parser.visitor.SQLASTVisitor;

/**
 * <code>higherExpr '-' higherExpr</code>
 * 
 * @author <a href="mailto:shuo.qius@alibaba-inc.com">QIU Shuo</a>
 */
public class ArithmeticSubtractExpression extends ArithmeticBinaryOperatorExpression {
    public ArithmeticSubtractExpression(Expression leftOprand, Expression rightOprand) {
        super(leftOprand, rightOprand, PRECEDENCE_ARITHMETIC_TERM_OP);
    }

    @Override
    public String getOperator() {
        return "-";
    }

    @Override
    public Number calculate(Integer integer1, Integer integer2) {
        if (integer1 == null || integer2 == null)
            return null;
        int i1 = integer1.intValue();
        int i2 = integer2.intValue();
        if (i2 == 0)
            return integer1;
        if (i1 == 0) {
            if (i2 == Integer.MIN_VALUE) {
                return new Long(-(long) i2);
            }
            return new Integer(-i2);
        }
        if (i1 >= 0 && i2 >= 0 || i1 <= 0 && i2 <= 0) {
            return new Integer(i1 - i2);
        }
        int rst = i1 - i2;
        if (i1 > 0 && rst < i1 || i1 < 0 && rst > i1) {
            return new Long((long) i1 - (long) i2);
        }
        return new Integer(rst);
    }

    @Override
    public Number calculate(Long long1, Long long2) {
        if (long1 == null || long1 == null)
            return null;
        long l1 = long1.longValue();
        long l2 = long1.longValue();
        if (l2 == 0L)
            return long1;
        if (l1 == 0L) {
            if (l2 == Long.MIN_VALUE) {
                return BigInteger.valueOf(l2).negate();
            }
            return new Long(-l2);
        }
        if (l1 >= 0L && l2 >= 0L || l1 <= 0L && l2 <= 0L) {
            return new Long(l1 - l2);
        }
        long rst = l1 - l2;
        if (l1 > 0L && rst < l1 || l1 < 00L && rst > l1) {
            BigInteger bi1 = BigInteger.valueOf(l1);
            BigInteger bi2 = BigInteger.valueOf(l2);
            return bi1.subtract(bi2);
        }
        return new Long(rst);
    }

    @Override
    public Number calculate(BigInteger bigint1, BigInteger bigint2) {
        if (bigint1 == null || bigint2 == null)
            return null;
        return bigint1.subtract(bigint2);
    }

    @Override
    public Number calculate(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
        if (bigDecimal1 == null || bigDecimal2 == null)
            return null;
        return bigDecimal1.subtract(bigDecimal2);
    }

    @Override
    public void accept(SQLASTVisitor visitor) {
        visitor.visit(this);
    }
}
