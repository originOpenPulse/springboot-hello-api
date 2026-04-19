package com.wizteco.configuration;

import java.io.Serializable;

import com.fasterxml.jackson.datatype.threetenbp.function.BiFunction;

public interface CustomBiFunction<T, U, R> extends BiFunction<T, U, R>,Serializable{

}
