package org.wooddog.servlets;

import org.wooddog.servlets.actions.AddChannelAction;
import org.wooddog.servlets.actions.AddCompanyAction;
import org.wooddog.servlets.actions.DeleteChannelAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: claus
 * Date: 24-06-11
 * Time: 18:35
 * To change this template use File | Settings | File Templates.
 */
public class PageActionFactory {
    private static final PageActionFactory INSTANCE = new PageActionFactory();
    private Map<String, Class<? extends PageAction>> actions;

    public PageActionFactory() {
        actions = new HashMap<String, Class<? extends PageAction>>();
        actions.put("AddChannel", AddChannelAction.class);
        actions.put("DeleteChannel", DeleteChannelAction.class);
        actions.put("AddCompany", AddCompanyAction.class);
    }

    public static PageActionFactory getInstance() {
        return INSTANCE;
    }


    public PageAction getAction(String name) {
        Class<? extends PageAction> action;

        action = actions.get(name);
        if (action == null) {
            return null;
        }

        try {
            return action.newInstance();
        } catch (InstantiationException x) {
            throw new RuntimeException(x);
        } catch (IllegalAccessException x) {
            throw new RuntimeException(x);
        }
    }
}
