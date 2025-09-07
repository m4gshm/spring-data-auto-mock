module io.github.m4gshm.spring.data.mock.test.jpa.test {
    exports test.jpa.test;
    opens test.jpa.test;
    requires transitive io.github.m4gshm.spring.data.mock;
    requires transitive io.github.m4gshm.spring.data.mock.test.jpa;
    requires org.junit.jupiter.api;
    requires spring.beans;
    requires spring.boot.test;
    requires org.mockito;
    requires spring.aop;
    requires spring.context;
    requires io.github.m4gshm.spring.data.mock.test.common;
    requires spring.boot.autoconfigure;
}