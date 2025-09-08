module io.github.m4gshm.spring.data.mock.test.common {
    exports test.common;
    opens test.common;
//    requires spring.data.jpa;
    requires io.github.m4gshm.spring.data.mock;
    requires spring.context;
    requires spring.data.commons;
    requires static lombok;
}