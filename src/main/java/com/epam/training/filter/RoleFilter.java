package main.java.com.epam.training.filter;

import main.java.com.epam.training.constant.CommandType;
import main.java.com.epam.training.constant.PagesConstant;
import main.java.com.epam.training.entity.User;
import main.java.com.epam.training.entity.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class RoleFilter implements Filter {
    private final Map<String, List<UserRole>> commandsUsers = new HashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String command = request.getParameter("command");
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            UserRole userRole = user.getRole();
            List<UserRole> roles = commandsUsers.get(command);
            if(roles!=null && !roles.contains(userRole)){
                String error = "You don't have rights for this operation";
                request.setAttribute("error", error);
                RequestDispatcher dispatcher = request.getRequestDispatcher(PagesConstant.LOGIN);
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<UserRole> allUsers = Arrays.asList(UserRole.STUDENT, UserRole.ADMIN, UserRole.TEACHER);
        List<UserRole> adminTeacher = Arrays.asList(UserRole.ADMIN, UserRole.TEACHER);
        List<UserRole> student = Arrays.asList(UserRole.STUDENT);
        List<UserRole> teacher = Arrays.asList(UserRole.TEACHER);
        List<UserRole> admin = Arrays.asList(UserRole.ADMIN);
        commandsUsers.put(CommandType.SHOW_MAIN_PAGE, allUsers);
        commandsUsers.put(CommandType.LOGIN, allUsers);
        commandsUsers.put(CommandType.LOG_OUT, allUsers);
        commandsUsers.put(CommandType.ENROLL_COURSE, student);
        commandsUsers.put(CommandType.UNENROLL_COURSE, student);
        commandsUsers.put(CommandType.SHOW_ADD_COURSE_PAGE, admin);
        commandsUsers.put(CommandType.SHOW_EDIT_COURSE_PAGE, admin);
        commandsUsers.put(CommandType.EDIT_COURSE, admin);
        commandsUsers.put(CommandType.ADD_COURSE, admin);
        commandsUsers.put(CommandType.SHOW_MANAGE_TASKS_PAGE, adminTeacher);
        commandsUsers.put(CommandType.SHOW_ADD_TASK_PAGE, adminTeacher);
        commandsUsers.put(CommandType.SHOW_TEACHER_COURSES, teacher);
        commandsUsers.put(CommandType.ESTIMATE_TASK, teacher);
        commandsUsers.put(CommandType.LOCK_TASK, adminTeacher);
        commandsUsers.put(CommandType.ADD_TASK, adminTeacher);
        commandsUsers.put(CommandType.EDIT_TASK, adminTeacher);
        commandsUsers.put(CommandType.LOCK_COURSE, admin);
        commandsUsers.put(CommandType.UPLOAD_STUDENT_TASK, student);
        commandsUsers.put(CommandType.DOWNLOAD_STUDENT_TASK, teacher);
        commandsUsers.put(CommandType.SHOW_COURSE_STUDENTS, adminTeacher);
        commandsUsers.put(CommandType.SHOW_STUDENTS_HAVE_TASK, teacher);
        commandsUsers.put(CommandType.SHOW_MY_COURSES, student);
        commandsUsers.put(CommandType.SHOW_TEACHER_TASKS, teacher);
        commandsUsers.put(CommandType.SHOW_STUDENT_TASKS, adminTeacher);
        commandsUsers.put(CommandType.SHOW_COURSE_TASKS, student);
        commandsUsers.put(CommandType.SHOW_MY_MARKS, student);
        commandsUsers.put(CommandType.ADD_STUDENT_TASK, student);
    }
}
