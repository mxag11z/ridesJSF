package filtro;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import modelo.DriverBean;

@Named
@ApplicationScoped
public class LoginPhaseListener implements jakarta.faces.event.PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        String currentPage = context.getViewRoot().getViewId();

        // List of pages that require login
        if (currentPage != null && (currentPage.contains("menuAfterLogin.xhtml"))) {
            DriverBean driverBean = context.getApplication()
                                           .evaluateExpressionGet(context, "#{driverBean}", DriverBean.class);

            if (driverBean == null || !driverBean.isLoggedIn()) {
                try {
                    context.getExternalContext().redirect(context.getExternalContext().getRequestContextPath() + "/index.xhtml");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        // Not used
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
