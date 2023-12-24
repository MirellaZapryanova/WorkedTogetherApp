package app.service;

import app.model.Record;
import app.model.Team;

import java.util.List;

public interface EmployeeService {
    void addEmployeeRecords(List<Record> records);

    List<Team> findAllTeamsWithOverlap();
}
