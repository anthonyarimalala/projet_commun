
package model.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utilisateur {
     String idUser;
    String nom;
    String prenom;
    Date dateNaissance;
    String email;
    String password;
    int role;
    int etat;
    
    public String insert(Connection connection) throws SQLException, Exception {
    String response = "Echec lors de l'insertion de nouveau utilisateur";
    String sql = "INSERT INTO utilisateur(nom, prenom, date_naissance , email, password) VALUES (?, ?, ?, ?, ?);";
    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        statement.setString(1, this.getNom());
        statement.setString(2, this.getPrenom());
        statement.setDate(3, this.getDateNaissance());
        statement.setString(4, this.getEmail());
        statement.setString(5, this.getPassword());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    String idUser = generatedKeys.getString(1);
                    return idUser;
                }
            }
        }
    }
    return response;
}
    
    public static Utilisateur getUtilisateurById(Connection connection, String id) throws SQLException, Exception{
        String sql = "select * from utilisateur where id_user=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
             statement.setString(1, id);
             try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetConstruct(resultSet);
                }
            }
        }
        return null;
    }
    
    public static Utilisateur authentificationUtilisateur(Connection connection, String email, String password) throws SQLException, Exception{
        if(!isEmailExcist(connection, email)){
            throw new Exception("L'email n'existe pas!");
        }else{
            String sql = "select * from utilisateur where email=? and password=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                 statement.setString(1, email);
                 statement.setString(2, password);
                 try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSetConstruct(resultSet);
                    }
                }
            }
        }
        throw new Exception("Mot de passe incorrect!");
    }
    
    public static Utilisateur resultSetConstruct(ResultSet resultSet) throws SQLException, Exception{
        String idUser = resultSet.getString("id_user");
        String nom = resultSet.getString("nom");
        String prenom = resultSet.getString("prenom");
        Date dateNaissance = resultSet.getDate("date_naissance");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        int role = resultSet.getInt("role");
        int etat = resultSet.getInt("etat");
        
        if(etat == 0) throw new Exception("L'utilisateur a été supprimé!");

        Utilisateur utilisateur = new Utilisateur(idUser, nom, prenom, dateNaissance, email, password, role, etat);
        return utilisateur;
    }
    public static boolean isEmailExcist(Connection connection, String email) throws SQLException{
        String sql = "select * from utilisateur where email=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
             statement.setString(1, email);
             try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Utilisateur(String idUser, String nom, String prenom, Date dateNaissance, String email, String password, int role, int etat) throws Exception {
        this.idUser = idUser;
        this.setNom(nom);
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
        this.setEtat(etat);
    }

    

    public Utilisateur() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        if(nom.length()<3) throw new Exception("Le nom n'est pas valide!");
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        if(!email.contains("@gmail.com")) throw new Exception("Email non valide!");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws Exception {
        if(password.length()<4) throw new Exception("Mot de passe trop court: 4 lettres au minimum!");
        this.password = password;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) throws Exception {
        if(etat<-1) throw new Exception("Etat impossible!");
        this.etat = etat;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "idUser=" + idUser + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", email=" + email + ", password=" + password + ", role=" + role + ", etat=" + etat + '}';
    }
    
    
    
    
}
