package com.wizteco.configuration;

import java.io.Serializable;

import com.fasterxml.jackson.datatype.threetenbp.function.Function;

public interface CustomFunction<T, R> extends Function<T, R>,Serializable{

}
