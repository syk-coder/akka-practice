package com.primeCalculator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class NumberRangeMessage implements Serializable{
    private long startNumber;
    private long endNumber;
}
