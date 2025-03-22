module io.github.m4gshm.spring.data.mock.test.jpa.test {
    exports test;
    opens test;
    requires static lombok;
    requires transitive io.github.m4gshm.spring.data.mock;
    requires transitive io.github.m4gshm.spring.data.mock.test.jpa;
    requires spring.data.commons;
    requires org.junit.jupiter.api;
    requires spring.beans;
    requires spring.boot.test;
    requires org.mockito;
    requires spring.aop;
}