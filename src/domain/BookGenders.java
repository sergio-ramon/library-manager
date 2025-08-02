package domain;

public enum BookGenders {
    FANTASY(1, "Fantasia"),
    FICTION(2, "Ficção"),
    NON_FICTION(3, "Não-Ficção"),
    NOVEL(4, "Romance"),
    SCIENCE_FICTION(5, "Ficção-Científica"),
    MYSTERY(6, "Mistério"),
    THRILLER_HORROR(7, "Suspense/Terror"),
    POETRY(8, "Poesia");

    private final int value;
    private final String nameValue;

    BookGenders(int value, String nameValue) {
        this.value = value;
        this.nameValue = nameValue;
    }

    public static BookGenders bookGendersByNumber(int gender) {
        for (BookGenders bookGender: values()) {
            if (bookGender.getValue() == gender) {
                return bookGender;
            }
        }
        return null;
    }

    public int getValue() { return value; }

    public String getNameValue() {
        return nameValue;
    }
}
