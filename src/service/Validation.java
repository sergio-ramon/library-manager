package service;
import domain.Reader;
import exceptions.InvalidCpfFormatException;
import exceptions.InvalidInputTypeException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidPhoneFormatException;

import java.util.List;

public class Validation {
    public static void validateArgument(String argument) throws InvalidArgumentException {
        if (!argument.matches("[\\p{L}\\s]+$")) {
            throw new InvalidArgumentException("O argumento só pode conter letras!");
        }

        if (argument.isBlank()) {
            throw new InvalidArgumentException("O argumento não pode estar vazio!");
        } else if (argument.length() < 2) {
            throw new InvalidArgumentException("O argumento precisa ter mais de 1 caractere!");
        } else if (argument.length() > 50) {
            throw new InvalidArgumentException("O argumento excede o limite de 50 caracteres!");
        }
    }

    public static void validateCpf(String cpf, List<Reader> readers) throws InvalidCpfFormatException {
        String cpfAltered = cpf.replaceAll("\\d", "X");
        String cpfModel = "XXX.XXX.XXX-XX";

        if (!cpfAltered.equals(cpfModel) || !validateCpfDigit(cpf)) {
            throw new InvalidCpfFormatException("Formato de CPF inválido!");
        }

        for (Reader reader : readers) {
            if (cpf.equals(reader.getCpf())) {
                throw new InvalidCpfFormatException("CPF já cadastrado!");
            }
        }
    }

    private static boolean validateCpfDigit(String cpf) {
        String returnedCpf = cpfTransfer(cpf);
        StringBuilder cpfAltered = new StringBuilder().append(returnedCpf);

        if (returnedCpf != null) {
            int digit1 = calculateDigit(cpfAltered, 0);
            cpfAltered.append(digit1);
            int digit2 = calculateDigit(cpfAltered, 1);
            cpfAltered.append(digit2);

            int cpfDigit1 = Integer.parseInt(String.valueOf(cpf.charAt(cpf.length()-2)));
            int cpfDigit2 = Integer.parseInt(String.valueOf(cpf.charAt(cpf.length()-1)));

            return cpfDigit1 == digit1 && cpfDigit2 == digit2;
        } else {
            return false;
        }
    }

    private static String cpfTransfer(String cpf) {
        cpf = cpf.replaceAll("\\D+", "");

        if (cpf.matches("(.)\\1+")) {
            return null;
        }

        return cpf;
    }

    private static int calculateDigit(StringBuilder cpf, int iterator) {
        int count = 10;
        int digit = 0;

        for (int i = iterator; i < cpf.length(); i++) {
            if (count >= 2) {
                int c = Integer.parseInt(String.valueOf(cpf.charAt(i)));
                digit += c * count;
                count--;
            }
        }

        if (digit % 11 == 0 | digit % 11 == 1) {
            return 0;
        } else {
            int s = digit % 11;
            return 11 - s;
        }
    }

    public static void validatePhone(String phone, List<Reader> readers) throws InvalidPhoneFormatException {
        String phoneModel = "XX XXXXX-XXXX";
        phone = phone.replaceAll("\\d", "X");

        if (!phone.equals(phoneModel)) {
            throw new InvalidPhoneFormatException("Formato de Telefone inválido!");
        }

        for (Reader reader : readers) {
            if (phone.equals(reader.getPhoneNumber())) {
                throw new InvalidPhoneFormatException("Telefone já cadastrado!");
            }
        }
    }

    public static int validateIntInput(String input) throws InvalidInputTypeException {
        if (!input.matches("\\d+")) {
            throw new InvalidInputTypeException("Digite um valor numérico positivo para a opção!");
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputTypeException("A opção digitada excede os limites!");
        }
    }
}
