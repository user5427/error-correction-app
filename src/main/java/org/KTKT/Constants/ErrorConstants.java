package org.KTKT.Constants;

import org.KTKT.Data.DataValidator;

/**
 * Error constants
 */
public class ErrorConstants {
    static public String K_N_INVALID = "k and n are invalid";
    static public String K_INVALID = "k is invalid";
    static public String N_INVALID = "n is invalid";
    static public String P_INVALID = "p is invalid";
    static public String PROBABILITY_INVALID = "probability is invalid";
    static public String MATRIX_INVALID = "matrix is invalid";
    static public String MATRIX_DIMENSIONS_INVALID = "matrix dimensions are invalid. (do not match k or n)";
    static public String MATRIX_NOT_CREATED = "matrix is not created";
    static public String INVALID_NUMBER = "invalid number";
    static public String VALID = "valid";
    static public String ERROR = "Error: ";
    static public String PREVIOUS_MATRIX_NOT_FOUND = "Previous matrix not found";
    static public String H_MATRIX_NOT_GENERATED = "H matrix not generated";
    static public String A_MATRIX_NOT_GENERATED = "A matrix not generated";
    static public String COSSET_SYNDROM_WEIGHTS_NOT_GENERATED = "Cosset syndrom weights not generated";

    static public final String EMPTY_TEXT = "Text cannot be empty";
    static public final String INVALID_VECTOR_MESSAGE = "Vector is invalid";

    /**
     * Matrix errors
     */

    static public final String INVALID_ROWS_COLUMNS_COUNT = "Invalid number of rows or columns";
    static public final String INVALID_ROW_COLUMNS_INDEX = "Invalid row or column index";
    static public final String INVALID_ROW_INDEX_OR_VALUES_COUNT = "Invalid row index or number of values";
    static public final String INVALID_COLUMN_INDEX_OR_VALUES_COUNT = "Invalid column index or number of values";
    static public final String INVALID_COLUMN_INDEX = "Invalid column index";
    static public final String INVALID_ROW_INDEX = "Invalid row index";
    static public final String SAME_DIMENSIONS_REQUIRED = "Matrices must have the same dimensions";
    static public final String INVALID_MATRIX_DIMENSIONS = "Invalid matrix dimensions";
    static public final String ONLY_ONE_ROW = "Matrix must have only one row";

    /**
     * Decoding errors
     */

    static public final String MESSAGE_SYNDROME_WEIGHT_NOT_FOUND = "Message syndrome weight not found";
    static public final String SYNDROME_WEIGHT_ZERO_NOT_FOUND = "Message syndrome with weight 0 not found";
    static public final String SYNDROME_WEIGHT_NOT_FOUND = "Syndrome weight not found";

    /**
     * Coset syndrome errors
     */

    static public final String VECTOR_LENGTH_ATLEAST_TWO = "Vector length must be at least 2";
    static public final String VECTOR_AT_MAXIMUM = "Vector is at maximum";
    static public final String VECTOR_CONTAINS_ONLY_ZERO_ONE = "Vector must contain only 0 and 1";
    static public final String VECTOR_CONTAINS_ATLEAST_ONE_ONE = "Vector must contain at least one 1";

    /**
     * Data validator
     */

    static public final String ROWS_K_LESS_THAN_COLUMNS_N = "rows_k must be less than columns_n.";
    static public final String DIFFERENCE_BETWEEN_COLUMNS_N_AND_ROWS_K = "difference between columns_n and rows_k must be less than " + DataValidator.KNDIFFERENCE_LIMIT + ".";
    static public final String ROWS_COLUMNS_LIMIT = "rows_k and columns_n must be less than " + DataValidator.KNLimit + ".";
    static public final String ROWS_K_LESS_OR_EQUAL_TO_ZERO = "rows_k cannot be less or equal to 0.";
    static public final String ROWS_K_NULL = "rows_k cannot be null.";
    static public final String ROWS_K_EMPTY = "rows_k cannot be empty.";
    static public final String ROWS_K_MUST_BE_A_NUMBER = "rows_k must be a number.";
    static public final String COLUMNS_N_LESS_OR_EQUAL_TO_ZERO = "columns_n cannot be less or equal to 0.";
    static public final String COLUMNS_N_NULL = "columns_n cannot be null.";
    static public final String COLUMNS_N_EMPTY = "columns_n cannot be empty.";
    static public final String COLUMNS_N_MUST_BE_A_NUMBER = "columns_n must be a number.";
    static public final String PROBABILITY_NEGATIVE = "probability cannot be negative.";
    static public final String PROBABILITY_MORE_THAN_ONE = "probability cannot be more than 1.";
    static public final String PROBABILITY_NULL = "probability cannot be null.";
    static public final String PROBABILITY_EMPTY = "probability cannot be empty.";
    static public final String PROBABILITY_MUST_BE_A_NUMBER = "probability must be a number.";
    static public final String MATRIX_NULL = "Matrix cannot be null.";
    static public final String MATRIX_ELEMENTS_ZERO_ONE = "Matrix elements must be either 0 or 1.";
    static public final String VECTOR_LENGTH_EQUAL = "Vector length must be equal to ";
    static public final String VECTOR_ELEMENTS_ZERO_ONE = "Vector elements must be either 0 or 1.";
}
