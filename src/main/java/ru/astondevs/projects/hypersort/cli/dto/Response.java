package ru.astondevs.projects.hypersort.cli.dto;


public class Response {
    private final ResponseCode code;
    private final Frame frame;

    public Response(Builder builder) {
        this.code = builder.code;
        this.frame = builder.frame;
    }

    public ResponseCode getCode() {
        return code;
    }

    public Frame getFrame() {
        return frame;
    }

    public static class Builder {
        private ResponseCode code = null;
        private Frame frame = null;

        public Builder setCode(ResponseCode code) {
            this.code = code;
            return this;
        }

        public Builder setFrame(Frame frame) {
            this.frame = frame;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }
}
