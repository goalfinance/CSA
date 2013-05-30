/*
 * File HQLCommand.java
 * Created on 2004-5-21
 *
 */
package cistern.dao.ql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.MappingException;
import org.hibernate.type.Type;
import org.hibernate.type.TypeResolver;

/**
 * @description HibernateQL查询语句类
 * @author seabao
 * @project cistern
 * @date 2004-5-21
 */
public class HQLQuery {
    /**
     * select子句
     */
    private String selectClause;

    /**
     * from子句
     */
    private String fromClause;

    /**
     * where子句
     */
    private String whereClause;

    /**
     * group子句
     */
    private String groupClause;

    /**
     * 排序子句
     */
    private String orderClause;

    private List<Object> parameters = new ArrayList<Object>();

    private List<Type> types = new ArrayList<Type>();

    public HQLQuery() {
    }

    public String getNonSelectClause() {
        StringBuffer clause = new StringBuffer();
        
        if (fromClause != null && fromClause.equals("") == false) {
            clause.append("from ");
            clause.append(fromClause);
            clause.append(" ");
        }

        if (whereClause != null && whereClause.equals("") == false) {
            clause.append("where ");
            clause.append(whereClause);
            clause.append(" ");
        }

        if (groupClause != null && groupClause.equals("") == false) {
            clause.append("group by ");
            clause.append(groupClause);
            clause.append(" ");
        }

        if (orderClause != null && orderClause.equals("") == false) {
            clause.append("order by ");
            clause.append(orderClause);
            clause.append(" ");
        }

        return clause.toString();
    }

    public String getClause() {
        StringBuffer clause = new StringBuffer();
        if (selectClause != null && selectClause.equals("") == false) {
            clause.append("select ");
            clause.append(selectClause);
            clause.append(" ");
        }

        clause.append(getNonSelectClause());
        
        return clause.toString();
    }

    public void addParameters(List<Object> parameters) throws MappingException {
        this.parameters.addAll(parameters);
        for (Iterator<Object> it = parameters.iterator(); it.hasNext();) {
            this.types.add(new TypeResolver().heuristicType(it.next().getClass().getName()));
        }
    }

    public void addParameters(List<Object> parameters, List<Type> types) {
        this.parameters.addAll(parameters);
        this.types.addAll(types);
    }

    public void setParameters(List<Object> parameters) throws MappingException {
        this.parameters = parameters;
        this.types.clear();
        for (Iterator<Object> it = this.parameters.iterator(); it.hasNext();) {
            this.types.add(new TypeResolver().heuristicType(it.next().getClass().getName()));
        }
    }

    public void setParameters(List<Object> parameters, List<Type> types) {
        this.parameters = parameters;
        this.types = types;
    }

    public List<Object> getParameters() {
        return parameters;
    }
    
    public void addParameter(Object value) throws MappingException {
        this.parameters.add(value);
        this.types.add(new TypeResolver().heuristicType(value.getClass().getName()));
    }

    public void addParameter(Object value, Type type) {
        this.parameters.add(value);
        this.types.add(type);
    }

    public Object[] getHQLArgs() {
        return parameters.toArray();
    }

    public Type[] getHQLTypes() {
        return (Type[]) types.toArray(new Type[types.size()]);
    }

    public void clearCommand() {
        selectClause = null;
        fromClause = null;
        whereClause = null;
        groupClause = null;
        orderClause = null;
        parameters.clear();
        types.clear();
    }

    public String getFromClause() {
        return fromClause;
    }

    public String getGroupClause() {
        return groupClause;
    }

    public String getOrderClause() {
        return orderClause;
    }

    public String getSelectClause() {
        return selectClause;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setFromClause(String string) {
        fromClause = string;
    }

    public void setGroupClause(String string) {
        groupClause = string;
    }

    public void setOrderClause(String string) {
        orderClause = string;
    }

    public void setSelectClause(String string) {
        selectClause = string;
    }

    public void setWhereClause(String string) {
        whereClause = string;
    }

}