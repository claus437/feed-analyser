package org.wooddog.servlets.actions;

import org.wooddog.ChannelManager;
import org.wooddog.dao.ChannelService;
import org.wooddog.dao.CompanyService;
import org.wooddog.domain.Channel;
import org.wooddog.domain.Company;
import org.wooddog.servlets.PageAction;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: DENCBR
 * Date: 12-08-11
 * Time: 08:58
 * To change this template use File | Settings | File Templates.
 */
public class AddCompanyAction implements PageAction {
    public void execute(Map<String, String[]> parameters) {
        String name;
        Company company;

        name = parameters.get("name")[0];

        company = new Company();
        company.setName(name);

        CompanyService.getInstance().storeCompany(company);
    }
}
