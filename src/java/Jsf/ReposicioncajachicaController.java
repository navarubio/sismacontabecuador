package Jsf;

import Modelo.Reposicioncajachica;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ReposicioncajachicaFacade;
import Jpa.ReposicioncajachicaFacadeLocal;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("reposicioncajachicaController")
@ViewScoped
public class ReposicioncajachicaController implements Serializable {

    @EJB
    private Jpa.ReposicioncajachicaFacadeLocal ejbFacade;
    private List<Reposicioncajachica> items = null;
    private Reposicioncajachica selected;
    @Inject
    private RequerimientosController requerimientosController;

    public ReposicioncajachicaController() {
    }

    public Reposicioncajachica getSelected() {
        return selected;
    }

    public void setSelected(Reposicioncajachica selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ReposicioncajachicaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Reposicioncajachica prepareCreate() {
        selected = new Reposicioncajachica();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicioncajachicaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicioncajachicaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecajachica").getString("ReposicioncajachicaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Reposicioncajachica> getItems() {
        if (items == null) {
            items = getFacade().reposicionesAll(requerimientosController.getEmpresa());
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecajachica").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Reposicioncajachica getReposicioncajachica(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Reposicioncajachica> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Reposicioncajachica> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Reposicioncajachica.class)
    public static class ReposicioncajachicaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ReposicioncajachicaController controller = (ReposicioncajachicaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reposicioncajachicaController");
            return controller.getReposicioncajachica(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Reposicioncajachica) {
                Reposicioncajachica o = (Reposicioncajachica) object;
                return getStringKey(o.getIdreposicioncajachica());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Reposicioncajachica.class.getName()});
                return null;
            }
        }

    }

}
