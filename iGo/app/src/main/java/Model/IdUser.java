package Model;

public class IdUser {
    private static int idUser;

    public IdUser() {
    }

    public static int getIdUser() {
        return idUser;
    }
    public static void setIdUser(int idUser) {
        IdUser.idUser = idUser;
    }
}
