package service;

import exceptions.InvalidInputTypeException;

public class HandleInputException {

    public static int handleIntInputException(String intArgument) {
        try {
            return Validation.validateIntInput(intArgument);
        } catch (InvalidInputTypeException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
