package main.java.com.epam.training.command.impl.common;

import main.java.com.epam.training.command.Command;
import main.java.com.epam.training.command.CommandResult;
import main.java.com.epam.training.constant.PagesConstant;
import main.java.com.epam.training.entity.StudentTask;
import main.java.com.epam.training.entity.Task;
import main.java.com.epam.training.exception.ServiceException;
import main.java.com.epam.training.service.StudentTaskService;
import main.java.com.epam.training.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowCourseTasksCommand implements Command {
    private TaskService service;

    public ShowCourseTasksCommand(TaskService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String course = request.getParameter("course_id");
        long courseId= Long.valueOf(course);
        List<Task> tasks = service.showTasksCourse(courseId);
        request.setAttribute("tasks",tasks);
        request.setAttribute("courseId", courseId);

        return CommandResult.forward(PagesConstant.COURSE_TASKS);
    }
}
