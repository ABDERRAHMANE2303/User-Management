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

@Named("userProfileBean")
@ViewScoped
public class UserProfileBean implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private LoginBean loginBean;

    private User currentUser;
    private boolean editMode = false;

    @PostConstruct
    public void init() {
        // Get the logged-in user from the session
        if (loginBean.isLoggedIn()) {
            currentUser = loginBean.getCurrentUser();
        } else {
            // Redirect to login page if not logged in
            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void toggleEditMode() {
        editMode = !editMode;
    }

    public String saveProfile() {
        try {
            userService.saveOrUpdate(currentUser);
            editMode = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile updated successfully"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update profile: " + e.getMessage()));
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
}