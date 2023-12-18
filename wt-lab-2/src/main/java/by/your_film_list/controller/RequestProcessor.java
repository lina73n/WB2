package by.your_film_list.controller;

import by.your_film_list.bean.User;
import by.your_film_list.bean.UserPrivilegeRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RequestProcessor {
    /**
     * Sets the role attribute in the request object based on the user's session attribute.
     * If the user is not logged in, the role is set to "VISITOR".
     * Otherwise, the role is set to the user's privilege role.
     *
     * @param req the HttpServletRequest object
     */
    private void setRole(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        User user = (User) httpSession.getAttribute(SessionAttribute.USER.getName());
        if (user == null) {
            req.setAttribute(RequestAttribute.ROLE.name().toLowerCase(),
                    UserPrivilegeRole.VISITOR.getName());
        } else {
            req.setAttribute(RequestAttribute.ROLE.name().toLowerCase(),
                    user.getUserPrivilegeRole().getName());
        }
    }

    /**
     * Sets the current page name attribute in the request object.
     * The current page name is constructed using the request URL and query string.
     *
     * @param req the HttpServletRequest object
     */
    private void setCurrPageName(HttpServletRequest req) {
        String currPageName = req.getRequestURL() + "?" + req.getQueryString();

        req.setAttribute(RequestParameter
                .CURR_PAGE_NAME.name().toLowerCase(), currPageName);
    }

    /**
     * Processes the HTTP request by setting the role and current page name attributes.
     *
     * @param req the HttpServletRequest object
     */
    public void process(HttpServletRequest req) {
        setRole(req);
        setCurrPageName(req);
    }
}
