package ru.astondevs.projects.hypersort.cli.dto;

import java.util.List;


public class Response {
    private final ResponseCode code;
    private final Frame frame;
    private final List<String> eventPayload;

    public Response(Builder builder) {
        this.code = builder.code;
        this.frame = builder.frame;
        this.eventPayload = builder.eventPayload;
    }

    public ResponseCode getCode() {
        return code;
    }

    public Frame getFrame() {
        return frame;
    }

    public List<String> getEventPayload() {
        return eventPayload;
    }

    public static class Builder {
        private ResponseCode code = null;
        private Frame frame = null;
        private List<String> eventPayload = null;

        public Builder setCode(ResponseCode code) {
            this.code = code;
            return this;
        }

        public Builder setFrame(Frame frame) {
            this.frame = frame;
            return this;
        }

        public Builder setEventPayload(List<String> eventPayload) {
            this.eventPayload = eventPayload;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
