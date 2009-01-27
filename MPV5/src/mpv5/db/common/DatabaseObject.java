/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mpv5.db.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import mpv5.globals.Messages;
import mpv5.logging.Log;
import mpv5.ui.panels.DataPanel;
import mpv5.ui.dialogs.Popup;
import mpv5.utils.date.DateConverter;

/**
 * Database Objects reflect a row in a table, and can parse graphical and
 * non-graphical beans to update or create itself to the database
 * @author anti43
 */
public abstract class DatabaseObject {

    /**
     * The db context of this do
     */
    public Context context = new Context(this);
    /**
     * The unique id, or 0 if it is a new do
     */
    public Integer ids = 0;
    /**
     * Should be false after this do's data has been altered in any way
     */
    public boolean isSaved = false;
    /**
     * I strue if this do is marked as Read-Only
     */
    public boolean readonly = false;
    /**
     * Is this do active or not?
     */
    public boolean active = true;
    /**
     * The mandatory name
     */
    public String cname = null;

    /**
     *
     * @return The mandatory name
     */
    public abstract String __getCName();

    /**
     * Set the mandatory name
     * @param name
     */
    public abstract void setCName(String name);

    /**
     *
     * @return The unique id of this do
     */
    public Integer __getIDS() {
        return ids;
    }

    /**
     * Sets the unique id of this do,
     * should never be called manually
     * @param ids
     */
    public void setIDS(int ids) {
        this.ids = ids;
    }

    /**
     *
     * @return A list of all <b>SETTERS</b> in this do child, except the native methods
     */
    public ArrayList<Method> setVars() {
        ArrayList<Method> list = new ArrayList<Method>();
        for (int i = 0; i < this.getClass().getMethods().length; i++) {
            if (this.getClass().getMethods()[i].getName().startsWith("set") &&
                    !this.getClass().getMethods()[i].getName().startsWith("setVars") &&
                    !this.getClass().getMethods()[i].getName().startsWith("setPanelData") &&
                    !this.getClass().getMethods()[i].getName().startsWith("getDbID") &&
                    !this.getClass().getMethods()[i].getName().startsWith("getObject")) {
                list.add(this.getClass().getMethods()[i]);
            }
        }
        return list;
    }

    /**
     *
     * @return A list of all <b>getters</b> in this do child,
     * which match the naming convetion as in
     * <strong>__get*methodname*</strong> (methodname starts with 2 (two) underscores)
     */
    public ArrayList<Method> getVars() {
        ArrayList<Method> list = new ArrayList<Method>();
        for (int i = 0; i < this.getClass().getMethods().length; i++) {
            if (this.getClass().getMethods()[i].getName().startsWith("__get")) {
                list.add(this.getClass().getMethods()[i]);
            }
        }
        return list;
    }

    /**
     * Save this do to db, or update if it has a valid uid already
     * @return
     */
    public boolean save() {

        if (__getCName() != null && __getCName().length() > 0) {
            try {
                if (ids <= 0) {
                    Log.Debug(this, "Inserting new dataset:");
                    ids = QueryHandler.instanceOf().clone(context).insert(collect(), this.__getCName() + Messages.ROW_INSERTED);
                } else {
                    Log.Debug(this, "Updating dataset: " + ids + " within context '" + context + "'");
                    QueryHandler.instanceOf().clone(context).update(collect(), new String[]{"ids", String.valueOf(ids), ""}, this.__getCName() + Messages.ROW_UPDATED);
                }
                return true;
            } catch (Exception e) {
                Log.Debug(this, e);
                e.printStackTrace();
                return false;
            }
        } else {
            Popup.notice(Messages.CNAME_CANNOT_BE_NULL);
            return false;
        }
    }

    /**
     * Reset the data of this do (reload from database and discard changes)
     * @return
     */
    public boolean reset() {
        return false;
    }

    /**
     * Deletes this do from database, can not be reverted!
     * Note: You can reuse this do then as it it would be a new one with prepopulated data
     * @return
     */
    public boolean delete() {
        return false;
    }

    /**
     *
     * @return The tablename/view of this do
     */
    public String getDbID() {
        return context.getDbIdentity();
    }

    /**
     * Collect the data to masked/valid DB String array
     */
    private String[] collect() {

        String left = "";
        String right = "";
        Object tempval;
//        int intval = 0;
        String stringval = "";

        for (int i = 0; i < this.getClass().getMethods().length; i++) {
            if (this.getClass().getMethods()[i].getName().startsWith("__get") && !this.getClass().getMethods()[i].getName().endsWith("IDS")) {
                try {
                    left += this.getClass().getMethods()[i].getName().toLowerCase().substring(5, this.getClass().getMethods()[i].getName().length()) + ",";
                    tempval = this.getClass().getMethods()[i].invoke(this, (Object[]) null);
                    System.out.println(tempval.getClass().getName() + " : " + this.getClass().getMethods()[i].getName() + " ? " + tempval);
                    if (tempval.getClass().isInstance(new String())) {
                        stringval = "(;;2#4#1#1#8#0#;;)" + tempval + "(;;2#4#1#1#8#0#;;)";
                    } else if (tempval.getClass().isInstance(true)) {
                        boolean c = (Boolean) tempval;
                        if (c) {
                            stringval = "1";
                        } else {
                            stringval = "0";
                        }
                    } else if (tempval.getClass().isInstance(new Date())) {
                        stringval = "(;;2#4#1#1#8#0#;;)" + DateConverter.getSQLDateString((Date) tempval) + "(;;2#4#1#1#8#0#;;)";
                    } else if (tempval.getClass().isInstance(new Integer(0))) {
                        stringval = String.valueOf(tempval);
                    } else if (tempval.getClass().isInstance(new Float(0f))) {
                        stringval = String.valueOf(tempval);
                    } else if (tempval.getClass().isInstance(new Double(0d))) {
                        stringval = String.valueOf(tempval);
                    }
                    right += stringval + "(;;,;;)";
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            else if (this.getClass().getMethods()[i].getName().startsWith("is")) {
//                try {
//                    left += this.getClass().getMethods()[i].getName().substring(2, this.getClass().getMethods()[i].getName().length()) + ",";
//                    tempval = this.getClass().getMethods()[i].invoke(this, (Object[]) null);
//                    if (tempval.getClass().isInstance(boolean.class)) {
//                        if (((Boolean) tempval)) {
//                            intval = 1;
//                        }
//                    }
//                    right += "(;;2#4#1#1#8#0#;;)" + intval + "(;;2#4#1#1#8#0#;;)" + "(;;,;;)";
//                } catch (IllegalAccessException ex) {
//                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (IllegalArgumentException ex) {
//                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (InvocationTargetException ex) {
//                    Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }

        return new String[]{left.substring(0, left.length() - 1), right.substring(0, right.length() - 7), ""};
    }

    /**
     * Parses the given DataPanel into this do.
     * Each of the DataPanel's fields wich has a name ending with underscore must match
     * one of the fields in this do child (without underscore)
     * @param source The DataPanel to parse.
     */
    public void getPanelData(DataPanel source) {
        source.collectData();
        ArrayList<Method> vars = setVars();
        for (int i = 0; i < vars.size(); i++) {
            try {
//                System.out.println(vars.get(i).getName().toLowerCase().substring(3, vars.get(i).getName().length()) + "_ : " + data.getClass().getField(vars.get(i).getName().toLowerCase().substring(3, vars.get(i).getName().length()) + "_").getType().getName());
                vars.get(i).invoke(this, source.getClass().getField(vars.get(i).getName().toLowerCase().substring(3, vars.get(i).getName().length()) + "_").get(source));
            } catch (Exception n) {
                System.out.println(n.getCause());
                n.printStackTrace();

            }
        }
    }

    /**
     * Injects this do into a Datapanel
     * Each of the DataPanel's fields wich has a name ending with underscore must match
     * one of the fields in this do child (without underscore)
     * @param target
     */
    public void setPanelData(DataPanel target) {

        ArrayList<Method> vars = getVars();

        for (int i = 0; i < vars.size(); i++) {
            try {
//                System.out.println(vars.get(i).getName());
                target.getClass().getField(vars.get(i).getName().toLowerCase().substring(5, vars.get(i).getName().length()) + "_").set(target, vars.get(i).invoke(this, new Object[0]));
            } catch (Exception n) {
                System.out.println(n.getCause());
                n.printStackTrace();
            }
        }
    }

    /**
     *
     * @return A list containing pairs of VARNAME and their VALUE of this Databaseobject,
     * those which return in getVars(), as two-fields String-Array
     * Example: new String[]{CName, Michael}
     */
    public ArrayList<String[]> getValues() {
        ArrayList<Method> vars = getVars();
        ArrayList<String[]> vals = new ArrayList<String[]>();

        for (int i = 0; i < vars.size(); i++) {
            try {
                vals.add(new String[]{vars.get(i).getName().substring(5, vars.get(i).getName().length()),
                            String.valueOf(vars.get(i).invoke(this, new Object[0]))});

            } catch (Exception n) {
                System.out.println(n.getCause());
                n.printStackTrace();
            }
        }

        return vals;
    }

    /**
     * Searches for a specific dataset
     * @param context The context to search under
     * @param id The id of the object
     * @return A database object with data, or null if none found
     * @throws NodataFoundException 
     */
    public static DatabaseObject getObject(Context context, int id) throws NodataFoundException {

        try {
            Object obj = context.getIdentityClass().newInstance();
            ((DatabaseObject) obj).fetchDataOf(id);
            return (DatabaseObject) obj;
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Fills this do with the data of the given dataset id
     * @param id
     * @throws NodataFoundException 
     */
    public void fetchDataOf(int id) throws NodataFoundException {
        explode(QueryHandler.instanceOf().clone(context).select(id));
    }

    /**
     * Tries to get the id of the last do with the given cname
     * @param cname
     * @return The id, or 0 if none found
     * @throws NodataFoundException
     */
    public Integer getIdOf(String cname) throws NodataFoundException {
            cname=cname.replaceAll("'", "`");
            Object[] data = QueryHandler.instanceOf().clone(context).selectLast("ids", new String[]{"cname", cname, "'"});
            if (data !=null && data.length > 0) {
                return Integer.valueOf(String.valueOf(data[0]));
            } else {
                return 0;
            }
    }

    /**
     * Fills this do with the data of the given dataset cname
     * @param cname
     * @return True if data was found
     * @throws NodataFoundException
     */
    public boolean fetchDataOf(String cname) throws NodataFoundException {
        cname=cname.replaceAll("'", "`");
        Integer id = this.getIdOf(cname);
        ReturnValue data = QueryHandler.instanceOf().clone(context).select(id);
        if (data.getData() != null && data.getData().length > 0) {
            explode(data);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Fills this do with the return value's data
     */
    private void explode(ReturnValue select) {
        ArrayList<Method> vars = setVars();

        for (int i = 0; i < select.getData().length; i++) {
            for (int j = 0; j < select.getData()[i].length; j++) {
                String name = select.getColumnnames()[j].toLowerCase();

                for (int k = 0; k < vars.size(); k++) {
                    if (vars.get(k).getName().toLowerCase().substring(3).equals(name)) {
                        Log.Debug(this, name + " ?? : " + vars.get(k).getName() + " = " + select.getData()[i][j]);
                        try {
                            if (name.startsWith("is") || name.toUpperCase().startsWith("BOOL")) {
                                if (String.valueOf(select.getData()[i][j]).equals("1")) {
                                    vars.get(k).invoke(this, new Object[]{true});
                                } else {
                                    vars.get(k).invoke(this, new Object[]{false});
                                }
                            } else if (name.toUpperCase().startsWith("INT") || name.endsWith("uid") || name.equals("ids")) {
                                vars.get(k).invoke(this, new Object[]{Integer.valueOf(String.valueOf(select.getData()[i][j]))});
                            } else if (name.toUpperCase().startsWith("DATE")) {
                                vars.get(k).invoke(this, new Object[]{DateConverter.getDate(String.valueOf(select.getData()[i][j]))});
                            } else {
                                vars.get(k).invoke(this, new Object[]{String.valueOf(select.getData()[i][j])});
                            }
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(DatabaseObject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
}