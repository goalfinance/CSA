package cistern.dao.ql.impl;

/**
 * <p>Title: Cistern</p>
 * <p>Description: UI控件类</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Intensoft Corp</p>
 * @author seabao
 * @version 1.01
 * @date 2003-9-18
 */
public class MacroVariableInterpreter {
    private static MacroVariableFormatter defaultMacroVariableFormatter = new DefaultMacroVariableFormatter();

    private static int searchMetaString(String str, String metaStr, int fromIndex, StringBuffer target) {
        int l = metaStr.length();

        do {
            int index = str.indexOf(metaStr, fromIndex);
            if (index >= 0) {
                if (str.indexOf(metaStr, index + l) == (index + l)) {
                    // 连续引用表示宏扩展
                    if (target != null) {
                        target.append(str.substring(fromIndex, index + l));
                    }

                    fromIndex = index + l * 2;
                    continue;
                }

                if (target != null) {
                    target.append(str.substring(fromIndex, index));
                }
            } else {
                if (target != null) {
                    target.append(str.substring(fromIndex));
                }
            }

            return index;
        } while (true);
    }

    public static boolean hasMacroVariables(String expr, String prefix) {
        return (searchMetaString(expr, prefix, 0, null) >= 0);
    }

    public static String interpret(String expr, String prefix, String suffix, MacroVariableContainer mvc, MacroVariableFormatter formatter) {
        StringBuffer sb = new StringBuffer();
        int index = 0;
        while ((index = searchMetaString(expr, prefix, index, sb)) >= 0) {
            String path;
            String pattern = null;
            int s = expr.indexOf("&ps&", index + prefix.length());
            if (s < 0) {
                s = expr.indexOf("^ps^", index + prefix.length());
            }

            int s1 = expr.indexOf(suffix, index + prefix.length());

            int eindex;
            if (s >= 0 && s < s1) {
                path = expr.substring(index + prefix.length(), s);
                int e = expr.indexOf("&pe&", s + 4);
                if (e < 0) {
                    e = expr.indexOf("^pe^", s + 4);

                    if (e < 0) {
                        throw new RuntimeException("Syntax error, expression=[" + expr + "]!");
                    }
                }

                pattern = expr.substring(s + 4, e);
                eindex = expr.indexOf(suffix, e + 4);
            } else {
                eindex = expr.indexOf(suffix, index + prefix.length());
                path = expr.substring(index + prefix.length(), eindex).trim();
            }

            if (eindex < 0) {
                throw new RuntimeException("Syntax error, expression=[" + expr + "]!");
            }

            s = path.indexOf("?");
            String thenPart = null;
            String elsePart = null;
            if (s > 0) {
                String thenElsePart = path.substring(s + 1);
                path = path.substring(0, s);
                int e = thenElsePart.indexOf(":");
                if (e > 0) {
                    thenPart = thenElsePart.substring(0, e);
                    elsePart = thenElsePart.substring(e + 1);
                } else {
                    thenPart = thenElsePart;
                }
            }

            Object value = mvc.getMacroVariable(path);
            String svalue;
            svalue = formatter.format(pattern, value);

            if (thenPart != null) {
                if (svalue.trim().equals("") == false) {
                    svalue = thenPart;
                } else {
                    if (elsePart != null) {
                        svalue = elsePart;
                    } else {
                        svalue = "";
                    }
                }
            }

            sb.append(svalue);
            index = eindex + suffix.length();
        }

        return sb.toString();
    }

    public static String interpret(String expr, String prefix, String suffix, MacroVariableContainer mvc) {
        return MacroVariableInterpreter.interpret(expr, prefix, suffix, mvc, defaultMacroVariableFormatter);
    }
}