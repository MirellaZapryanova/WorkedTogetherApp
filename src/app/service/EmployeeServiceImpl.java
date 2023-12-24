package app.service;

import app.factories.TeamFactory;
import app.model.Record;
import app.model.Team;
import app.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static app.constants.AppConstants.*;

public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /** Method which save all records to the database using EmployeeRepository */
    @Override
    public void addEmployeeRecords(List<Record> records) {
        this.employeeRepository.saveAll(records);
    }

    /** Method which finding all teams,
     * couples which have overlap and save them into List<Team> */
    @Override
    public List<Team> findAllTeamsWithOverlap() {
        List<Record> allRecords = this.employeeRepository.getAllRecords();

        List<Team> teams = new ArrayList<>();
        for (int i = INDEX_ZERO; i < allRecords.size() - ONE; i++) {
            for (int j = i + ONE; j < allRecords.size(); j++) {
                Record firstEmployee = allRecords.get(i);
                Record secondEmployee = allRecords.get(j);

                if (firstEmployee.getProjectId() == secondEmployee.getProjectId()
                        && hasOverlap(firstEmployee, secondEmployee)) {
                    long overlapDays = calculateOverlap(firstEmployee, secondEmployee);

                    if (overlapDays > DEFAULT_OVERLAP_ZERO_DAYS) {
                        updateTeamCollection(teams, firstEmployee, secondEmployee, overlapDays);
                    }
                }
            }
        }
        return teams;
    }

    /** Method which calculating the total overlap and returning it */
    private long calculateOverlap(Record firstEmployee, Record secondEmployee) {
        LocalDate periodStartDate =
                firstEmployee.getDateFrom().isBefore(secondEmployee.getDateFrom()) ?
                        secondEmployee.getDateFrom() : firstEmployee.getDateFrom();

        LocalDate periodEndDate =
                firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo()) ?
                        firstEmployee.getDateTo() : secondEmployee.getDateTo();

        //This method work good and when we have involved leap years too
        //from 2019-01-01 to 2019-01-15 will return 14days in result not 15, which will accept for correct
        return Math.abs(ChronoUnit.DAYS.between(periodStartDate, periodEndDate));
    }

    /** hasOverlap method returning if two employees have overlap */
    private boolean hasOverlap(Record firstEmployee, Record secondEmployee) {
        //have overlap if (startA <= endB) and (endA >= startB)
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo())
                || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo()))
                && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom())
                || firstEmployee.getDateTo().isEqual(secondEmployee.getDateFrom()));
    }

    /** method check and returning if the current team is already present in team collection
     * (worked together under others projects) */
    private boolean isTeamPresent(Team team, long firstEmployeeId, long secondEmployeeId) {
        return ( team.getFirstEmployeeId() == firstEmployeeId
                && team.getSecondEmployeeId() == secondEmployeeId )
                || ( team.getFirstEmployeeId() == secondEmployeeId
                && team.getSecondEmployeeId() == firstEmployeeId );
    }

    /** If the team is already present, it's total overlap duration will be updated with the new value,
     * otherwise will be created and add new team with the current data */
    private void updateTeamCollection(List<Team> teams, Record firstEmployee, Record secondEmployee, long overlapDays) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        //If the team is present -> update team's total overlap
        teams.forEach(team -> {
            if (isTeamPresent(team, firstEmployee.getEmployeeId(), secondEmployee.getEmployeeId())) {
                team.addOverlapDuration(overlapDays);
                isPresent.set(true);
            }
        });
        //If the team isn't present -> create and add new team with the current data
        if (!isPresent.get()) {
            Team newTeam = TeamFactory.execute(
                    firstEmployee.getEmployeeId(),
                    secondEmployee.getEmployeeId(),
                    overlapDays);
            teams.add(newTeam);
        }
    }
}
