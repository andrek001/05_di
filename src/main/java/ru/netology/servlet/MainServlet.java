package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
  private PostController controller;

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext("ru.netology");
    controller = context.getBean(PostController.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      final var path = req.getRequestURI();
    if (path.equals("/api/posts")) {
      controller.all(resp);
      return;
    }
    if (path.matches("/api/posts/\\d+")) {
      // easy way
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
      controller.getById(id, resp);
      return;
    }}

    catch (Exception e) {

        e.printStackTrace();
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      }
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (path.equals("/api/posts")) {
        controller.save(req.getReader(), resp);
        return;
      }
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    // если деплоились в root context, то достаточно этого
    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();
      if (hasId(path)) {
        // easy way
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/")+1));
        controller.removeById(id, resp);
        return;
      }

      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
  private boolean hasId(String path){
    return path.matches("/api/posts/\\d+");
  }
}

