package org.usermanagement.bean;

import org.usermanagement.entities.User;
import org.usermanagement.service.UserService;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private UserService userService;

    private String username;
    private String password;
    private User currentUser;

    public String login() {
        User user = userService.authenticate(username, password);
        if (user != null) {
            currentUser = user;

            // Redirect based on user role
            if ("admin".equals(user.getRole())) {
                return "/admin/userManagement.xhtml?faces-redirect=true";
            } else if ("user".equals(user.getRole())) {
                return "/user/profile.xhtml?faces-redirect=true";
            }
        }

        // Authentication failed
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login", "Invalid username or password"));
        return "/error.xhtml?faces-redirect=true";
    }

    public String logout() {
        // Invalidate the session
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public boolean hasAdminRole() {
        return isLoggedIn() && "admin".equals(currentUser.getRole());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
