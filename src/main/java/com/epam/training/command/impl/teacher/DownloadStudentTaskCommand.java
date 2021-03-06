package main.java.com.epam.training.command.impl.teacher;

import main.java.com.epam.training.command.Command;
import main.java.com.epam.training.command.CommandResult;
import main.java.com.epam.training.exception.ServiceException;
import main.java.com.epam.training.service.StudentTaskService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DownloadStudentTaskCommand implements Command {
    StudentTaskService service;

    public DownloadStudentTaskCommand(StudentTaskService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String studentTask = request.getParameter("student_task_id");
        long studentTaskId= Long.valueOf(studentTask);
        String pathToFile = service.downloadStudentTask(studentTaskId);
        downloadFile(pathToFile, response, request);

        return null;
    }

    private void downloadFile(String filePath, HttpServletResponse response, HttpServletRequest request) {
        File downloadFile = new File(filePath);
        configureResponse(downloadFile, filePath,response, request);
        try (FileInputStream inStream = new FileInputStream(downloadFile); OutputStream outStream = response.getOutputStream();) {
           byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureResponse(File downloadFile, String filePath, HttpServletResponse response, HttpServletRequest request){
        ServletContext context = request.getServletContext();
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
    }
}
