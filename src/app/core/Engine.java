package app.core;

import app.core.interfaces.Runnable;
import app.io.FileIO;
import app.factories.RecordFactory;
import app.model.Record;
import app.model.Team;
import app.service.EmployeeService;

import app.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

import static app.constants.AppConstants.*;

public class Engine implements Runnable {

    private FileIO fileIO;
    private Writer writer;
    private EmployeeService employeeService;

    public Engine(FileIO fileIO, Writer writer, EmployeeService employeeService) {
        this.fileIO = fileIO;
        this.writer = writer;
        this.employeeService = employeeService;
    }

    @Override
    public void run() {
        //Read all records data from .txt file
        List<Record> records = this.fileIO.read(FILE_PATH)
                .stream()
                .map(RecordFactory::execute)
                .collect(Collectors.toList());

        //Save all employee records into "database"
        this.employeeService.addEmployeeRecords(records);

        //Find all team, couple of employees which r worked under same project and have overlap
        List<Team> teams = this.employeeService.findAllTeamsWithOverlap();

        printResult(teams);
    }

    /**
     * If there is no couple of employees which are worked together under same project
     * will be print message with text "Doesn't exist teams", otherwise
     * will be found and printed the team with best overlap under their joint projects.
     **/

    private void printResult(List<Team> teams){
        if (teams.size() != EMPTY_COLLECTION_SIZE) {
            teams.sort((team1, team2) ->
                    (int) (team2.getTotalDuration() - team1.getTotalDuration()));
            Team bestTeam = teams.get(INDEX_ZERO);
            this.writer.write(
                    String.format(BEST_TEAM_PATTERN,
                            bestTeam.getFirstEmployeeId(),
                            bestTeam.getSecondEmployeeId(),
                            bestTeam.getTotalDuration()));
        } else {
            this.writer.write(NO_TEAMS_MSG);
        }
    }
}

