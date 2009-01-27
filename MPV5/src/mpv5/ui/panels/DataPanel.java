/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mpv5.ui.panels;

import mpv5.db.common.DatabaseObject;

/**
 *
 * @author Andreas
 */
public interface DataPanel {

    /**
     * Reset the view
     */
    public void reset();
    /**
     * Collect the view data
     */
    public void collectData();
    /**
     * Get the database objec twhich owns the view
     * @return
     */
    public DatabaseObject getDataOwner();
    /**
     * Assign this view to a database object
     * @param object
     */
    public void setDataOwner(DatabaseObject object);
    /**
     * Reload the view from database
     */
    public void refresh();
}