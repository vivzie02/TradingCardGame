package at.fhtw.swen1.mcg.httpserver.server;

public interface Service {
    Response handleRequest(Request request);
}
