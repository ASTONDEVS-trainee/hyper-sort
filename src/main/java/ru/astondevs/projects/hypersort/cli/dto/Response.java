package ru.astondevs.projects.hypersort.cli.dto;


public class Response {
    private final ResponseCode code;
    private final Frame frame;

    public Response(ResponseCode code) {
        this.code = code;
        this.frame = null;
    }

    public Response(ResponseCode code, Frame frame) {
        this.code = code;
        this.frame = frame;
    }

    public ResponseCode getCode() {
        return code;
    }

    public Frame getFrame() {
        return frame;
    }
}
