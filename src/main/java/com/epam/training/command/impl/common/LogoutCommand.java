package com.epam.training.command.impl.common;

import com.epam.training.constant.PagesConstant;
import com.epam.training.command.Command;
import com.epam.training.command.CommandResult;
import com.epam.training.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.invalidate();

        return CommandResult.forward(PagesConstant.LOGIN);
    }
}
