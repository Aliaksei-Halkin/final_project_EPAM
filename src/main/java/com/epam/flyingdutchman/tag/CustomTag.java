package com.epam.flyingdutchman.tag;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

import static com.epam.flyingdutchman.util.constants.Context.SESSION_USERNAME;
import static com.epam.flyingdutchman.util.constants.Context.SESSION_USER_ROLE;

public class CustomTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        Integer role = (Integer) session.getAttribute(SESSION_USER_ROLE);
        String username = (String) session.getAttribute(SESSION_USERNAME);
        String message ;
        if (role == null) {
            message = "Welcome to our restaurant, QUEST!";
        } else {
            switch (role) {
                case 1:
                    message = "Hello dear ADMINISTRATOR! You entered as: " + username;
                    break;
                case 2:
                    message = "Hi, MANAGER! You entered as: " + username;
                    break;
                case 3:
                    message = "Hi, dear authorized Client!  You entered as: " + username;
                    break;
                case 4:
                    message = "Hi, Cook!  You entered as: " + username;
                    break;
                default:
                    message = "Welcome to our restaurant, GUEST!";
            }
        }

        try {
            JspWriter out = pageContext.getOut();
            out.write("<hr/>" + message + "<hr/>");
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

}
