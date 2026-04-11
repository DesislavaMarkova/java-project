package models;

import java.io.Serializable;

/**
 * Изброим тип за музикални жанрове.
 * Използва се за категоризация на песните в системата.
 */
public enum Genre implements Serializable {
    ROCK,
    POP,
    JAZZ,
    HIP_HOP,
    ELECTRONIC,
    CLASSICAL,
    NATIONAL_MUSIC,
    UNKNOWN
}
