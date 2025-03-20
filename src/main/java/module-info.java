module io.github.m4gshm.spring.data.mock {
    exports io.github.m4gshm.spring.data.mock;
    requires static lombok;
    requires static org.slf4j;
    requires org.mockito;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.data.commons;
}