package ru.astondevs.projects.hypersort.cli.dto;

import ru.astondevs.projects.hypersort.service.ServiceName;

import java.util.List;


public class Event {
    private final Selector selector;
    private final Switch switchName;
    private final ServiceName serviceName;
    private final List<String> payload;

    public Event(Builder builder) {
        this.selector = builder.selector;
        this.switchName = builder.switchName;
        this.serviceName = builder.serviceName;
        this.payload = builder.payload;
    }

    public Selector getSelector() {
        return selector;
    }

    public Switch getSwitchName() {
        return switchName;
    }

    public ServiceName getServiceName() {
        return serviceName;
    }

    public List<String> getPayload() {
        return payload;
    }

    public static class Builder {
        private Selector selector;
        private Switch switchName;
        private ServiceName serviceName;
        private List<String> payload;

        public Builder setSelector(Selector selector) {
            this.selector = selector;
            return this;
        }

        public Builder setSwitchName(Switch switchName) {
            this.switchName = switchName;
            return this;
        }

        public Builder setServiceName(ServiceName serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public Builder setPayload(List<String> payload) {
            this.payload = payload;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
