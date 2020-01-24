package com.epam.training.command.impl.common;

import com.epam.training.command.CommandType;
import com.epam.training.constant.PagesConstant;
import com.epam.training.command.Command;
import com.epam.training.command.CommandResult;
import com.epam.training.command.RedirectUrlCreator;
import com.epam.training.entity.User;
import com.epam.training.exception.ServiceException;
import com.epam.training.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private UserService service;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Optional<User> user = service.login(login, password);
        HttpSession session = request.getSession();
        user.ifPresent(u -> session.setAttribute("user", u));

        return user.isPresent() ? CommandResult.redirect(RedirectUrlCreator.create(CommandType.SHOW_MAIN_PAGE)) :
                                  CommandResult.forward(PagesConstant.LOGIN);
    }
}