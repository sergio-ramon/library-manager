package service;

public class MenuMessageManager {
    public static void showMainMenuMessage() {
        System.out.println("======================= BIBLIOTECA ========================");
        System.out.println("+---+-------------------------+---+-----------------------+");
        System.out.println("| 1 | Cadastro                | 4 | Gerenciar Empréstimos |");
        System.out.println("| 2 | Verificar Cadastros     | 5 | Deletar Cadastros     |");
        System.out.println("| 3 | Editar Cadastros        |   |                       |");
        System.out.println("| 0 | Fechar o Programa       |   |                       |");
        System.out.println("+---+-----------------------------+-----------------------+");
        System.out.print("Selecione a opção desejada: ");
    }

    public static void showRegisterMenuMessage() {
        System.out.println("========================= CADASTRO =========================");
        System.out.println("+---+----------------------------+---+---------------------+");
        System.out.println("| 1 | Cadastrar Autor            | 3 | Cadastrar Leitor    |");
        System.out.println("| 2 | Cadastrar Livro            |   |                     |");
        System.out.println("| 0 | Voltar ao Menu Principal   |   |                     |");
        System.out.println("+---+----------------------------+---+---------------------+");
        System.out.print("Selecione a opção desejada: ");
    }

    public static void showListMenuMessage() {
        System.out.println("============================= LISTAGEM ===============================");
        System.out.println("+---+--------------------------------+---+---------------------------+");
        System.out.println("| 1 | Listar Autores Cadastrados     | 3 | Listar Livros Disponíveis |");
        System.out.println("| 2 | Listar Leitores Cadastrados    | 4 | Listar Livros Por Autor   |");
        System.out.println("| 0 | Voltar ao Menu Principal       |   |                           |");
        System.out.println("+---+--------------------------------+---+---------------------------+");
        System.out.print("Selecione a opção desejada: ");
    }

    public static void showUpdateMessage() {
        System.out.println("========================== EDIÇÃO ===========================");
        System.out.println("+---+-----------------------------+---+---------------------+");
        System.out.println("| 1 | Editar Autor                | 3 | Editar Livro        |");
        System.out.println("| 2 | Editar Leitor               |   |                     |");
        System.out.println("| 0 | Vol|tar ao Menu Principal   |   |                     |");
        System.out.println("+---+-----------------------------+---+---------------------+");
        System.out.print("Selecione a opção desejada: ");
    }

    public static void showDeleteMessage() {
        System.out.println("=============================== DELETAR ==============================");
        System.out.println("+---+-----------------------------+---+------------------------------+");
        System.out.println("| 1 | Deletar Autor               | 3 | Deletar Livro                |");
        System.out.println("| 2 | Deletar Leitor              |   |                              |");
        System.out.println("| 0 | Voltar ao Menu Principal    |   |                              |");
        System.out.println("+---+-----------------------------+---+------------------------------+");
        System.out.print("Selecione a opção desejada: ");
    }

    public static void showLoanMenuMessage() {
        System.out.println("==================== EMPRÉSTIMO ====================");
        System.out.println("+---+----------------------------------------------+");
        System.out.println("| 1 | Emprestar Livro                              |");
        System.out.println("| 2 | Devolver Livro                               |");
        System.out.println("| 0 | Voltar ao Menu Principal                     |");
        System.out.println("+---+----------------------------------------------+");
        System.out.print("Selecione a opção desejada: ");
    }
}
