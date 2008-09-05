/*
 *  This file is part of MP by anti43 /GPL.
 *  
 *      MP is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      MP is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *  
 *      You should have received a copy of the GNU General Public License
 *      along with MP.  If not, see <http://www.gnu.org/licenses/>.
 */
package mp4.items;

import mp4.items.handler.NumberFormatHandler;
import java.util.Date;
import javax.swing.JTable;

import mp4.interfaces.Queries;
import mp4.datenbank.verbindung.Query;

import mp4.utils.datum.DateConverter;

/**
 *
 * @author anti
 */
public abstract class Things implements Queries, mp4.datenbank.installation.Tabellen {
//    public Integer id = 0;
    public boolean isSaved = false;
    private Query q;
    public NumberFormatHandler nfh;

    /**
     * 
     * @param query
     */
    public Things(Query query) {
        q = query;
    }

    /**
     * Needed for cloning subclasses
     */
    public Things() {
    }

    public void setQueryHandler(Query query) {
        q = query;
    }

    public Query getQueryHandler() {
        return q;
    }

    /**
     * Disables the item with the given id
     * @param id
     * @return 1 if successfull
     */
    public int delete(Integer id) {
        String[] where = {"id", id.toString(), ""};
        String[] what = {"deleted", "1"};
//        return q.delete(where);
        return q.update(what, where);
    }

    /**
     * Enables a disabled item
     * @param id
     * @return 1 if successfull
     */
    public int unDelete(Integer id) {
        String[] where = {"id", id.toString(), ""};
        String[] what = {"deleted", "0"};
//        return q.delete(where);
        return q.update(what, where);
    }
//    @Override
//    public void finalize() {
//
//        if (!isSaved) {
//            this.save();
//        }
//
//    }
    /**
     * Hides he first column of a table (usually "id")
     * @param table
     * @deprecated Use TableFormat.stripFirst() instead
     */
    @Deprecated
    public void stripFirst(JTable table) {
        try {
            table.getColumn(table.getColumnName(0)).setMinWidth(0);
            table.getColumn(table.getColumnName(0)).setMaxWidth(0);
        } catch (Exception exception) {
        }
    }

    /**
     * 
     * @param set
     * @param value
     * @param id
     * @return rowcount
     */
    public int update(String set, String value, String id) {
        String[] where = {"id", id, ""};
        String[] what = {set, value, "'"};
        return q.update(what, where);
    }

    /**
     * 
     * @param set
     * @param value
     * @param from
     * @param where
     * @return
     */
    public int update(String set, String value, String from, String where) {
        String[] wher = {from, where, "'"};
        String[] what = {set, value, "'"};
        return q.update(what, wher);
    }

    /**
     * 
     * @param what
     * @param from
     * @param where
     * @param id
     * @return
     */
    public String[] selectFirst(String what, String from, String where, boolean id) {
        String hk = "'";
        if (id) {
            hk = "";
        }

        String[] wher = {from, where, hk};

        return q.selectFirst(what, wher);
    }

    /**
     * 
     * @param what
     * @param from
     * @param where
     * @param id
     * @return
     */
    public String[] selectLast(String what, String from, String where, boolean id) {
        String hk = "'";
        if (id) {
            hk = "";
        }

        String[] wher = {from, where, hk};

        return q.selectLast(what, wher);
    }



    /**
     * 
     * @param what
     * @param from
     * @param where
     * @param id
     * @param ghosts
     * @return
     */
    public String[][] select(String what, String from, String where, boolean id, boolean ghosts) {
        String hk = "'";
        String[] wher = null;
        if (id) {
            hk = "";
        }

        if ((from != null) && (where != null)) {
            wher = new String[]{from, where, hk};
        }

        return q.select(what, wher, ghosts);
    }

    public String[][] select(String what, String from, String where, boolean id) {
        String hk = "'";
        String[] wher = null;
        if (id) {
            hk = "";
        }

        if ((from != null) && (where != null)) {
            wher = new String[]{from, where, hk};
        }

        return q.select(what, wher);
    }

    /**
     * Example: "*", "Name", "anti43", "Name", true
     * will return everyone who`s name is like "anti43" sortet by name.
     * eg. anti43, anti43w
     * 
     * @param what
     * @param from
     * @param where
     * @param order
     * @param like
     * @return A multidimensional string-array containing the data found
     */
    public String[][] select(String what, String from, String where, String order, boolean like) {
        String hk = "'";

        String[] wher = {from, where, hk};
        if (from == null) {
            wher = null;
        }

        return q.select(what, wher, order, like, false, false);
    }

    /**
     * 
     * 
     * @param query 
     */
    public void freeQuery(String query) {

        q.freeQuery(query);
    }

    /**
     * 
     * 
     * @param set
     * @param value
     * @return the id of the inserted dataset
     */
    public int insert(String set, String value) {
        String[] str = {set, value, ""};
        return q.insert(str);
    }

    /**
     * Deletes this item!!
     * @param of
     * @return 
     */
//    public Integer id = 0;
//    public Integer getId() {
//        return id;
//    }
//    public void destroy() {
//        this.delete(this.id);
//        this.id = 0;
//    }
    public Integer getNextIndex(String of) {
        return q.getNextIndexOfStringCol(of);
    }

    /**
     * Example: "*", "Name", "anti43", "Name", true
     * will return everyone who`s name is lika "anti43" sortet by name.
     * eg. anti43, anti43w, andre
     * 
     * @param what
     * @param from
     * @param where
     * @param order
     * @param like
     * @param integer 
     * @return A multidimensional string-array containing the data found
     */
    public String[][] select(String what, String from, String where, String order, boolean like, boolean integer, boolean ghosts) {
        String hk = "'";

        String[] wher = {from, where, hk};
        if (from == null) {
            wher = null;
        }

        return q.select(what, wher, order, like, integer, ghosts);
    }

    /**
     * Example: "*", "Name", "anti43", "Name", true
     * will return everyone who`s name is like "anti43" sortet by name.
     * eg. anti43, anti43w
     * 
     * @param what
     * @param from
     * @param where
     * @param order
     * @param like
     * @param additionalTable 
     * @param addTableKey 
     * @return A multidimensional string-array containing the data found
     */
    public String[][] select(String what, String from, String where, String order, boolean like, String additionalTable, String addTableKey) {
        String hk = "'";

        String[] wher = {from, where, hk};
        if (from == null) {
            wher = null;
        }

        return q.select(what, wher, additionalTable, addTableKey, order, like);
    }

    public NumberFormatHandler getNfh() {
        return nfh;
    }


}
