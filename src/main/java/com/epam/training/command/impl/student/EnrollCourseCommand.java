package com.epam.training.command.impl.student;

import com.epam.training.command.Command;
import com.epam.training.command.CommandResult;
import com.epam.training.command.CommandType;
import com.epam.training.command.RedirectUrlCreator;
import com.epam.training.constant.PagesConstant;
import com.epam.training.entity.Course;
import com.epam.training.entity.User;
import com.epam.training.exception.ServiceException;
import com.epam.training.service.StudentCourseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EnrollCourseCommand implements Command {
    private StudentCourseService service;

    public EnrollCourseCommand(StudentCourseService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession(false);
        User user =(User) session.getAttribute("user");
        long userId = user.getId();
        String courseId = request.getParameter("course_id");
        service.enroll(userId, courseId);

        return CommandResult.redirect(RedirectUrlCreator.create(CommandType.SHOW_MY_COURSES));
    }
}
