package service;

import dao.ReaderDAO;
import domain.Reader;
import exceptions.InvalidCpfFormatException;
import exceptions.InvalidArgumentException;

import java.util.List;
import java.util.Scanner;

public class ReaderManager {
    static Scanner input = new Scanner(System.in);

    public Reader createReader(ReaderDAO readerDAO)
            throws InvalidArgumentException, InvalidCpfFormatException {
        System.out.print("Digite o nome do leitor: ");
        String name = input.nextLine();
        Validation.validateArgument(name);
        System.out.print("Digite o cpf do leitor - XXX.XXX.XXX-XX: ");
        String cpf = input.next();
        input.nextLine(); // Resolve momentaneamente lixo residual
        Validation.validateCpf(cpf, readerDAO.listReaders());
        System.out.print("Digite o telefone do leitor - XX XXXXX-XXXX: ");
        String phoneNumber = input.nextLine();
        Validation.validatePhone(phoneNumber, readerDAO.listReaders());
        return new Reader(name, cpf, phoneNumber);
    }

    public void editReader(ReaderDAO readerDAO)
            throws InvalidArgumentException, InvalidCpfFormatException {
        listReaders(readerDAO.listReaders());
        System.out.println("Digite o número do leitor: ");
        int id = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual
        Reader reader = readerDAO.findReaderById(id);

        if (reader == null) {
            System.out.println("Número de leitor inválido!");
            return;
        }

        System.out.println("============== EDIÇÃO DE LEITOR ==============");
        System.out.print("Digite o novo nome do leitor: ");
        String name = input.nextLine();
        Validation.validateArgument(name);
        if (checkReaderName(readerDAO.listReaders(), name)) {
            System.out.println("Nome de leitor já cadastrado. Tente novamente!");
            return;
        }

        System.out.print("Digite o telefone do leitor - XX XXXXX-XXXX: ");
        String phoneNumber = input.nextLine();

        reader.setName(name);
        reader.setPhoneNumber(phoneNumber);
        System.out.println("===============================================");
    }

    public void listReaders(List<Reader> readers) {
        if (readers != null && !readers.isEmpty()) {
            System.out.println("=========== LEITORES CADASTRADOS ===========");
            System.out.println("+----+---------------------------------------");
            for (Reader reader : readers) {
                System.out.println(reader);
            }
            System.out.println("+----+---------------------------------------");
        } else {
            System.out.println("Não existem leitores cadastrados...");
        }
    }

    public void deleteReader(ReaderDAO readerDAO) {
        if (!readerDAO.listReaders().isEmpty()) {
            listReaders(readerDAO.listReaders());
            System.out.println("Digite o número do leitor: ");
            int id = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual
            Reader reader = readerDAO.findReaderById(id);

            if (reader == null) {
                System.out.println("Número de leitor inválido!");
                return;
            }

            readerDAO.deleteReader(reader);

            System.out.println("Leitor excluído com sucesso!");
            System.out.println("===============================================");
        } else {
            System.out.println("Não existe leitores cadastrados...");
        }
    }

    private boolean checkReaderName(List<Reader> readers, String name) {
        for (Reader r : readers) {
            if (r.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
