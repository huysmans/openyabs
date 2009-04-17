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
package mpv5.items.div;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import mpv5.db.common.Context;
import mpv5.db.common.DatabaseObject;
import mpv5.db.common.NodataFoundException;
import mpv5.db.common.QueryHandler;
import mpv5.items.main.Item;
import mpv5.logging.Log;
import mpv5.ui.frames.MPV5View;
import mpv5.utils.date.DateConverter;
import mpv5.utils.date.vTimeframe;

/**
 *
 * @author anti
 */
public class Schedule extends DatabaseObject {


    private int usersids = 4343;
    private int itemsids;
    private Date nextdate = new Date();
    private int intervalmonth = 1;
    private Item item;

    public Schedule() {
        context.setDbIdentity(Context.IDENTITY_SCHEDULE);
        context.setIdentityClass(this.getClass());
    }

    /**
     *
     * @return A vTimeframe object with this schedules day set
     */
    public vTimeframe getDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(__getNextdate());
        c.add(Calendar.DAY_OF_MONTH, 1);
        vTimeframe t = new vTimeframe(__getNextdate(), new Date(c.getTimeInMillis() - 1));
        return t;
    }

    /**
     *
     * @return The item assigned to this schedule object
     * @throws mpv5.db.common.NodataFoundException
     */
    public Item getItem() throws NodataFoundException {
        if (item == null) {
            item = (Item) DatabaseObject.getObject(Context.getItems(), __getItemsids());
        }
        return item;
    }

    /**
     *
     * @param date
     * @return
     */
    public static ArrayList<Schedule> getEvents(vTimeframe date) {
        Object[][] data = QueryHandler.instanceOf().clone(Context.getSchedule()).selectBetween("ids", null, "nextdate", date);
        ArrayList<Schedule> l = new ArrayList<Schedule>();
        for (int i = 0; i < data.length; i++) {
            Object[] objects = data[i];
            try {
                Schedule s = (Schedule) DatabaseObject.getObject(Context.getSchedule(), Integer.valueOf(objects[0].toString()));
                l.add(s);
            } catch (NodataFoundException ex) {
                Log.Debug(ex);
            }
        }
        return l;
    }


    /**
     *
     * @param day
     * @return
     */
    public static ArrayList<Schedule> getEvents(Date day) {
        return getEvents(new vTimeframe(day,new Date( DateConverter.addDay(day).getTime()-1)));
    }

    @Override
    public JComponent getView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the usersids
     */
    public int __getUsersids() {
        return usersids;
    }

    /**
     * @param usersids the usersids to set
     */
    public void setUsersids(int usersids) {
        this.usersids = usersids;
    }

    /**
     * @return the itemsids
     */
    public int __getItemsids() {
        return itemsids;
    }

    /**
     * @param itemsids the itemsids to set
     */
    public void setItemsids(int itemsids) {
        this.itemsids = itemsids;
    }

    /**
     * @return the nextdate
     */
    public Date __getNextdate() {
        return nextdate;
    }

    /**
     * @param nextdate the nextdate to set
     */
    public void setNextdate(Date nextdate) {
        this.nextdate = nextdate;
    }

    /**
     * @return the intervalmonth
     */
    public int __getIntervalmonth() {
        return intervalmonth;
    }

    /**
     * @param intervalmonth the intervalmonth to set
     */
    public void setIntervalmonth(int intervalmonth) {
        this.intervalmonth = intervalmonth;
    }
}
