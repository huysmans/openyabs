package mpv5.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import java.util.logging.Level;
import java.util.logging.Logger;
import mpv5.db.common.Context;
import mpv5.db.common.NodataFoundException;
import mpv5.db.common.QueryCriteria;
import mpv5.db.common.QueryHandler;
import mpv5.db.common.ReturnValue;
import mpv5.db.objects.HistoryItem;
import mpv5.db.objects.Item;
import mpv5.db.objects.Schedule;
import mpv5.globals.Messages;
import mpv5.logging.Log;
import mpv5.ui.dialogs.Popup;
import mpv5.ui.frames.MPView;
import mpv5.usermanagement.MPSecurityManager;
import mpv5.utils.arrays.ArrayUtilities;
import mpv5.utils.date.DateConverter;
import mpv5.utils.date.vTimeframe;

/**
 * This class handles the scheduled events
 */
public class Scheduler extends Thread {

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignore) {
            }
            checkForOverdueEvents();
            checkForCreateBillEvents();
            try {
                Thread.sleep(600000);
            } catch (InterruptedException ignore) {
            }
        }
    }

    @SuppressWarnings("static-access")
    private void checkForCreateBillEvents() {
        ArrayList<Schedule> data = Schedule.getEvents(new vTimeframe(DateConverter.addYears(new Date(), -2), new Date()));
        List<Item> list = new Vector<Item>();
        for (int i = 0; i < data.size(); i++) {
            Schedule schedule = data.get(i);
            if (!schedule.__getIsdone()) {
                try {
                    Item item = schedule.getItem();
                    item.setIDS(-1);
                    item.setDateadded(new Date());
                    item.setDatetodo(new Date());
                    item.setDateend(new Date());
                    item.setIntreminders(0);
                    item.setIntstatus(item.STATUS_IN_PROGRESS);
                    item.setDescription(item.__getDescription() +
                            "\n" +
                            Messages.SCHEDULE_GENERATED +
                            "\n" +
                            item.__getCName());
                    item.save();

                    Date olddate = schedule.__getNextdate();
                    schedule.setNextdate(DateConverter.addMonths(schedule.__getNextdate(), schedule.__getIntervalmonth()));
                    if (schedule.__getNextdate().before(schedule.__getStopdate())) {
                        Schedule s2 = (Schedule) schedule.clone();
                        s2.setIsdone(false);
                        s2.setIDS(-1);
                        s2.save(true);
                        schedule.setNextdate(olddate);
                        schedule.setIsdone(true);
                        schedule.save(true);
                    } else {
                        schedule.setNextdate(olddate);
                        schedule.setIsdone(true);
                        schedule.save(true);
                        MPView.addMessage(Messages.SCHEDULE_ITEM_REMOVED + " " + schedule);
                    }
                    list.add(item);
                } catch (NodataFoundException ex) {
                    Log.Debug(ex);
                }
            }
        }

        if (list.size() > 0) {
            if (Popup.Y_N_dialog(list.size() + " " + Messages.SCHEDULE_NEW_ITEMS)) {
                for (int i = 0; i < list.size(); i++) {
                    Item item = list.get(i);
                    MPView.identifierView.addTab(item);
                }
            }
        }
    }

    private void checkForOverdueEvents() {

        List<Item> warnings = new Vector<Item>();
        List<Item> alerts = new Vector<Item>();
        try {
            if (MPView.getUser().getProperties().hasProperty("bills.warn.days")) {
                Integer warn = Integer.valueOf(MPView.getUser().getProperties().getProperty("bills.warn.days"));
                String sql = "SELECT ids FROM items WHERE dateadded <= '" +
                        DateConverter.getSQLDateString(DateConverter.addDays(new Date(), warn * -1)) +
                        "' AND intstatus <> " + Item.STATUS_PAID;
                ReturnValue data = QueryHandler.getConnection().freeSelectQuery(sql, MPSecurityManager.VIEW, null);

                if (data.hasData()) {
                    Object[][] d = data.getData();
                    for (int i = 0; i < d.length; i++) {
                        int id = Integer.valueOf(d[i][0].toString());
                        try {
                            Item it = (Item) Item.getObject(Context.getItems(), id);
                            warnings.add(it);
                        } catch (NodataFoundException ex) {
                            Log.Debug(ex);
                        }
                    }
                }
            } else {
                Log.Debug(this, "No warn treshold for bills defined.");
            }
        } catch (Exception numberFormatException) {
            Log.Debug(numberFormatException);
        }

        if (MPView.getUser().getProperties().hasProperty("bills.alert.days")) {
            Integer alert = Integer.valueOf(MPView.getUser().getProperties().getProperty("bills.alert.days"));
            String sql = "SELECT ids FROM items WHERE dateadded <= '" +
                    DateConverter.getSQLDateString(DateConverter.addDays(new Date(), alert * -1)) +
                    "' AND intstatus <> " + Item.STATUS_PAID;
            ReturnValue data = QueryHandler.getConnection().freeSelectQuery(sql, MPSecurityManager.VIEW, null);

            if (data.hasData()) {
                Object[][] d = data.getData();
                for (int i = 0; i < d.length; i++) {
                    int id = Integer.valueOf(d[i][0].toString());
                    try {
                        Item it = (Item) Item.getObject(Context.getItems(), id);
                        alerts.add(it);
                    } catch (NodataFoundException ex) {
                        Log.Debug(ex);
                    }
                }
            }
        } else {
            Log.Debug(this, "No alert treshold for bills defined.");
        }

        for (Item i : alerts) {//Remove dupes
            if (warnings.contains(i)) {
                warnings.remove(i);
            }
        }

        if (!warnings.isEmpty()) {
            if (Popup.Y_N_dialog(warnings.size() + " " + Messages.SCHEDULE_NEW_WARNINGS, Messages.WARNING)) {
                for (int i = 0; i < warnings.size(); i++) {
                    Item item = warnings.get(i);
                    MPView.identifierView.addTab(item);
                }
            }
        }

        if (!alerts.isEmpty()) {
            if (Popup.Y_N_dialog(alerts.size() + " " + Messages.SCHEDULE_NEW_ALERTS, Messages.WARNING)) {
                for (int i = 0; i < alerts.size(); i++) {
                    Item item = alerts.get(i);
                    MPView.identifierView.addTab(item);
                }
            }
        }
    }
}