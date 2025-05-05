package org.usermanagement.bean;

import org.usermanagement.entities.User;
import org.usermanagement.service.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class UserManagementBean implements Serializable {

    @Inject
    private UserService userService;

    private List<User> users;
    private User selectedUser;
    private User newUser;

    @PostConstruct
    public void init() {
        users = userService.findAll();
        newUser = new User();
    }

    public String prepareAddUser() {
        newUser = new User();
        return "/admin/addUser.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        try {
            userService.saveOrUpdate(newUser);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User saved successfully"));
            return "/admin/userManagement.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save user: " + e.getMessage()));
            return null;
        }
    }

    public void deleteUser(User user) {
        try {
            userService.delete(user);
            users.remove(user);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User deleted successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete user: " + e.getMessage()));
        }
    }

    public String editUser(User user) {
        selectedUser = user;
        return "/admin/editUser.xhtml?faces-redirect=true";
    }

    public String updateUser() {
        try {
            userService.saveOrUpdate(selectedUser);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "User updated successfully"));
            return "/admin/userManagement.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update user: " + e.getMessage()));
            return null;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }
}