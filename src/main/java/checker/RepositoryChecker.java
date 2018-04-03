package checker;


import data.group.Group;
import data.group.GroupsKeeper;
import data.group.SubGroup;
import data.lab.IssuedLab;
import data.mark.LabMark;
import data.Student;
import exceptions.CheckException;
import exceptions.GitException;
import gitAPI.GitShell;
import org.apache.log4j.Logger;
import resources.Controllers.DefaultController;
import resources.Hibernate.Exceptions.DataBaseQueryException;

import java.util.Date;
import java.util.List;

/**
 * Created by kesso on 01.08.17.
 * Проверка репозиториев.
 */
public class RepositoryChecker {
    private static final Logger logger = Logger.getLogger(RepositoryChecker.class);

    /**
     * Check issue labs in all groups.
     *
     * @param groupList
     */
    static public void checkForCommitsInGroups(List<Group> groupList) throws CheckException {
        logger.info("Start check for commits of all the groups.");

        //loop by group
        for (Group currentGroup : groupList) {
            logger.info("Check commits of " + currentGroup.toString());
            RepositoryChecker.checkForCommitsInGroup(currentGroup);
        }
        logger.info("End check for commits in all the groups.");
    }

    private static void checkForCommitsInGroup(Group group) throws CheckException {
        logger.info("Start check for commits of " + group.toString());
        //loop by subgroup
        for (SubGroup currentSubGroup : group.getSubGroupList()) {
            logger.info("Check commits of " + currentSubGroup.toString());
            RepositoryChecker.checkForCommitsInSubgroup(currentSubGroup);
        }
        logger.info("End check for commits in group.");
    }

    private static void checkForCommitsInSubgroup(SubGroup subGroup) throws CheckException {
        logger.info("Start check for commits of " + subGroup.toString());
        //loop by issue lab for subgroup

        for (IssuedLab currentIssuedLab : subGroup.getIssuedLabsList()) {

            RepositoryChecker.checkIssuedLab(currentIssuedLab);
        }

        logger.info("End check for commits is group.");
    }

    static private void checkIssuedLab(IssuedLab issuedLab) throws CheckException {
        logger.info("Check lab number " + issuedLab.toString());
        DefaultController defaultController = new DefaultController();
        Date newDateOfLastRepoCheck = new Date();

        //loop by students who did not pass the lab
        for (Student currentStudent : issuedLab.getStudentControlList()) {
            try {
                RepositoryChecker.checkStudent(currentStudent, issuedLab);
            } catch (CheckException e) {
                throw new CheckException(e);
            }
        }

        //save new date of last lab checking
        logger.info("New date of last repo check : " + newDateOfLastRepoCheck.toString() + ".");
        issuedLab.setDateOfLastRepoCheck(newDateOfLastRepoCheck);
        try {
            defaultController.updateInDataBase(issuedLab);
        } catch (DataBaseQueryException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param student
     * @param issuedLab
     * @return true if deadline should be changed or false if not
     * @throws CheckException
     */
    static private void checkStudent(Student student, IssuedLab issuedLab) throws CheckException {
        LabMark labMark = student.getLabMark(issuedLab.getLabDescription());
        Date commitDate;
        DefaultController defaultController = new DefaultController();

        //Check if there link to github repository
        if (student.getGitURL().equals(""))
            return;

        try {
            commitDate = GitShell.getDateOfTheCommit(student.getGitUserName(),
                    student.getGitRepoName(),
                    issuedLab.getLabDescription().getKeyWord());
        } catch (GitException e) {
            throw new CheckException(e);
        }

        if (commitDate != null) {
            try {
                if (commitDate.before(issuedLab.getDateOfLastRepoCheck())) {
                    labMark.setCoefficient(-2.0);
                    defaultController.updateInDataBase(labMark);
                    logger.info("Student " + student + " cheated");
                } else {
                    double coefficient = issuedLab.getCoefficientOfCommit(commitDate);
                    labMark.setCoefficient(coefficient);
                    defaultController.updateInDataBase(labMark);
                    logger.info("Student " + student + " committed a lab with coefficient " + coefficient);
                }
            } catch (DataBaseQueryException e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Student " + student + " did not commit a lab.");
        }
    }
}