package hu.smartcampus.appointmentschedule.service;

import hu.smartcampus.appointmentschedule.domain.EventSchedule;
import hu.smartcampus.appointmentschedule.domain.Day;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppointmentScheduleService {

	private static final String SOLVER_CONFIG = "hu/smartcampus/appointmentschedule/solver/eventScheduleSolverConfig.xml";
	private static final Logger logger = LoggerFactory.getLogger(AppointmentScheduleService.class);

	public static void main(String[] args) throws InterruptedException {
		logger.info("Hello Opta Planner!");

		SolverFactory solverFactory = SolverFactory.createFromXmlResource(SOLVER_CONFIG);
		Solver solver = solverFactory.buildSolver();

		// int currentYear = Calendar.getInstance(budapestTimeZone).get(Calendar.YEAR);
		// int currentWeek = Calendar.getInstance(budapestTimeZone).get(Calendar.WEEK_OF_YEAR);
		int year = 2015;
		int weekOfYear = 12;
		String[] requiredLoginNames = new String[] { "KOLLARL", "KISSSANDORADAM", "MKOSA" };
		String[] skippableLoginNames = new String[] { "VAGNERA" };
		Day[] requiredDays = { Day.Monday, Day.Tuesday, Day.Friday };

		EventSchedule unsolvedEventSchedule = EventSchedule.createEventSchedule(requiredLoginNames,
				skippableLoginNames, year, weekOfYear, requiredDays);
		
		System.out.println("EVENTS");
		unsolvedEventSchedule.getEvents().forEach(System.out::println);
		System.out.println("ENDOFEVENTS");

		System.out.println("USERS");
		unsolvedEventSchedule.getUsers().forEach(System.out::println);
		System.out.println("ENDOFUSERS");

		System.out.println("unsolvedEventSchedule:");
		System.out.println(unsolvedEventSchedule);

		solver.solve(unsolvedEventSchedule);

		EventSchedule solvedEventSchedule = (EventSchedule) solver.getBestSolution();
		System.out.println("solvedEventSchedule:");
		System.out.println(solvedEventSchedule);

		System.out.println("Goodbye Opta Planner!");
	}

}