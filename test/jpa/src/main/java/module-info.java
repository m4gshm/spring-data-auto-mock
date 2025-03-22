module io.github.m4gshm.spring.data.mock.test.jpa {
    exports test.jpa;
    exports test.jpa.api;
    exports test.jpa.model;
    exports test.jpa.repo;
    exports test.jpa.service;
    opens test.jpa;
    opens test.jpa.api;
    opens test.jpa.model;
    opens test.jpa.repo;
    opens test.jpa.service;
    requires static lombok;
    requires jdk.unsupported;
    requires spring.web;
    requires spring.data.commons;
    requires spring.data.jpa;
}