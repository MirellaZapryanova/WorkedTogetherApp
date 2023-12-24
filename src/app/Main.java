package app;


import app.core.Engine;
import app.io.ConsoleOutputWriter;
import app.io.FileIO;
import app.io.FileIOImpl;
import app.io.Writer;
import app.repository.EmployeeRepository;
import app.repository.EmployeeRepositoryImpl;
import app.service.EmployeeService;
import app.service.EmployeeServiceImpl;




public class Main {
    public static void main(String[] args) {

        FileIO fileIO = new FileIOImpl();
        Writer writer = new ConsoleOutputWriter();

        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();
        EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);

        Engine engine = new Engine(fileIO, writer, employeeService);
        engine.run();
    }
}

