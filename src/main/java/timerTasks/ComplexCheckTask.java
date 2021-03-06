package timerTasks;

/*
Check repositories every hour and timetable at 00.00 after checking repositories.
 */

import checker.RepositoryChecker;
import checker.ScheduleChecker;

import java.util.*;

import dao.DataBaseCore;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import logics.GroupLogic;

public class ComplexCheckTask implements Job {

    private static final Logger logger = Logger.getLogger(ComplexCheckTask.class);
    private ScheduleChecker scheduleChecker;
    private RepositoryChecker repositoryChecker;
    private Calendar calendar;

    public ComplexCheckTask() {
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Minsk"));
        scheduleChecker = new ScheduleChecker();
        repositoryChecker = new RepositoryChecker();
    }

    /*
        Temporary container for holidays.
     */
    private HashMap<Integer, List<Integer>> holidays = new HashMap<Integer, List<Integer>>(){{
        put(11, Arrays.asList(7));
        put(12, Arrays.asList(25));
        put(1, Arrays.asList(1, 7));
        put(3, Arrays.asList(8));
        put(4, Arrays.asList(8,17));
        put(5, Arrays.asList(1, 9));
        put(7, Arrays.asList(3));
    }};

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("Start scheduled task.");
            GroupLogic groupLogic = new GroupLogic();
            DataBaseCore dataBaseCore = DataBaseCore.getInstance();

            repositoryChecker.checkForCommitsInGroups(groupLogic.getAll());

            if(isMidnight() && !isSunday() && !isHoliday()) {
                logger.info("Check student`s timetable at midnight.");

                scheduleChecker.groupScheduleCheck(groupLogic.getAll());
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    private boolean isSunday(){
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return (day == 1);
    }

    private boolean isHoliday(){
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if(holidays.containsKey(month)){
            if(holidays.get(month).contains(day));
                return true;
        }
        return false;
    }

    private boolean isMidnight(){
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return (hour == 0 && minute == 0);
    }
}
