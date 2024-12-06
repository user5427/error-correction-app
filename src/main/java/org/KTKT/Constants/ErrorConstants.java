package org.KTKT.Constants;

import org.KTKT.Data.DataValidator;

/**
 * Error constants
 */
public class ErrorConstants {
//    static public String K_N_INVALID = "k and n are invalid";
//    static public String K_INVALID = "k is invalid";
//    static public String N_INVALID = "n is invalid";
//    static public String P_INVALID = "p is invalid";
//    static public String PROBABILITY_INVALID = "probability is invalid";
//    static public String MATRIX_INVALID = "matrix is invalid";
//    static public String MATRIX_DIMENSIONS_INVALID = "matrix dimensions are invalid. (do not match k or n)";
//    static public String MATRIX_NOT_CREATED = "matrix is not created";
//    static public String INVALID_NUMBER = "invalid number";
//    static public String VALID = "valid";
//    static public String ERROR = "Error: ";
//    static public String PREVIOUS_MATRIX_NOT_FOUND = "Previous matrix not found";
//    static public String H_MATRIX_NOT_GENERATED = "H matrix not generated";
//    static public String A_MATRIX_NOT_GENERATED = "A matrix not generated";
//    static public String COSSET_SYNDROM_WEIGHTS_NOT_GENERATED = "Cosset syndrom weights not generated";
//
//    static public final String EMPTY_TEXT = "Text cannot be empty";
//    static public final String INVALID_VECTOR_MESSAGE = "Vector is invalid";

    // LT

    static public String K_N_INVALID = "k ir n yra neteisingi";
    static public String K_INVALID = "k yra neteisingas";
    static public String N_INVALID = "n yra neteisingas";
    static public String P_INVALID = "p yra neteisingas";
    static public String PROBABILITY_INVALID = "tikimybė yra neteisinga";
    static public String MATRIX_INVALID = "matrica yra neteisinga";
    static public String MATRIX_DIMENSIONS_INVALID = "matricos matmenys yra neteisingi (nesutampa su k arba n)";
    static public String MATRIX_NOT_CREATED = "matrica nesukurta";
    static public String INVALID_NUMBER = "neteisingas skaičius";
    static public String VALID = "Teisinga";
    static public String ERROR = "Klaida: ";
    static public String PREVIOUS_MATRIX_NOT_FOUND = "Ankstesnė matrica nerasta";
    static public String H_MATRIX_NOT_GENERATED = "H matrica nesugeneruota";
    static public String A_MATRIX_NOT_GENERATED = "A matrica nesugeneruota";
    static public String COSSET_SYNDROM_WEIGHTS_NOT_GENERATED = "Coset sindromo svoriai nesugeneruoti";

    static public final String EMPTY_TEXT = "Tekstas negali būti tuščias";
    static public final String INVALID_VECTOR_MESSAGE = "Vektorius yra neteisingas";

    static public final String INVALID = "Neteisinga";
    static public final String SAVED = "Išsaugota";
    static public final String NOT_SAVED = "Neišsaugota";
    static public final String INVALID_MATRIX_VALUE = "Neteisinga matricos reikšmė";
    /**
     * Matrix errors
     */

//    static public final String INVALID_ROWS_COLUMNS_COUNT = "Invalid number of rows or columns";
//    static public final String INVALID_ROW_COLUMNS_INDEX = "Invalid row or column index";
//    static public final String INVALID_ROW_INDEX_OR_VALUES_COUNT = "Invalid row index or number of values";
//    static public final String INVALID_COLUMN_INDEX_OR_VALUES_COUNT = "Invalid column index or number of values";
//    static public final String INVALID_COLUMN_INDEX = "Invalid column index";
//    static public final String INVALID_ROW_INDEX = "Invalid row index";
//    static public final String SAME_DIMENSIONS_REQUIRED = "Matrices must have the same dimensions";
//    static public final String INVALID_MATRIX_DIMENSIONS = "Invalid matrix dimensions";
//    static public final String ONLY_ONE_ROW = "Matrix must have only one row";

    // LT

    static public final String INVALID_ROWS_COLUMNS_COUNT = "Neteisingas eilučių arba stulpelių skaičius";
    static public final String INVALID_ROW_COLUMNS_INDEX = "Neteisingas eilutės arba stulpelio indeksas";
    static public final String INVALID_ROW_INDEX_OR_VALUES_COUNT = "Neteisingas eilutės indeksas arba reikšmių skaičius";
    static public final String INVALID_COLUMN_INDEX_OR_VALUES_COUNT = "Neteisingas stulpelio indeksas arba reikšmių skaičius";
    static public final String INVALID_COLUMN_INDEX = "Neteisingas stulpelio indeksas";
    static public final String INVALID_ROW_INDEX = "Neteisingas eilutės indeksas";
    static public final String SAME_DIMENSIONS_REQUIRED = "Matricos turi turėti vienodus matmenis";
    static public final String INVALID_MATRIX_DIMENSIONS = "Neteisingi matricos matmenys";
    static public final String ONLY_ONE_ROW = "Matricoje turi būti tik viena eilutė";

    /**
     * Decoding errors
     */

//    static public final String MESSAGE_SYNDROME_WEIGHT_NOT_FOUND = "Message syndrome weight not found";
//    static public final String SYNDROME_WEIGHT_ZERO_NOT_FOUND = "Message syndrome with weight 0 not found";
//    static public final String SYNDROME_WEIGHT_NOT_FOUND = "Syndrome weight not found";

    // LT

    static public final String MESSAGE_SYNDROME_WEIGHT_NOT_FOUND = "Žinutės sindromo svoris nerastas";
    static public final String SYNDROME_WEIGHT_ZERO_NOT_FOUND = "Žinutės sindromas su svoriu 0 nerastas";
    static public final String SYNDROME_WEIGHT_NOT_FOUND = "Sindromo svoris nerastas";

    /**
     * Coset syndrome errors
     */

//    static public final String VECTOR_LENGTH_ATLEAST_TWO = "Vector length must be at least 2";
//    static public final String VECTOR_AT_MAXIMUM = "Vector is at maximum";
//    static public final String VECTOR_CONTAINS_ONLY_ZERO_ONE = "Vector must contain only 0 and 1";
//    static public final String VECTOR_CONTAINS_ATLEAST_ONE_ONE = "Vector must contain at least one 1";

    // LT
    static public final String VECTOR_LENGTH_ATLEAST_TWO = "Vektoriaus ilgis turi būti bent 2";
    static public final String VECTOR_AT_MAXIMUM = "Vektorius yra maksimalus";
    static public final String VECTOR_CONTAINS_ONLY_ZERO_ONE = "Vektorius turi sudaryti tik 0 ir 1";
    static public final String VECTOR_CONTAINS_ATLEAST_ONE_ONE = "Vektorius turi turėti bent vieną 1";

    /**
     * Data validator
     */

//    static public final String ROWS_K_LESS_THAN_COLUMNS_N = "rows_k must be less than columns_n.";
//    static public final String DIFFERENCE_BETWEEN_COLUMNS_N_AND_ROWS_K = "difference between columns_n and rows_k must be less than " + DataValidator.KNDIFFERENCE_LIMIT + ".";
//    static public final String ROWS_COLUMNS_LIMIT = "rows_k and columns_n must be less than " + DataValidator.KNLimit + ".";
//    static public final String ROWS_K_LESS_OR_EQUAL_TO_ZERO = "rows_k cannot be less or equal to 0.";
//    static public final String ROWS_K_NULL = "rows_k cannot be null.";
//    static public final String ROWS_K_EMPTY = "rows_k cannot be empty.";
//    static public final String ROWS_K_MUST_BE_A_NUMBER = "rows_k must be a number.";
//    static public final String COLUMNS_N_LESS_OR_EQUAL_TO_ZERO = "columns_n cannot be less or equal to 0.";
//    static public final String COLUMNS_N_NULL = "columns_n cannot be null.";
//    static public final String COLUMNS_N_EMPTY = "columns_n cannot be empty.";
//    static public final String COLUMNS_N_MUST_BE_A_NUMBER = "columns_n must be a number.";
//    static public final String PROBABILITY_NEGATIVE = "probability cannot be negative.";
//    static public final String PROBABILITY_MORE_THAN_ONE = "probability cannot be more than 1.";
//    static public final String PROBABILITY_NULL = "probability cannot be null.";
//    static public final String PROBABILITY_EMPTY = "probability cannot be empty.";
//    static public final String PROBABILITY_MUST_BE_A_NUMBER = "probability must be a number.";
//    static public final String MATRIX_NULL = "Matrix cannot be null.";
//    static public final String MATRIX_ELEMENTS_ZERO_ONE = "Matrix elements must be either 0 or 1.";
//    static public final String VECTOR_LENGTH_EQUAL = "Vector length must be equal to ";
//    static public final String VECTOR_ELEMENTS_ZERO_ONE = "Vector elements must be either 0 or 1.";

    // LT
    static public final String ROWS_K_LESS_THAN_COLUMNS_N = "eilutės_k turi būti mažiau nei stulpeliai_n.";
    static public final String DIFFERENCE_BETWEEN_COLUMNS_N_AND_ROWS_K = "skirtumas tarp stulpelių_n ir eilučių_k turi būti mažesnis nei " + DataValidator.KNDIFFERENCE_LIMIT + ".";
    static public final String ROWS_COLUMNS_LIMIT = "eilutės_k ir stulpeliai_n turi būti mažesni nei " + DataValidator.KNLimit + ".";
    static public final String ROWS_K_LESS_OR_EQUAL_TO_ZERO = "eilutės_k negali būti mažesnės arba lygios 0.";
    static public final String ROWS_K_NULL = "eilutės_k negali būti null.";
    static public final String ROWS_K_EMPTY = "eilutės_k negali būti tuščios.";
    static public final String ROWS_K_MUST_BE_A_NUMBER = "eilutės_k turi būti skaičius.";
    static public final String COLUMNS_N_LESS_OR_EQUAL_TO_ZERO = "stulpeliai_n negali būti mažesni arba lygūs 0.";
    static public final String COLUMNS_N_NULL = "stulpeliai_n negali būti null.";
    static public final String COLUMNS_N_EMPTY = "stulpeliai_n negali būti tušti.";
    static public final String COLUMNS_N_MUST_BE_A_NUMBER = "stulpeliai_n turi būti skaičius.";
    static public final String PROBABILITY_NEGATIVE = "tikimybė negali būti neigiama.";
    static public final String PROBABILITY_MORE_THAN_ONE = "tikimybė negali būti didesnė nei 1.";
    static public final String PROBABILITY_NULL = "tikimybė negali būti null.";
    static public final String PROBABILITY_EMPTY = "tikimybė negali būti tuščia.";
    static public final String PROBABILITY_MUST_BE_A_NUMBER = "tikimybė turi būti skaičius.";
    static public final String MATRIX_NULL = "Matrica negali būti null.";
    static public final String MATRIX_ELEMENTS_ZERO_ONE = "Matricos elementai turi būti arba 0, arba 1.";
    static public final String VECTOR_LENGTH_EQUAL = "Vektoriaus ilgis turi būti lygus ";
    static public final String VECTOR_ELEMENTS_ZERO_ONE = "Vektoriaus elementai turi būti arba 0, arba 1.";

    static public final String SAFE_LIMIT_NKDIFFERENCE = "Vartotojo sąsaja nepalaiko didesnio skirtumo tarp stulpelių ir eilučių nei " + DataValidator.SAFE_KNDIFFERENCE_LIMIT + ". Duomenys atspausdinti terminale.";

}
